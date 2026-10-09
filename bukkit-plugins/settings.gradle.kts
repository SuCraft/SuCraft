rootProject.name = "sucraft-monorepo"

includeBuild("forked/CoreProtect") { name = "CoreProtect"}
includeBuild("forked/PermissionsBukkit") { name = "PermissionsBukkit"}
includeBuild("forked/WorldGuard") { name = "WorldGuard"}
includeBuild("generation-5/Sushi") { name = "Sushi" }
