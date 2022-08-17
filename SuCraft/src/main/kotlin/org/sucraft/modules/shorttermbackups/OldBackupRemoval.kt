/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.shorttermbackups

import org.sucraft.common.module.SuCraftComponent
import org.sucraft.common.time.TimeInDays
import org.sucraft.common.time.TimeInWeeks
import org.sucraft.common.time.TimeLength
import org.sucraft.modules.shorttermbackups.BackupTaskExecutor.startOldFileRemovalTask
import org.sucraft.modules.shorttermbackups.ShortTermBackups.airlockFolder
import org.sucraft.modules.shorttermbackups.ShortTermBackups.backupFolder
import org.sucraft.modules.shorttermbackups.ShortTermBackups.longBackupFolder

/**
 * Provides functionality for removing old backup files.
 */
object OldBackupRemoval : SuCraftComponent<ShortTermBackups>() {

	// Settings

	private val airlockFileMinimumAgeToRemove = TimeInDays(1)
	private val backupFileMinimumAgeToRemove = TimeInDays(2)
	private val longBackupFileMinimumAgeToRemove = TimeInWeeks(1)

	// Provided functionality

	fun scheduleRemovingOldBackups(delay: TimeLength? = null) {
		info("Scheduling removing old backups...")
		sequenceOf(
			airlockFolder to airlockFileMinimumAgeToRemove,
			backupFolder to backupFileMinimumAgeToRemove,
			longBackupFolder to longBackupFileMinimumAgeToRemove
		).forEach { (folder, minimumAgeToRemove) ->
			folder.walkTopDown().forEach {
				if (it.exists() && !it.isDirectory) {
					if (it.lastModified() < System.currentTimeMillis() - minimumAgeToRemove.millis) {
						startOldFileRemovalTask(it, minimumAgeToRemove, delay)
					}
				}
			}
		}
	}

}