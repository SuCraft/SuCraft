rootProject.name = "sucraft-monorepo"

includeBuild("bukkit-plugins") { name = "plugins" }
includeBuild("minecraft-server-software") { name = "Suki" }
includeBuild("scaffolding")
