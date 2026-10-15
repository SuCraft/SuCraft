import com.github.houbb.opencc4j.util.ZhConverterUtil
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import java.net.URL
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.zip.ZipFile

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.github.houbb:opencc4j:1.14.0")
    }
}

plugins {
    id("java-library")
    id("com.gradleup.shadow") version "9.4.1"
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
    maven("https://libraries.minecraft.net")
}

configurations {
    create("shadowInclude")
}

dependencies {
    compileOnly("org.sucraft.suki:suki-api:26.3-R0.1-SNAPSHOT")
    compileOnly("org.sucraft.suki:suki-server:26.3-R0.1-SNAPSHOT")
    compileOnly("com.mojang:datafixerupper:10.0.21")
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(25)
}

val serverDir: File = projectDir.resolve("run")
val pluginDir: File = serverDir.resolve("plugins")

val generatedResourcesDir: Provider<Directory> = layout.buildDirectory.dir("tmp/generated_resources")

val generateResources by tasks.registering {
    val outputDir = generatedResourcesDir

    // Declare the entire directory as this task's output.
    outputs.dir(outputDir)

    doLast {
        val dir = outputDir.get().asFile
        dir.mkdirs()

        // Keep track of exactly which files this invocation generates.
        val generatedFiles = mutableSetOf<String>()

        fun writeGeneratedFile(relativePath: String, content: String) {
            generatedFiles += relativePath

            val file = dir.resolve(relativePath)
            file.parentFile.mkdirs()
            file.writeText(content)
        }

        // Example generated files.
        writeGeneratedFile(
            "foo.txt",
            "This is the contents of foo.txt"
        )

        writeGeneratedFile(
            "bar.txt",
            "This is the contents of bar.txt"
        )

        writeGeneratedFile(
            "nested/baz.txt",
            "This is the contents of nested/baz.txt"
        )

        writeGeneratedFile(
            "nested/deeper/qux.txt",
            "This is the contents of nested/deeper/qux.txt"
        )

        // Remove anything that was not generated during this invocation.
        dir.walkTopDown()
            .filter { it.isFile }
            .forEach { file ->
                val relativePath = file.relativeTo(dir).invariantSeparatorsPath

                if (relativePath !in generatedFiles) {
                    file.delete()
                }
            }

        // Remove empty directories left behind by deleted files.
        dir.walkBottomUp()
            .filter { it.isDirectory && it != dir }
            .forEach { directory ->
                if (directory.listFiles()?.isEmpty() == true) {
                    directory.delete()
                }
            }
    }
}

val runDatagen = tasks.register<Exec>("runDatagen") {
    group = "data generation"
    workingDir = file("../../..")
    commandLine("./gradlew", ":plugins:Sushi:datagen:runDatagen") // Windows
}

tasks {

    build {
        dependsOn(shadowJar)
    }

    withType<Jar> {
        dependsOn(runDatagen)
        // Include generated resources
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        from("datagen/src/main/generated") {
            exclude("assets/**")
            exclude("data/**")
        }
        from("datagen/src/main/generated/assets") {
            into("resource_pack/assets")
        }
        from("datagen/src/main/generated/data") {
            into("data_pack/data")
        }
        includeEmptyDirs = false
        // Don't include data generation sources
        if (name == "shadowJar") {
            exclude("assets/**")
            exclude("data/**")
        }
    }

    shadowJar {
        // dependsOn("jar")
        configurations = listOf(project.configurations.getByName("shadowInclude"))
    }

    abstract class GenerateAdditionalLangFileTask : DefaultTask() {

        init {
            group = "data generation"
        }

        @get:InputFile
        val inputFile = project.objects.fileProperty()

        @get:OutputFile
        val outputFile = project.objects.fileProperty()

        @get:Input
        abstract val transformer: Property<(String, String) -> String>

        @TaskAction
        fun generate() {
            val inputAsString = inputFile.get().asFile.readText()
            val inputAsMap = JsonSlurper().parseText(inputAsString) as Map<String, String>
            val transformation = transformer.get()
            val outputAsMap = inputAsMap.mapValues { (key, value) ->
                transformation(key, value)
            }
            val outputAsString = JsonOutput.toJson(outputAsMap)
            outputFile.get().asFile.apply {
                parentFile.mkdirs()
                writeText(outputAsString)
            }
        }

    }

    fun langInputFile(locale: String) =
        layout.projectDirectory.file("src/main/resources/resource_pack/assets/minecraft/lang/$locale.json")

    fun langOutputFile(locale: String) = layout.buildDirectory.file("generated/langfiles/resource_pack/assets/minecraft/lang/$locale.json")

    val generateUpsidedownEnglishLangFile =
        register<GenerateAdditionalLangFileTask>("generateUpsidedownEnglishLangFile") {
            inputFile = langInputFile("en_us")
            outputFile = langOutputFile("en_ud")
            transformer = { _, value ->
                val regularText =
                    "abcdefghijklmnpqrtuvwyABCDEFGJKLMPQRTUVWY12345679.,'\"?!([{<&;"
                val upsidedownText =
                    "ɐqɔpǝɟᵷɥᴉɾʞꞁɯudbɹʇnʌʍʎⱯᗺƆᗡƎℲ⅁ՐꞰꞀWԀꝹᴚ⟘∩ɅM⅄⥝ᘔƐ߈ϛ9ㄥ6˙',„¿¡)]}>⅋⸵"
                value.map {
                    regularText.indexOf(it).takeIf { it != -1 }?.let { upsidedownText[it] }
                        ?: upsidedownText.indexOf(it).takeIf { it != -1 }?.let { regularText[it] }
                        ?: it
                }.joinToString("").reversed()
            }
        }

    val generateSimplifiedChineseLangFile =
        register<GenerateAdditionalLangFileTask>("generateSimplifiedChineseLangFile") {
            inputFile = langInputFile("zh_tw")
            outputFile = langOutputFile("zh_cn")
            transformer = { _, value -> ZhConverterUtil.toSimple(value) }
        }

    processResources {

        // Generated additional lang files
        dependsOn(
            generateUpsidedownEnglishLangFile,
            generateSimplifiedChineseLangFile
        )

        // Duplicate lang files
        for ((from, to) in sequenceOf(
            "en_us" to "en_au",
            "en_us" to "en_ca",
            "en_us" to "en_gb",
            "en_us" to "en_nz",
            "fr_fr" to "fr_ca",
            "nl_nl" to "nl_be",
            "zh_tw" to "zh_hk",
        )) {
            from("src/main/resources") {
                include("resource_pack/assets/minecraft/lang/$from.json")
                rename("$from.json", "$to.json")
            }
        }

    }

    processResources {
        val props = mapOf(
            "version" to version,
            "description" to project.description,
            "apiVersion" to "\"${project.providers.gradleProperty("apiVersion").get()}\"",
        )
        filesMatching("paper-plugin.yml") {
            expand(props)
        }
    }

}

// Include some vanilla client assets
val vanillaClientURL = providers.gradleProperty("vanillaClientURL").get()
val vanillaClientDownloadFolder = layout.buildDirectory.dir("vanilla-client/downloads").get()
val vanillaClientDownloadFile = vanillaClientDownloadFolder.file("${providers.gradleProperty("apiVersion").get()}.jar")
val vanillaClientExtractsFolder = layout.buildDirectory.dir("resources/main/resource_pack/assets").get()

val downloadMinecraftClient: TaskProvider<Task> = tasks.register("downloadMinecraftClient") {
    group = "vanilla client assets"

    outputs.dir(vanillaClientDownloadFolder)
    doLast {
        val url = vanillaClientURL
            ?: throw IllegalArgumentException("No vanillaClientURL configured; skipping direct download.")
        vanillaClientDownloadFolder.asFile.mkdirs()
        val target = vanillaClientDownloadFile.asFile
        if (!target.exists()) {
            logger.lifecycle("Downloading Minecraft client from $url -> $target")
            URL(url).openStream().use { input ->
                Files.copy(input, target.toPath(), StandardCopyOption.REPLACE_EXISTING)
            }
        }
    }
}

sourceSets {
    main {
        resources.srcDir(layout.buildDirectory.dir("generated/langfiles"))
    }
}

val includedVanillaAssets =
    (listOf(
        "black",
        "blue",
        "brown",
        "cyan",
        "gray",
        "green",
        "light_blue",
        "light_gray",
        "lime",
        "magenta",
        "orange",
        "pink",
        "purple",
        "red",
        "white",
        "yellow"
    ).flatMap { listOf("${it}_concrete", "${it}_wool") } + listOf(
        "tuff",
        "polished_tuff"
    )).flatMap { listOf("${it}_slab", "${it}_stairs") }
        .flatMap {
            listOf(
                "minecraft/blockstates/$it.json" to "sucraft/blockstates/$it.json",
                "minecraft/items/$it.json" to "sucraft/items/$it.json"
            )
        }.toMap()

val extractVanillaAssets: TaskProvider<Task> = tasks.register("extractVanillaAssets") {
    group = "vanilla client assets"

    dependsOn(downloadMinecraftClient, "processResources")
    outputs.dir(vanillaClientExtractsFolder)

    doLast {
        val folder = vanillaClientExtractsFolder.asFile
        folder.mkdirs()
        val sourceJar: File = vanillaClientDownloadFile.asFile

        logger.lifecycle("Extracting assets from ${sourceJar.absolutePath}")

        ZipFile(sourceJar).use { zip ->
            val entries = zip.entries()
            while (entries.hasMoreElements()) {
                val entry = entries.nextElement()

                // We only want specific entries
                if (!entry.name.startsWith("assets/")) continue
                val outName = includedVanillaAssets.get(entry.name.removePrefix("assets/"))
                if (outName == null) continue

                val outFile = folder.resolve(outName)
                if (entry.isDirectory) {
                    outFile.mkdirs()
                } else {
                    outFile.parentFile?.mkdirs()
                    zip.getInputStream(entry).use { input ->
                        Files.copy(input, outFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
                    }
                }
            }
        }
    }
}

tasks.named("jar") {
    dependsOn(extractVanillaAssets)
}
tasks.named("shadowJar") {
    dependsOn(extractVanillaAssets)
}
