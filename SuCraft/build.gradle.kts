/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import de.undercouch.gradle.tasks.download.Download
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Plugins

plugins {
	kotlin("jvm") version "1.7.0"
	java
	id("com.github.johnrengelman.shadow") version "7.1.2"
	id("maven-publish")
	id("net.minecrell.plugin-yml.bukkit") version "0.5.1"
	id("de.undercouch.download") version "3.4.3"
}

tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "17"
}

// Project

val projectGroup = "org.sucraft"
val projectVersion = "0.2"
val projectDescription = "SuCraft is a survival Minecraft server."
val projectName = rootProject.name
val projectNameLowerCase = projectName.toLowerCase()

group = projectGroup
version = projectVersion
description = projectDescription

// Repositories

repositories {
	mavenCentral()
	//mavenLocal()
	maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
	maven { url = uri("https://maven.enginehub.org/repo/") }
	maven { url = uri("https://repo.dmulloy2.net/repository/public/") }
	maven { url = uri("https://libraries.minecraft.net") }
	maven { url = uri("https://repo.codemc.org/repository/maven-public/") }
	maven { url = uri("https://repo.viaversion.com") }
}

// Download library jars

val libsFolder = "libs"

var downloadLibraryJarTasks: Array<String> = emptyArray()
var downloadedLibraryJarPathsForImplementation: Array<String> = emptyArray()
var downloadedLibraryJarPathsForCompileOnly: Array<String> = emptyArray()

fun downloadLibraryJar(
	name: String,
	url: String,
	filename: String,
	implementation: Boolean,
	mavenGroupId: String,
	mavenArtifactId: String,
	mavenVersion: String
) {
	val taskName = "download$name"
	val innerTaskName = "download${name}Inner"
	val jarPath = "$libsFolder/$filename"
	task<DefaultTask>(taskName) {
		dependsOn(innerTaskName)
		task<Download>(innerTaskName) {
			src(url)
			dest(File(jarPath))
			overwrite(false)
		}
	}
//	task<DefaultTask>("installDownloaded$name") {
//		dependsOn(taskName)
//		doLast {
//			exec {
//				executable("mvn")
//				args(
//					"install:install-file",
//					"\"–Dfile=$rootDir/$jarPath\"",
//					"\"-DgroupId=$mavenGroupId\"",
//					"\"-DartifactId=$mavenArtifactId\"",
//					"\"-Dversion=$mavenVersion\""
//				)
//			}
//			println("$rootDir/$jarPath")
//		}
//	}
	val jarFile = layout.projectDirectory.file(jarPath)
	val jarArtifact = artifacts.add("archives", jarFile.asFile) {
		type = "jar"
		builtBy(taskName)
	}
	publishing {
		publications {
			create<MavenPublication>(name) {

				artifact(jarArtifact)

				groupId = mavenGroupId
				artifactId = mavenArtifactId
				version = mavenVersion
			}
		}
	}
	downloadLibraryJarTasks += arrayOf(taskName)
	if (implementation)
		downloadedLibraryJarPathsForImplementation += arrayOf(jarPath)
	else
		downloadedLibraryJarPathsForCompileOnly += arrayOf(jarPath)
}

val paperMinecraftVersion = "1.19.2"
val paperBuildGitVersion = "20f4a06fa"
val paperBuildVersion = "martijn-$paperMinecraftVersion-$paperBuildGitVersion"

val paperStubFilename =
	"paper-$paperBuildVersion.jar"
val paperStubURL =
	"https://screpo.000webhostapp.com/martijn-paper-$paperMinecraftVersion-$paperBuildGitVersion-stub.jar"

val paperAPIFilename =
	"paper-api-$paperBuildVersion.jar"
val paperAPIURL =
	"https://screpo.000webhostapp.com/martijn-paper-api-$paperMinecraftVersion-R0.1-SNAPSHOT-$paperBuildGitVersion.jar"

downloadLibraryJar(
	"PaperStub",
	paperStubURL,
	paperStubFilename,
	false,
	"io.papermc.paper",
	"paper-stub",
	paperBuildVersion
)
downloadLibraryJar(
	"PaperAPI",
	paperAPIURL,
	paperAPIFilename,
	false,
	"io.papermc.paper",
	"paper-api",
	paperBuildVersion
)

// Compile and build tasks

tasks.getByName<Task>("compileKotlin") {
	dependsOn("clean", *downloadLibraryJarTasks)
}

tasks.getByName<Task>("build") {
	dependsOn("clean", *downloadLibraryJarTasks)
}

// Dependencies

val jsonVersion = "20220320"
val snakeYAMLVersion = "1.30"
val joorVersion = "0.9.14"
val apacheCommonsMathVersion = "3.6.1"
val fastutilVersion = "8.5.8"
val adventureAPIVersion = "4.11.0"
val adventureTextMiniMessageVersion = "4.11.0"
val adventureTextSerializerPlainVersion = "4.11.0"
val paperAPIVersion = "1.19.2-R0.1-SNAPSHOT"

@Suppress("SpellCheckingInspection")
dependencies {
	library(kotlin("stdlib"))
	library("org.jetbrains.kotlin:kotlin-reflect:1.7.0")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
	compileOnly("io.netty:netty-all:4.1.77.Final")
	library("org.json:json:$jsonVersion")
	library("org.yaml:snakeyaml:$snakeYAMLVersion")
	library("org.jooq:joor:$joorVersion")
	library("org.apache.commons:commons-math3:$apacheCommonsMathVersion")
	compileOnly("it.unimi.dsi:fastutil:$fastutilVersion")
	testImplementation("it.unimi.dsi:fastutil:$fastutilVersion")
	compileOnly("net.kyori:adventure-api:$adventureAPIVersion")
	compileOnly("net.kyori:adventure-text-minimessage:$adventureTextMiniMessageVersion")
	compileOnly("net.kyori:adventure-text-serializer-plain:$adventureTextSerializerPlainVersion")
//	compileOnly("io.papermc.paper:paper-api:$paperAPIVersion")
	compileOnly("io.papermc.paper:paper-mojangapi:$paperAPIVersion")
//	compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.3.0-SNAPSHOT")
	compileOnly("com.intellectualsites.paster:Paster:1.1.4")
	compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Core:2.3.0")
	compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Bukkit:2.3.0") { isTransitive = false }
	compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.1.0-SNAPSHOT")
	// Commented out while ProtocolLib dependency is unnecessary
//	compileOnly("com.comphenix.protocol:ProtocolLib:5.0.0-SNAPSHOT")
//	compileOnly("com.comphenix.packetwrapper:PacketWrapper:1.13-R0.1-SNAPSHOT")
	compileOnly("com.mojang:brigadier:1.0.18")
	compileOnly("dev.jorel:commandapi-core:8.5.1")
	compileOnly("com.viaversion:viaversion-api:4.1.1")
	library("com.github.shynixn.mccoroutine:mccoroutine-bukkit-api:2.4.0")
	library("com.github.shynixn.mccoroutine:mccoroutine-bukkit-core:2.4.0")
	library("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")
	library("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.6.2")
	downloadedLibraryJarPathsForImplementation.forEach {
		implementation(files(it))
	}
	downloadedLibraryJarPathsForCompileOnly.forEach {
		compileOnly(files(it))
	}
}

// Shade

val shadowJarArchiveFilename = "${project.name}-${project.version}.jar"
val shadowJarShadedPackage = "org.sucraft.shaded"
fun ShadowJar.relocate(`package`: String) =
	relocate(`package`, "$shadowJarShadedPackage.$`package`")

tasks {
	shadowJar {
		dependsOn("build", "compileKotlin")
		archiveFileName.set(shadowJarArchiveFilename)
		dependencies {
			exclude(dependency("org.jetbrains.kotlin::"))
			exclude(dependency("org.intellij.lang::"))
			exclude(dependency("org.jetbrains::"))
			exclude(dependency("kotlin::"))
		}
	}
}

// Test

tasks.getByName<Test>("test") {
	useJUnitPlatform()
}

// Bukkit plugin

bukkit {
	author = "Martijn Muijsers"
	website = "sucraft.org"
	main = "org.sucraft.main.SuCraftPlugin"
	apiVersion = "1.19"
	depend = listOf(
		"CommandAPI",
		"PacketWrapper",
		"PermissionsBukkit",
		"ProtocolLib",
		"WorldGuard"
	)
	softDepend = listOf(
		"AsyncWorldEdit",
		"AuthMe",
		"BKCommonLib",
		"ColoredSigns",
		"CoreProtect",
		"CrazyAdvancementsAPI",
		"CreativeFlySuCraft",
		"FartherViewDistance",
		"FastAsyncWorldEdit",
		"FastLogin",
		"Geyser-Spigot",
		"GrimAC",
		"ImageOnMap",
		"InventoryRollback",
		"InventoryRollbackPlus",
		"LightCleaner",
		"LWC",
		"Multiverse-Core",
		"Multiverse-Inventories",
		"Multiverse-NetherPortals",
		"Multiverse-Portals",
		"NoCheatPlus",
		"PlaceHolderAPI",
		"PlugMan",
		"RealBlockOldUsers",
		"Redye",
		"RoyalGrenadier",
		"ViaBackwards",
		"ViaRewind",
		"ViaVersion",
		"ViaVersionStatus",
		"Votifier",
		"WebConsole",
		"WorldEdit",
		"WorldGuard"
	)
}

publishing {
	publications {
		create<MavenPublication>("Plugin") {
			groupId = projectGroup
			artifactId = projectNameLowerCase
			version = projectVersion

			from(components["java"])
		}
	}
}

println(components.names)

// Copy to test server

//@Suppress("SpellCheckingInspection")
//val testServerPath = """"""
//
//tasks.register("jarAndCopyToTestServer") {
//	dependsOn("shadowJar")
//	copy {
//		onlyIf { testServerPath.isNotBlank() }
//		from("$buildDir/libs/$shadowJarArchiveFilename")
//		into("$testServerPath/plugins")
//	}
//}
