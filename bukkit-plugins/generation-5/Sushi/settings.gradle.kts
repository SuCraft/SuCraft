pluginManagement {
    repositories {
        maven {
            name = "Paper"
            url = uri("https://repo.papermc.io/repository/maven-public/")
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

includeBuild("../../../minecraft-server-software") {
    dependencySubstitution {
        substitute(module("org.sucraft.suki:suki-api"))
            .using(project(":suki-api"))
        substitute(module("org.sucraft.suki:suki-server"))
            .using(project(":suki-server"))
    }
}

rootProject.name = "Sushi"

includeBuild("datagen")
