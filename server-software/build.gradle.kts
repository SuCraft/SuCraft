import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import kotlin.io.path.copyToRecursively
import kotlin.io.path.deleteRecursively

plugins {
    id("io.papermc.paperweight.patcher") version "2.0.0-beta.24"
}

paperweight {
    upstreams.register("spout") {

        repo = project.file(".gradle/caches/repo/${providers.gradleProperty("spoutRepoSubdirectory").get()}/.git").path
        ref = "HEAD"

        patchFile {
            path = "spout-server/build.gradle.kts"
            outputFile = file("suki-server/build.gradle.kts")
            patchFile = file("suki-server/build.gradle.kts.patch")
        }
        patchFile {
            path = "spout-api/build.gradle.kts"
            outputFile = file("suki-api/build.gradle.kts")
            patchFile = file("suki-api/build.gradle.kts.patch")
        }

        patchRepo("paperApi") {
            upstreamPath = "paper-api"
            patchesDir = file("suki-api/paper-patches")
            outputDir = file("paper-api")
        }

        patchDir("spoutApi") {
            upstreamPath = "spout-api"
            excludes = listOf("build.gradle.kts", "build.gradle.kts.patch", "paper-patches")
            patchesDir = file("suki-api/spout-patches")
            outputDir = file("spout-api")
        }

    }
}

val paperMavenPublicUrl = "https://repo.papermc.io/repository/maven-public/"

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")

    extensions.configure<JavaPluginExtension> {
        toolchain {
            languageVersion = JavaLanguageVersion.of(25)
        }
    }

    repositories {
        mavenCentral()
        maven(paperMavenPublicUrl)
    }

    tasks.withType<AbstractArchiveTask>().configureEach {
        isPreserveFileTimestamps = false
        isReproducibleFileOrder = true
    }
    tasks.withType<JavaCompile>().configureEach {
        options.encoding = Charsets.UTF_8.name()
        options.release = 25
        options.isFork = true
        // Suki start - Project setup - Hide annoying compilation warnings
        options.compilerArgs.addAll(
            listOf(
                "-Xlint:-dep-ann",
                "-Xlint:-deprecation",
                "-Xlint:-module",
                "-Xlint:-removal",
            )
        )
        // Suki end - Project setup - Hide annoying compilation warnings
    }
    tasks.withType<Javadoc>().configureEach {
        options.encoding = Charsets.UTF_8.name()
    }
    tasks.withType<ProcessResources>().configureEach {
        filteringCharset = Charsets.UTF_8.name()
    }
    tasks.withType<Test>().configureEach {
        testLogging {
            showStackTraces = true
            exceptionFormat = TestExceptionFormat.FULL
            events(TestLogEvent.STANDARD_OUT)
        }
    }

    extensions.configure<PublishingExtension> {
        repositories {
            /*
            maven("https://repo.papermc.io/repository/maven-snapshots/") {
                name = "paperSnapshots"
                credentials(PasswordCredentials::class)
            }
             */
        }
    }
}

tasks.register("clean") {
    group = "build"
    doLast {
        delete(project.file(".gradle/caches/repo"))
    }
}

tasks.register("viewSpoutPaperFolder") {
    group = "paperweight internal"
    doLast doLast@{

        val sub = providers.gradleProperty("spoutRepoSubdirectory").get()
        val apiCommonSourceFolders = listOf("common/src/main", "spout/paper/spout-api/src/not-api")
        val serverCommonSourceFolders = listOf(
            "common/minecraft/src/main",
            "spout/common/src/main",
            "spout/paper/spout-server/src/api",
            "spout/paper/spout-server/src/api-impl"
        )
        val repoDir = project.file(".gradle/caches/repo")
        val repoDirSub = project.file(".gradle/caches/repo/$sub")
        val repo = providers.gradleProperty("spoutRepo").get()
        val ref = providers.gradleProperty("spoutRef").get()

        fun runGit(vararg args: String, inSub: Boolean = false): String {
            val process = ProcessBuilder("git", *args)
                .directory(if (inSub) repoDirSub else repoDir)
                .redirectErrorStream(true)
                .start()

            val output = process.inputStream.bufferedReader().readText()
            val exitCode = process.waitFor()

            if (exitCode != 0) {
                throw GradleException(
                    "Git command failed (exit code $exitCode): " +
                        "git ${args.joinToString(" ")}\n$output"
                )
            }

            return output
        }

        // Check if still up-to-date
        if (repoDir.resolve(".git").exists()) {
            val currentRef = runGit("rev-parse", "HEAD")
            if (currentRef.trim() == ref.trim()) {
                println("Up-to-date")
                return@doLast
            }
        }

        // Delete the old sub-Git
        repoDirSub.resolve(".git").delete()

        if (!repoDir.resolve(".git").exists()) {
            // First run: create the repository
            repoDir.mkdirs()
            runGit(
                "clone",
                "--depth=1",
                "--filter=blob:none",
                "--sparse",
                repo,
                "."
            )
        } else {
            // Reuse the existing repository
            runGit("fetch", "--depth=1", "--all", "--tags", "--prune")
        }

        // Make sure Git's working tree is in a clean state
        runGit("reset", "--hard")
        runGit("clean", "-fd")

        // Check out the requested ref
        runGit("checkout", "--force", ref)

        // Configure sparse checkout to contain spout/paper
        runGit("sparse-checkout", "add", sub)
        apiCommonSourceFolders.forEach { runGit("sparse-checkout", "add", it) }
        serverCommonSourceFolders.forEach { runGit("sparse-checkout", "add", it) }

        // Create a Git repository
        if (!repoDirSub.resolve(".git").exists()) {
            runGit("init", inSub = true)
        }

        // Copy Gradle files
        repoDir.resolve("gradlew").copyTo(repoDirSub.resolve("gradlew"))
        repoDir.resolve("gradlew.bat").copyTo(repoDirSub.resolve("gradlew.bat"))

        @OptIn(kotlin.io.path.ExperimentalPathApi::class)
        fun copyCommonSources(folders: List<String>, directory: String) {
            folders.forEach {
                val source = repoDir.resolve(it).toPath()
                source.copyToRecursively(
                    repoDirSub.resolve(directory).resolve("src/main").toPath(),
                    followLinks = false,
                    overwrite = false
                )
                source.deleteRecursively()
            }
        }

        // Copy common sources
        copyCommonSources(apiCommonSourceFolders, "spout-api")
        copyCommonSources(serverCommonSourceFolders, "spout-server")

        // Commit
        runGit("add", ".", inSub = true)
        runGit("commit", "-a", "-m", "\"spout\"", "--allow-empty", inSub = true)

    }
}

tasks.configureEach {
    if (name == "checkoutSpoutRepo") {
        dependsOn("viewSpoutPaperFolder")
    }
}
