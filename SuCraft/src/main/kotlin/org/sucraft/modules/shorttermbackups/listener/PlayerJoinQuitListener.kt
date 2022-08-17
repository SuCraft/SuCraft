/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.anticorruption.listener

import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.sucraft.common.event.on
import org.sucraft.common.module.SuCraftComponent
import org.sucraft.common.time.TimeInSeconds
import org.sucraft.modules.shorttermbackups.MultiverseBackupExtensions.scheduleMultiverseFilesBackup
import org.sucraft.modules.shorttermbackups.PlayerServerDataBackupExtensions.scheduleServerDataFilesBackup
import org.sucraft.modules.shorttermbackups.ShortTermBackups

/**
 * Listens for player joins and quits to schedule backup up their corresponding files if appropriate.
 */
object PlayerJoinQuitListener : SuCraftComponent<ShortTermBackups>() {

	// Settings

	private val backupAfterJoinDelay = TimeInSeconds(10)
	private val backupAfterQuitDelay = TimeInSeconds(90)

	// Events

	init {
		// Listen for player joins to schedule backups if appropriate
		on(PlayerJoinEvent::class) {
			player.scheduleServerDataFilesBackup(backupAfterJoinDelay)
			player.scheduleMultiverseFilesBackup(backupAfterJoinDelay)
		}
		// Listen for player quits to schedule backups if appropriate
		on(PlayerQuitEvent::class) {
			player.scheduleServerDataFilesBackup(backupAfterQuitDelay)
			player.scheduleMultiverseFilesBackup(backupAfterQuitDelay)
		}
	}

}