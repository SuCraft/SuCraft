import java.net.URI
import java.net.URL
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.zip.ZipFile

plugins {
    id("java-library")
    id("net.fabricmc.fabric-loom")
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    minecraft("com.mojang:minecraft:${providers.gradleProperty("mcVersion").get()}")
    implementation("net.fabricmc:fabric-loader:${providers.gradleProperty("fabricLoaderVersion").get()}")
    implementation("net.fabricmc.fabric-api:fabric-api:${providers.gradleProperty("fabricApiVersion").get()}")
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(25)
}

loom {
    mods {
        // The name has to be "sucraft" (including in fabric.mod.json),
        // otherwise data generation refuses to generate some files
        // because some parts of the inner Fabric API implementation
        // are automatically limited to generating files with the same namespace
        // as the mod id
        register("sucraft") {
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

tasks {

    getByName("runDatagen") {
        doFirst {
            project.layout.projectDirectory.dir("src/main/generated/assets").asFile.deleteRecursively()
            project.layout.projectDirectory.dir("src/main/generated/data").asFile.deleteRecursively()
        }
    }

}
