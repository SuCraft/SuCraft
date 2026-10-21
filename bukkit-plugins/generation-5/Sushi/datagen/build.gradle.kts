plugins {
    id("java-library")
    id("net.fabricmc.fabric-loom")
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public")
    maven("https://jitpack.io")
}

dependencies {
    implementation("org.sucraft.suki:suki-api:26.3-R0.1-SNAPSHOT")
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

sourceSets {
    main {
        java.srcDir(layout.projectDirectory.dir("../common-with-datagen/src/main/java"))
        resources.srcDir(layout.projectDirectory.dir("../common-with-datagen/src/main/resources"))
        java.srcDir(layout.projectDirectory.dir("src/fake-paper-stubs/java"))
        resources.srcDir(layout.projectDirectory.dir("src/fake-paper-stubs/resources"))
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
