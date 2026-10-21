dependencies {
    api(project(":suki-server"))
}

tasks.jar {
    from(project(":suki-server").sourceSets.main.get().output) {
        exclude("net/minecraft/**", "com/mojang/**")
    }
}

configure<PublishingExtension> {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

val outgoingVariants = arrayOf("runtimeElements", "apiElements")
val mainCapability = "${project.group}:${project.name}:${project.version}"
configurations {
    val outgoing = outgoingVariants.map { named(it) }
    for (config in outgoing) {
        config {
            attributes {
                attribute(io.papermc.paperweight.util.mainCapabilityAttribute, mainCapability)
            }
            outgoing {
                capability(mainCapability)
                for (coordinates in arrayOf(
                    "org.sucraft.suki:suki-server-filtered",
                )) {
                    for (version in arrayOf(project.version)) {
                        val alternativeCapability = "$coordinates:$version"
                        if (alternativeCapability != mainCapability) capability(alternativeCapability)
                    }
                }
            }
        }
    }
}
