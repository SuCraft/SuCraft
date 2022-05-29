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

	val airlockFile get(): File = Path.of(airlockFolder.path, relativeFilePath).toFile()

	val backupFile get(): File = Path.of(backupFolder.path, relativeFilePath).toFile()

	val longBackupFile get(): File = Path.of(longBackupFolder.path, relativeFilePath).toFile()

	companion object {

		private const val airlockFolderName = """airlock"""
		private const val backupFolderName = """backup"""
		private const val longBackupFolderName = """longbackup"""

		val airlockFolder: File = Path.of(SuCraftAntiCorruptionPlugin.getInstance().dataFolder.path, airlockFolderName).toFile()
		val backupFolder: File = Path.of(SuCraftAntiCorruptionPlugin.getInstance().dataFolder.path, backupFolderName).toFile()
		val longBackupFolder: File = Path.of(SuCraftAntiCorruptionPlugin.getInstance().dataFolder.path, longBackupFolderName).toFile()

	}

}