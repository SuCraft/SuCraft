/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.modules.shorttermbackups.listener

import org.bukkit.event.world.ChunkLoadEvent
import org.bukkit.event.world.ChunkUnloadEvent
import org.sucraft.common.event.on
import org.sucraft.common.module.SuCraftComponent
import org.sucraft.common.time.TimeInMinutes
import org.sucraft.common.time.TimeInSeconds
import org.sucraft.modules.shorttermbackups.RegionBackupExtensions.scheduleRegionFilesBackupIfInhabitedLongEnough
import org.sucraft.modules.shorttermbackups.ShortTermBackups

/**
 * Listens for chunk loads and unloads to schedule backup up their corresponding files if appropriate.
 */
object ChunkLoadUnloadListener : SuCraftComponent<ShortTermBackups>() {

	// Settings

	private val backupAfterLoadDelay = TimeInSeconds(10)
	private val backupAfterUnloadDelay = TimeInMinutes(2)

	// Events

	init {
		// Listen for chunk loads to schedule backups if appropriate
		on(ChunkLoadEvent::class) {
			chunk.scheduleRegionFilesBackupIfInhabitedLongEnough(backupAfterLoadDelay)
		}
		// Listen for chunk unloads to schedule backups if appropriate
		on(ChunkUnloadEvent::class) {
			chunk.scheduleRegionFilesBackupIfInhabitedLongEnough(backupAfterUnloadDelay)
		}
	}

}