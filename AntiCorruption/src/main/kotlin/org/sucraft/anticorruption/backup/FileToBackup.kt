/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.anticorruption.backup

import org.sucraft.anticorruption.main.SuCraftAntiCorruptionPlugin
import java.io.File
import java.nio.file.Path


/**
 * The given path must be relative
 */
data class FileToBackup(
	var relativeFilePath: String
) {

	val sourceFile get() = File(relativeFilePath)

	val airlockFile get() = Path.of(airlockFolder.path, relativeFilePath).toFile()

	val backupFile get() = Path.of(backupFolder.path, relativeFilePath).toFile()

	val longBackupFile get() = Path.of(longBackupFolder.path, relativeFilePath).toFile()

	companion object {

		const val airlockFolderName = """airlock"""
		const val backupFolderName = """backup"""
		const val longBackupFolderName = """longbackup"""

		val airlockFolder = Path.of(SuCraftAntiCorruptionPlugin.getInstance().dataFolder.path, airlockFolderName).toFile()
		val backupFolder = Path.of(SuCraftAntiCorruptionPlugin.getInstance().dataFolder.path, backupFolderName).toFile()
		val longBackupFolder = Path.of(SuCraftAntiCorruptionPlugin.getInstance().dataFolder.path, longBackupFolderName).toFile()

	}

}