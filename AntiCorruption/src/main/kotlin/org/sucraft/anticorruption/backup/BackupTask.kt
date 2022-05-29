/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.anticorruption.backup

data class BackupTask(
	val file: FileToBackup
) {

	val sourceFile get() = file.sourceFile

	val airlockFile get() = file.airlockFile

	val backupFile get() = file.backupFile

	val longBackupFile get() = file.longBackupFile

	//private val sourceFileRelativePath get() = Bukkit.getPluginsFolder().parentFile.absoluteFile.toPath().relativize(sourceFile.toPath())

}