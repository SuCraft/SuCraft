import java.net.URI
import java.nio.file.Files
import java.nio.file.StandardCopyOption

plugins {
    id("java-library")
    id("com.gradleup.shadow") version "9.4.1"
    id("net.fabricmc.fabric-loom")
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
}

configurations {
    create("shadowInclude")
}

dependencies {
    compileOnly("com.github.ModernSpout:Spout:${project.providers.gradleProperty("spoutVersion").get()}")
    minecraft("com.mojang:minecraft:${providers.gradleProperty("apiVersion").get()}")
    implementation("net.fabricmc:fabric-loader:${providers.gradleProperty("fabricLoaderVersion").get()}")
    implementation("net.fabricmc.fabric-api:fabric-api:${providers.gradleProperty("fabricApiVersion").get()}")
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(25)
}

loom {
    mods {
        register("sushi") {
            sourceSet(sourceSets.main.get())
        }
    }

    accessWidenerPath = file("src/main/resources/sushi.classtweaker")
}

fabricApi {
    configureDataGeneration {
        addToResources = true
        client = true
    }
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

tasks {

    build {
        dependsOn(shadowJar)
    }

    getByName("runDatagen") {
        doFirst {
            project.layout.projectDirectory.dir("src/main/generated/data").asFile.deleteRecursively()
        }
    }

    withType<Jar> {
        // Include generated resources
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        from("src/main/generated") {
            exclude("data/**")
        }
        from("src/main/generated/data") {
            into("data_pack/data")
        }
        dependsOn("runDatagen")
        includeEmptyDirs = false
        // Don't include data generation sources
        exclude("org/sucraft/sushi/datageneration/**")
        exclude("fabric.mod.json")
        if (name == "shadowJar") {
            exclude("data/**")
        }
    }

    shadowJar {
        // dependsOn("jar")
        configurations = listOf(project.configurations.getByName("shadowInclude"))
    }

    processResources {

        // Include generated resources
        // filesMatching("data/**") {
        //     path = path.replaceFirst("data/", "data_pack/data/")
        // }

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

    // Task to download the Spout server JAR for the runTask
    register("downloadSpoutServer") {
        group = "spout"; notCompatibleWithConfigurationCache(""); doFirst {
        serverDir.mkdirs(); pluginDir.mkdirs(); URI(
        "https://github.com/ModernSpout/Spout/releases/download/${
            project.providers.gradleProperty("spoutVersion").get()
                .let { "$it/spout-${project.providers.gradleProperty("apiVersion").get()}-R$it" }
        }.jar").toURL().openStream().use {
        Files.copy(it, serverDir.resolve("server.jar").toPath(), StandardCopyOption.REPLACE_EXISTING)
    }
    }
    }
    // Task to spin up a Spout server with the plugin
    register("runSpoutServer", JavaExec::class) {
        group = "spout"; notCompatibleWithConfigurationCache("")
        dependsOn("shadowJar")
        if (!serverDir.resolve("server.jar").exists()) dependsOn("downloadSpoutServer")
        doFirst {
            pluginDir.resolve("${project.name}.jar").delete()
            Files.copy(
                layout.buildDirectory.file("libs/${project.name}-${version}.jar").get().asFile.toPath(),
                pluginDir.resolve("${project.name}.jar").toPath()
            )
        }
        classpath = files(serverDir.resolve("server.jar")); minHeapSize = "2G"; maxHeapSize = "2G"
        workingDir = serverDir; jvmArgs = listOf("-Dcom.mojang.eula.agree=true", "-Dspout.server.paper.enabled=true")
        args = listOf("--nogui"); standardInput = System.`in`
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
