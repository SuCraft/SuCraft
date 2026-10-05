import org.gradle.internal.extensions.stdlib.capitalized
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.nio.file.attribute.PosixFilePermission
import kotlin.io.path.createParentDirectories
import kotlin.io.path.exists
import kotlin.io.path.fileSize
import kotlin.io.path.getLastModifiedTime
import kotlin.io.path.pathString

val buildDependencyTasks = listOf(
    ":suki:suki-server:createBundlerJar",
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

} + listOf(
    "bukkit-plugins/forked/CoreProtect",
    "bukkit-plugins/forked/PermissionsBukkit"
).map { mavenTask ->

    val newTaskName =
        "${mavenTask.split("/").last().replaceFirstChar(Char::lowercase)}Install"
    tasks.register<Exec>(newTaskName) {
        group = "prepare dependencies"
        workingDir = file("../$mavenTask")
        commandLine("mvn", "install")
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

fun rootProjectFile(targetPath: String, sourcePath: String) =
    projectFile(targetPath, "../$sourcePath")

fun rootProjectFile(filename: String, targetDirectory: String, sourceDirectory: String) =
    rootProjectFile("$targetDirectory/$filename", "$sourceDirectory/$filename")

val targetFiles = arrayListOf(
    rootProjectFile(
        "minecraft-server/plugins/CoreProtect.jar",
        "bukkit-plugins/forked/CoreProtect/target/CoreProtect-24.1-SuCraft.jar"
    ),
    rootProjectFile(
        "minecraft-server/plugins/PermissionsBukkit.jar",
        "bukkit-plugins/forked/PermissionsBukkit/target/bukkit-permissions-2.5-SuCraft.jar"
    ),
    rootProjectFile(
        "minecraft-server/server.jar",
        "minecraft-server-software/suki-server/build/libs/suki-bundler-26.3-R0.1.jar"
    )
)
val baseResourcesFolderName = "resources"
val secretResourcesFolderName = "secret-resources"
for (secret in arrayOf(false, true)) {
    val resourcesFolderName = if (secret) secretResourcesFolderName else baseResourcesFolderName
    val resourcesFolder = project.layout.projectDirectory.file(resourcesFolderName).asFile
    resourcesFolder.walkTopDown().toList().filter { it.isFile }.map { resourceFile ->
        val relativePath = resourceFile.relativeTo(resourcesFolder).path
        if (secret || !project.layout.projectDirectory.file("${secretResourcesFolderName}/${relativePath}").asFile.exists()) {
            targetFiles += projectFile(relativePath, "$resourcesFolderName/$relativePath")
        }
    }
}

tasks.register("update") {
    group = "scaffolding"
    dependsOn(buildDependencyTasks)
    doFirst {
        for (targetFile in targetFiles) {
            if (targetFile.needToCopy()) {
                logger.lifecycle("Placing target file: ${targetFile.targetFile().pathString}")
                targetFile.targetFile().createParentDirectories()
                targetFile.copy()
                if (targetFile.targetFile().pathString.endsWith(".sh") || targetFile.targetFile().pathString.endsWith(".jar")) {
                    // Make executable
                    logger.lifecycle("Making executable: ${targetFile.targetFile().pathString}")
                    val permissions = Files.getPosixFilePermissions(targetFile.targetFile()).toMutableSet()
                    permissions.add(PosixFilePermission.OWNER_EXECUTE)
                    permissions.add(PosixFilePermission.OTHERS_EXECUTE)
                    Files.setPosixFilePermissions(targetFile.targetFile(), permissions)
                }
            } else {
                logger.lifecycle("Skipping (already up-to-date): ${targetFile.targetFile().pathString}")
            }
        }
    }
}
