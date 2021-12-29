/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.anticorruption.backup

import org.sucraft.anticorruption.main.SuCraftAntiCorruptionPlugin
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent


object OldBackupRemoval : SuCraftComponent<SuCraftAntiCorruptionPlugin>(SuCraftAntiCorruptionPlugin.getInstance()) {

	// Settings

	private const val airlockFileMinimumAgeToRemoveInMillis = 1000L * 3600 * 24; // 1 day
	private const val backupFileMinimumAgeToRemoveInMillis = 1000L * 3600 * 24 * 2; // 2 days
	private const val longBackupFileMinimumAgeToRemoveInMillis = 1000L * 3600 * 24 * 7; // 7 days

	// Implementation

	fun scheduleRemoveOldBackups() {
		PlayerBackups.logger.info("Scheduling removing old backups...")
		for ((folder, minimumAgeToRemoveInMillis) in arrayOf(
			Pair(FileToBackup.airlockFolder, airlockFileMinimumAgeToRemoveInMillis),
			Pair(FileToBackup.backupFolder, backupFileMinimumAgeToRemoveInMillis),
			Pair(FileToBackup.longBackupFolder, longBackupFileMinimumAgeToRemoveInMillis)
		)) {
			folder.walkTopDown().forEach {
				if (it.exists() && !it.isDirectory) {
					if (it.lastModified() < System.currentTimeMillis() - minimumAgeToRemoveInMillis) {
						BackupTaskExecutor.scheduleOldFileRemoval(it, minimumAgeToRemoveInMillis)
					}
				}
			}
		}
	}

}