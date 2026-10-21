pluginManagement {
    repositories {
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        id("net.fabricmc.fabric-loom") version providers.gradleProperty("loomVersion")
    }
}

includeBuild("../../../../minecraft-server-software") {
    dependencySubstitution {
        substitute(module("org.sucraft.suki:suki-api"))
            .using(project(":suki-api"))
        substitute(module("org.sucraft.suki:suki-server-filtered"))
            .using(project(":suki-server-filtered"))
    }
}

rootProject.name = "Sushi-datagen"
