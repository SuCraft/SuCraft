import com.github.houbb.opencc4j.util.ZhConverterUtil
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import java.net.URL
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.TreeMap
import java.util.zip.ZipFile
import kotlin.collections.filter

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

    abstract class GenerateLangFileTask : DefaultTask() {

        init {
            group = "language file generation"
        }

        @get:OutputFile
        val outputFile = project.objects.fileProperty()

    }

    abstract class GenerateLangFileFromParametrizedLangFileTask : GenerateLangFileTask() {

        @get:InputFile
        val inputFile = project.objects.fileProperty()

        @get:Input
        @get:Optional
        abstract val valuePostProcessFunction: Property<(String, String) -> String>

        @TaskAction
        fun generate() {
            val inputAsString = inputFile.get().asFile.readText()
            val inputAsMap = JsonSlurper().parseText(inputAsString) as Map<String, String>
            val parameters = inputAsMap
                .filter { (key, _) -> key.startsWith("#") }
                .mapKeys { (key, _) -> key.removePrefix("#") }
            val actualValuePostProcessFunction = valuePostProcessFunction.orNull
            val outputAsMap = inputAsMap
                .filter { (key, _) -> !key.startsWith("#") }
                .mapValues { (fullKey, value) ->
                    if (value != "=") return@mapValues value
                    val key = fullKey.substring(fullKey.lastIndexOf('.') + 1)
                    val stringBuilder = TreeMap<Double, MutableList<String>>()
                    var index = 0
                    while (index < key.length) {
                        val bestParameter = parameters
                            .filter { parameter -> key.startsWith(parameter.key, index) }
                            .maxByOrNull { it.key.length }
                        if (bestParameter == null) throw IllegalStateException(
                            "No sufficient parameters to decode parametrized key '$key' (at index $index) in '${inputFile.get().asFile.path}'"
                        )
                        val positionIndex = bestParameter.key.indexOf('<').takeIf { it != -1 }
                            ?: bestParameter.key.indexOf('>')
                        val position = if (positionIndex == -1)
                            0.0
                        else {
                            val positionValue =
                                if (positionIndex == 0) 1.0 else
                                    bestParameter.key.substring(0, positionIndex).toDouble()
                            val positionSign = if (bestParameter.key[positionIndex] == '<') -1.0 else 1.0
                            positionValue * positionSign
                        }
                        stringBuilder.computeIfAbsent(position) { ArrayList() } += bestParameter.value
                        index += bestParameter.key.length
                    }
                    val result = stringBuilder.entries
                        .joinToString("") { (_, value) -> value.joinToString("") }
                    return@mapValues actualValuePostProcessFunction?.let { it(fullKey, result) } ?: result
                }
            val outputAsString = JsonOutput.prettyPrint(JsonOutput.toJson(outputAsMap), true)
            outputFile.get().asFile.apply {
                parentFile.mkdirs()
                writeText(outputAsString)
            }
        }

    }

    abstract class GenerateLangFileByMappingOtherLangFileTask : GenerateLangFileTask() {

        @get:InputFile
        val inputFile = project.objects.fileProperty()

        @get:Input
        abstract val valueFunction: Property<(String, String) -> String>

        @TaskAction
        fun generate() {
            val inputAsString = inputFile.get().asFile.readText()
            val inputAsMap = JsonSlurper().parseText(inputAsString) as Map<String, String>
            val actualValueFunction = valueFunction.get()
            val outputAsMap = inputAsMap.mapValues { (key, value) ->
                actualValueFunction(key, value)
            }
            val outputAsString = JsonOutput.prettyPrint(JsonOutput.toJson(outputAsMap), true)
            outputFile.get().asFile.apply {
                parentFile.mkdirs()
                writeText(outputAsString)
            }
        }

    }

    abstract class GenerateLangFileByCopyingOtherLangFileTask : GenerateLangFileTask() {

        @get:InputFile
        val inputFile = project.objects.fileProperty()

        @TaskAction
        fun generate() {
            outputFile.get().asFile.apply {
                parentFile.mkdirs()
                writeText(inputFile.get().asFile.readText())
            }
        }

    }

    abstract class GenerateLangFileByMergingOtherLangFilesTask : GenerateLangFileTask() {

        @get:InputFile
        val inputFile1 = project.objects.fileProperty()

        @get:InputFile
        val inputFile2 = project.objects.fileProperty()

        @TaskAction
        fun generate() {
            val inputAsString1 = inputFile1.get().asFile.readText()
            val inputAsMap1 = JsonSlurper().parseText(inputAsString1) as Map<String, String>
            val inputAsString2 = inputFile2.get().asFile.readText()
            val inputAsMap2 = JsonSlurper().parseText(inputAsString2) as Map<String, String>
            val outputAsMap = (inputAsMap1.keys + inputAsMap2.keys).associateWith { key ->
                if (inputAsMap2.containsKey(key)) inputAsMap2[key] else inputAsMap1[key]
            }
            val outputAsString = JsonOutput.prettyPrint(JsonOutput.toJson(outputAsMap), true)
            outputFile.get().asFile.apply {
                parentFile.mkdirs()
                writeText(outputAsString)
            }
        }

    }

    fun parametrizedLangFile(locale: String) =
        layout.projectDirectory.file("src/langfiles/parametrized/$locale.json")

    fun overridesLangFile(locale: String) =
        layout.projectDirectory.file("src/langfiles/overrides/$locale.json")

    fun generatedLangFile(locale: String) =
        layout.buildDirectory.file("generated/langfiles/resource_pack/assets/minecraft/lang/$locale.json")

    // Process parameters
    val generateDeDe =
        register<GenerateLangFileFromParametrizedLangFileTask>("generateDeDe") {
            inputFile = parametrizedLangFile("de_de")
            outputFile = generatedLangFile("de_de")
        }
    val generateEnPt =
        register<GenerateLangFileFromParametrizedLangFileTask>("generateEnPt") {
            inputFile = parametrizedLangFile("en_pt")
            outputFile = generatedLangFile("en_pt")
        }
    val generateEnUs =
        register<GenerateLangFileFromParametrizedLangFileTask>("generateEnUs") {
            inputFile = parametrizedLangFile("en_us")
            outputFile = generatedLangFile("en_us")
        }
    val generateFrFr =
        register<GenerateLangFileFromParametrizedLangFileTask>("generateFrFr") {
            inputFile = parametrizedLangFile("fr_fr")
            outputFile = generatedLangFile("fr_fr")
            valuePostProcessFunction = { _, value -> value.replaceFirstChar { it.uppercase() } }
        }
    val generateJaJp =
        register<GenerateLangFileFromParametrizedLangFileTask>("generateJaJp") {
            inputFile = parametrizedLangFile("ja_jp")
            outputFile = generatedLangFile("ja_jp")
        }
    val generateLolUs =
        register<GenerateLangFileFromParametrizedLangFileTask>("generateLolUs") {
            inputFile = parametrizedLangFile("lol_us")
            outputFile = generatedLangFile("lol_us")
        }
    val generateNlNl =
        register<GenerateLangFileFromParametrizedLangFileTask>("generateNlNl") {
            inputFile = parametrizedLangFile("nl_nl")
            outputFile = generatedLangFile("nl_nl")
        }
    val generateZhTw =
        register<GenerateLangFileFromParametrizedLangFileTask>("generateZhTw") {
            inputFile = parametrizedLangFile("zh_tw")
            outputFile = generatedLangFile("zh_tw")
        }

    // Substitute
    val generateEnGb =
        register<GenerateLangFileByMergingOtherLangFilesTask>("generateEnGb") {
            dependsOn(generateEnUs)
            inputFile1 = generatedLangFile("en_us")
            inputFile2 = overridesLangFile("en_gb")
            outputFile = generatedLangFile("en_gb")
        }

    // Turn text upside down
    val generateEnUd =
        register<GenerateLangFileByMappingOtherLangFileTask>("generateEnUd") {
            dependsOn(generateEnGb)
            inputFile = generatedLangFile("en_gb")
            outputFile = generatedLangFile("en_ud")
            valueFunction = { _, value ->
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

    // Traditional to simplified
    val generateZhCn =
        register<GenerateLangFileByMappingOtherLangFileTask>("generateZhCn") {
            dependsOn(generateZhTw)
            inputFile = generatedLangFile("zh_tw")
            outputFile = generatedLangFile("zh_cn")
            valueFunction = { _, value -> ZhConverterUtil.toSimple(value) }
        }

    // Simple copy
    val generateEnAu =
        register<GenerateLangFileByCopyingOtherLangFileTask>("generateEnAu") {
            dependsOn(generateEnGb)
            inputFile = generatedLangFile("en_gb")
            outputFile = generatedLangFile("en_au")
        }
    val generateEnCa =
        register<GenerateLangFileByCopyingOtherLangFileTask>("generateEnCa") {
            dependsOn(generateEnGb)
            inputFile = generatedLangFile("en_gb")
            outputFile = generatedLangFile("en_ca")
        }
    val generateEnNz =
        register<GenerateLangFileByCopyingOtherLangFileTask>("generateEnNz") {
            dependsOn(generateEnGb)
            inputFile = generatedLangFile("en_gb")
            outputFile = generatedLangFile("en_nz")
        }
    val generateFrCa =
        register<GenerateLangFileByCopyingOtherLangFileTask>("generateFrCa") {
            dependsOn(generateFrFr)
            inputFile = generatedLangFile("fr_fr")
            outputFile = generatedLangFile("fr_ca")
        }
    val generateNlBe =
        register<GenerateLangFileByCopyingOtherLangFileTask>("generateNlBe") {
            dependsOn(generateNlNl)
            inputFile = generatedLangFile("nl_nl")
            outputFile = generatedLangFile("nl_be")
        }
    val generateZhHk =
        register<GenerateLangFileByCopyingOtherLangFileTask>("generateZhHk") {
            dependsOn(generateZhTw)
            inputFile = generatedLangFile("zh_tw")
            outputFile = generatedLangFile("zh_hk")
        }

    processResources {

        // Generated lang files
        dependsOn(
            generateDeDe,
            generateEnAu,
            generateEnCa,
            generateEnGb,
            generateEnNz,
            generateEnPt,
            generateEnUs,
            generateFrCa,
            generateFrFr,
            generateJaJp,
            generateLolUs,
            generateNlBe,
            generateNlNl,
            generateZhCn,
            generateZhHk,
            generateZhTw,
            generateEnUd,
            generateZhCn
        )

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
        java.srcDir(layout.projectDirectory.dir("common-with-datagen/src/main/java"))
        resources.srcDir(layout.projectDirectory.dir("common-with-datagen/src/main/resources"))
        resources.srcDir(layout.buildDirectory.dir("generated/langfiles"))
        java.srcDir(layout.projectDirectory.dir("src/fake-fabric-stubs/java"))
        resources.srcDir(layout.projectDirectory.dir("src/fake-fabric-stubs/resources"))
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
        "tuff"
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
