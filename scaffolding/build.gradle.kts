import org.gradle.internal.extensions.stdlib.capitalized
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import kotlin.io.path.createParentDirectories
import kotlin.io.path.exists
import kotlin.io.path.fileSize
import kotlin.io.path.getLastModifiedTime

val buildDependencyTasks = listOf(
    ":suki:suki-server:createBundlerJar"
).map { gradleTask ->

    fun String.toCamelCase(): String =
        split("-")
            .mapIndexed { index, part ->
                if (index == 0) part
                else part.replaceFirstChar { it.uppercase() }
            }
            .joinToString("")

    val gradleTaskSplit = gradleTask.split(":")
    val newTaskName =
        "${gradleTaskSplit[gradleTaskSplit.size - 2].toCamelCase()}${gradleTaskSplit.last().capitalized()}"
    tasks.register<Exec>(newTaskName) {
        group = "prepare dependencies"
        workingDir = file("..")
        commandLine("./gradlew", gradleTask)
    }

}

abstract class TargetFile(val targetPath: String) {

    fun targetFile() = project.layout.projectDirectory.file("../run/$targetPath").asFile.toPath()

    open fun needToCopy() =
        !targetFile().exists()

    abstract fun copy()

}

fun projectFile(targetPath: String, sourcePath: String) = object : TargetFile(targetPath) {

    fun sourceFile() = project.layout.projectDirectory.file(sourcePath).asFile.toPath()

    override fun needToCopy(): Boolean {
        if (super.needToCopy()) return true
        val targetFile = targetFile()
        val sourceFile = sourceFile()
        if (sourceFile.fileSize() != targetFile.fileSize()) return true;
        if (sourceFile.getLastModifiedTime() != targetFile.getLastModifiedTime()) return true;
        return false;
    }

    override fun copy() {
        Files.copy(
            sourceFile(),
            targetFile(),
            StandardCopyOption.REPLACE_EXISTING,
            StandardCopyOption.COPY_ATTRIBUTES
        )
    }

}

fun rootProjectFile(targetPath: String, sourcePath: String) = projectFile(targetPath, "../$sourcePath")

val targetFiles = listOf(
    rootProjectFile("minecraft-server/server.jar", "minecraft-server-software/suki-server/build/libs/suki-bundler-26.3-R0.1.jar")
)

tasks.register("update") {
    group = "scaffolding"
    dependsOn(buildDependencyTasks)
    doFirst {
        for (targetFile in targetFiles) {
            if (targetFile.needToCopy()) {
                targetFile.targetFile().createParentDirectories()
                targetFile.copy()
            }
        }
    }
}

tasks.register("run") {
    group = "scaffolding"
    doFirst {

    }
}
