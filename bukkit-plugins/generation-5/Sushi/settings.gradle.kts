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

includeBuild("../../..") {
    dependencySubstitution {
        substitute(module("org.sucraft.suki:suki-api"))
            .using(project(":Suki:suki-api"))
        substitute(module("org.sucraft.suki:suki-server"))
            .using(project(":Suki:suki-server"))
    }
}

rootProject.name = "Sushi"

includeBuild("datagen")
