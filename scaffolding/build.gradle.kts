import java.net.URI
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.nio.file.attribute.PosixFilePermission
import java.security.MessageDigest
import kotlin.io.path.createParentDirectories
import kotlin.io.path.exists
import kotlin.io.path.fileSize
import kotlin.io.path.getLastModifiedTime
import kotlin.io.path.inputStream
import kotlin.io.path.pathString
import kotlin.io.path.relativeTo
import kotlin.io.path.writeBytes

fun sha256(data: ByteArray): String {
    val digest = MessageDigest.getInstance("SHA-256")
    digest.update(data)
    return digest.digest().joinToString("") { "%02x".format(it) }
}

fun sha256(file: java.nio.file.Path) = sha256(file.inputStream().readAllBytes())

val lastUpdateGitRefFile: File = layout.projectDirectory.file(".gradle/caches/scaffolding/last_git_ref.txt").asFile

fun getCurrentGitRef(): String {
    val process = ProcessBuilder(
        "git", "rev-parse", "--short", "HEAD"
    ).directory(layout.projectDirectory.asFile.parentFile).start()
    val output = process.inputStream.bufferedReader().readText().trim()
    val exitCode = process.waitFor()
    if (exitCode == 0) return output.trim()
    throw IllegalStateException("Could not determine current git ref")
}

fun needsNewUpdateDueToGitRefChange() =
    !lastUpdateGitRefFile.exists() || lastUpdateGitRefFile.readText().trim() != getCurrentGitRef()

fun replaceLastUpdateGitRef() {
    lastUpdateGitRefFile.parentFile.mkdirs()
    lastUpdateGitRefFile.writeText(getCurrentGitRef())
}

val runDirectory: File = project.layout.projectDirectory.file("../run").asFile

abstract class FileToUpdate(
    val targetPath: String,
    val intendedSha256Hash: String? = null,
    val alwaysUpdate: Boolean = false
) {

    fun targetFile(): java.nio.file.Path = runDirectory.resolve(targetPath).toPath()

    open fun needToUpdate() =
        alwaysUpdate || !targetFile().exists()

    abstract fun update()

}

open class FileToUpdateFromSourceFile(
    targetPath: String,
    val sourcePath: String,
    intendedSha256Hash: String? = null,
    alwaysUpdate: Boolean = false
) : FileToUpdate(targetPath, intendedSha256Hash, alwaysUpdate) {

    fun sourceFile(): java.nio.file.Path = project.layout.projectDirectory.file(sourcePath).asFile.toPath()

    open fun createSourceFile() {}

    override fun update() {
        createSourceFile()
        Files.copy(
            sourceFile(),
            targetFile(),
            StandardCopyOption.REPLACE_EXISTING,
            StandardCopyOption.COPY_ATTRIBUTES
        )
    }

}

class FileToUpdateFromIncludedSourceFile(
    targetPath: String,
    sourcePath: String,
    alwaysCopy: Boolean = false
) : FileToUpdateFromSourceFile(targetPath, sourcePath, null, alwaysCopy) {

    override fun needToUpdate(): Boolean {
        if (super.needToUpdate()) return true
        val targetFile = targetFile()
        val sourceFile = sourceFile()
        if (sourceFile.fileSize() != targetFile.fileSize()) return true
        if (sourceFile.getLastModifiedTime() != targetFile.getLastModifiedTime()) return true
        return needsNewUpdateDueToGitRefChange()
    }

}

abstract class FileToUpdateByExecuting(
    targetPath: String,
    sourcePathFromProjectRoot: String,
    alwaysExecute: Boolean = false
) : FileToUpdateFromSourceFile(targetPath, "../$sourcePathFromProjectRoot", null, alwaysExecute) {

    override fun needToUpdate(): Boolean {
        if (super.needToUpdate()) return true
        val sourceFile = sourceFile()
        if (!sourceFile.exists()) return true
        val targetFile = targetFile()
        if (sourceFile.fileSize() != targetFile.fileSize()) return true
        if (sourceFile.getLastModifiedTime() != targetFile.getLastModifiedTime()) return true
        return needsNewUpdateDueToGitRefChange()
    }

}

class FileToUpdateByGradleTask(
    targetPath: String,
    sourcePathFromProjectRoot: String,
    val gradleTask: String,
    alwaysUpdate: Boolean = false
) : FileToUpdateByExecuting(targetPath, sourcePathFromProjectRoot, alwaysUpdate) {

    override fun createSourceFile() {
        logger.lifecycle("Performing Gradle task: $gradleTask")
        ProcessBuilder("./gradlew", gradleTask)
            .directory(project.layout.projectDirectory.asFile.parentFile)
            .inheritIO()
            .start()
            .waitFor()
            .let { if (it != 0) throw IllegalStateException("Gradle task \"$gradleTask\" was not successful") }
    }

}

class FileToUpdateByMavenPackage(
    targetPath: String,
    sourcePathFromProjectRoot: String,
    val mavenDirectory: String,
    alwaysUpdate: Boolean = false
) : FileToUpdateByExecuting(targetPath, sourcePathFromProjectRoot, alwaysUpdate) {

    override fun createSourceFile() {
        logger.lifecycle("Performing Maven package: $mavenDirectory")
        ProcessBuilder("mvn", "package")
            .directory(project.layout.projectDirectory.asFile.parentFile.resolve(mavenDirectory))
            .inheritIO()
            .start()
            .waitFor()
            .let { if (it != 0) throw IllegalStateException("Maven package task \"$mavenDirectory\" was not successful") }
    }

}

class FileToUpdateByDownloading(
    targetPath: String,
    val sourceURL: String,
    intendedSha256Hash: String? = null,
    alwaysUpdate: Boolean = false
) : FileToUpdate(targetPath, intendedSha256Hash, alwaysUpdate) {

    override fun needToUpdate(): Boolean {
        if (super.needToUpdate()) return true
        if (sha256(targetFile()) != intendedSha256Hash) return true
        return false
    }

    override fun update() {
        logger.lifecycle("Downloading: $sourceURL")
        val contents = URI.create(sourceURL).toURL().openStream().readAllBytes()
        if (intendedSha256Hash != null && sha256(contents) != intendedSha256Hash) {
            throw IllegalStateException("Downloaded file hash does not match expected for target file: $targetPath")
        }
        targetFile().writeBytes(contents)
    }

}

val targetFiles = arrayListOf(
    FileToUpdateByMavenPackage(
        "minecraft-server/plugins/CoreProtect.jar",
        "bukkit-plugins/forked/CoreProtect/target/CoreProtect-24.1-SuCraft.jar",
        "bukkit-plugins/forked/CoreProtect"
    ),
    FileToUpdateByMavenPackage(
        "minecraft-server/plugins/PermissionsBukkit.jar",
        "bukkit-plugins/forked/PermissionsBukkit/target/bukkit-permissions-2.5-SuCraft.jar",
        "bukkit-plugins/forked/PermissionsBukkit"
    ),
    FileToUpdateByGradleTask(
        "minecraft-server/plugins/Sushi.jar",
        "bukkit-plugins/generation-5/Sushi/build/libs/Sushi-0.1-SNAPSHOT-all.jar",
        ":plugins:Sushi:shadowJar",
        alwaysUpdate = true
    ),
    FileToUpdateByDownloading(
        "minecraft-server/plugins/ColorText.jar",
        "https://github.com/Dimianas/ColorText/releases/download/1.0/ColorText-1.0.jar",
        "0aeaebc97e798e36a77b79b9528cf459fa1b6c0b65820e97439ca3d8402cec32"
    ),
    FileToUpdateByDownloading(
        "minecraft-server/plugins/CommandAPI.jar",
        "https://github.com/CommandAPI/CommandAPI/releases/download/12.1.0/CommandAPI-12.1.0-Paper.jar",
        "ab48e55fd06264dd239cf015b501936cb50eb6c38d967fbffca6c91a48064049"
    ),
    FileToUpdateByDownloading(
        "minecraft-server/plugins/ImageFrame.jar",
        "https://hangarcdn.papermc.io/plugins/LOOHP/ImageFrame/versions/2026.1.5/PAPER/ImageFrame-2026.1.5.0.jar",
        "65b5c14eb6bd847f1cc0f4985efa95ab43d2f3e2f67dbaec2ecf3ea327fde9b1"
    ),
    FileToUpdateByDownloading(
        "minecraft-server/plugins/InventoryRollbackPlus.jar",
        "https://cdn.modrinth.com/data/XWKWAzd8/versions/eDsOfX6z/InventoryRollbackPlus-1.8.5.jar?mr_download_reason=standalone&mr_game_version=26.3&mr_loader=paper",
        "864825fa34fc2aeb10189f87afb767f418a6c0a32786f257730c5785ca9cf205"
    ),
    FileToUpdateByDownloading(
        "minecraft-server/plugins/Multiverse-Core.jar",
        "https://github.com/Multiverse/Multiverse-Core/releases/download/5.8.1/multiverse-core-5.8.1.jar",
        "bc3f34e2d747fdf024f44868747bb016b08fa11d98ccb4ffcac958f083151a83"
    ),
    FileToUpdateByDownloading(
        "minecraft-server/plugins/Multiverse-NetherPortals.jar",
        "https://github.com/Multiverse/Multiverse-NetherPortals/releases/download/5.1.0/multiverse-netherportals-5.1.0.jar",
        "a4b3750970b5b710aa942baaff07a8fcef56802cf286b9dcda7e69dd904492cc"
    ),
    FileToUpdateByDownloading(
        "minecraft-server/plugins/Multiverse-Portals.jar",
        "https://github.com/Multiverse/Multiverse-Portals/releases/download/5.3.0/multiverse-portals-5.3.0.jar",
        "345f0f296bb54e1fd98494260f93fb1ab97ba92f8ad7f6bfba129ea90021ea8d"
    ),
    FileToUpdateByDownloading(
        "minecraft-server/plugins/nuvotifier.jar",
        "https://github.com/NuVotifier/NuVotifier/releases/download/v2.7.3/nuvotifier.jar",
        "5fe5457e9ba7989dc9446f522a690e4e877236ba2fc5e90ed66fad33d3c69f39"
    ),
    FileToUpdateByDownloading(
        "minecraft-server/plugins/OldGenerator.jar",
        "https://hangarcdn.papermc.io/plugins/Spottedleaf/OldGenerator/versions/1.2.0/PAPER/oldgenerator-1.2.0.jar",
        "2ec8936c4e8ae515be3a193fe55db654f3c6f4bc40f434df515bd499e069365a"
    ),
    FileToUpdateByDownloading(
        "minecraft-server/plugins/PlaceholderAPI.jar",
        "https://github.com/PlaceholderAPI/PlaceholderAPI/releases/download/2.12.3/PlaceholderAPI-2.12.3.jar",
        "fde03259f5af6938f3c33eeb4d814000a1adabf1d2304ce14970be81f609a437"
    ),
    FileToUpdateByDownloading(
        "minecraft-server/plugins/SkinsRestorer.jar",
        "https://github.com/SkinsRestorer/SkinsRestorer/releases/download/15.12.6/SkinsRestorer.jar",
        "a85b4a370f988741c9a38f4d0498262edacbf3220314790ac1c127461996359c"
    ),
    FileToUpdateByDownloading(
        "minecraft-server/plugins/UltimateAdvancementAPI.jar",
        "https://hangarcdn.papermc.io/plugins/DevHeim/UltimateAdvancementAPI/versions/3.0.0-beta-4/PAPER/UltimateAdvancementAPI-Plugin-3.0.0-beta-4.jar",
        "8be40e20c41a057d3d71f037474e6d829a4935d422756d63d9f16a584b5cd2fe"
    ),
    FileToUpdateByDownloading(
        "minecraft-server/plugins/WorldEdit.jar",
        "https://mediafilez.forgecdn.net/files/8962/666/worldedit-bukkit-7.4.6-beta-02.jar",
        "166365c44bad4cd916200b288cf391d3770e3197d1eca84ff74f3914d933e289"
    ),
    FileToUpdateByDownloading(
        "minecraft-server/plugins/WorldGuard.jar",
        "https://mediafilez.forgecdn.net/files/8911/284/worldguard-bukkit-7.0.19.jar",
        "396df065f1cf38bab9878a0f53a776d5a085ebbf3c41813938b73f95d4eaca03"
    ),
    FileToUpdateByGradleTask(
        "minecraft-server/server.jar",
        "minecraft-server-software/suki-server/build/libs/suki-bundler-26.3-R0.1-SNAPSHOT.jar",
        ":Suki:suki-server:createBundlerJar",
        alwaysUpdate = true
    )
)
val baseFilesFolderName = "files"
val secretFilesFolderName = "secret-files"
for (secret in arrayOf(false, true)) {
    val filesFolderName = if (secret) secretFilesFolderName else baseFilesFolderName
    val filesFolder = project.layout.projectDirectory.file(filesFolderName).asFile
    filesFolder.walkTopDown().toList().filter { it.isFile }.forEach { file ->
        val relativePath = file.relativeTo(filesFolder).path
        if (secret || !project.layout.projectDirectory.file("${secretFilesFolderName}/${relativePath}").asFile.exists()) {
            targetFiles += FileToUpdateFromIncludedSourceFile(relativePath, "$filesFolderName/$relativePath")
        }
    }
}

tasks.register("printFileHashes") {
    group = "scaffolding"
    doFirst {
        for (targetFile in targetFiles) {
            if (targetFile !is FileToUpdateFromIncludedSourceFile) {
                val actualHash = sha256(targetFile.targetFile())
                val intendedHash = targetFile.intendedSha256Hash
                if (actualHash != intendedHash) {
                    logger.lifecycle(
                        "${targetFile::class.simpleName} / ${targetFile.targetFile().relativeTo(runDirectory.toPath()).pathString} -> $actualHash"
                    )
                }
            }
        }
    }
}

tasks.register("update") {
    group = "scaffolding"
    doFirst {
        targetFiles.stream().parallel().forEach { targetFile ->
            if (targetFile.needToUpdate()) {
                logger.lifecycle("Updating file: ${targetFile.targetFile().pathString}")
                targetFile.targetFile().createParentDirectories()
                targetFile.update()
                if (targetFile.targetFile().pathString.endsWith(".sh") || targetFile.targetFile().fileName.equals("server.jar")) {
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
        replaceLastUpdateGitRef()
    }
}

tasks.register("clean") {
    group = "build"
    doFirst {
        lastUpdateGitRefFile.delete()
    }
}

tasks.register<Exec>("updateAndRun") {
    group = "scaffolding"
    dependsOn("update")
    workingDir = project.layout.projectDirectory.dir("../run/minecraft-server").asFile
    commandLine("./start_without_screen.sh")
    standardInput = System.`in`
}
