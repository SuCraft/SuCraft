/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.anticorruption.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.sucraft.anticorruption.backup.MultiverseBackups
import org.sucraft.anticorruption.backup.PlayerBackups
import org.sucraft.anticorruption.main.SuCraftAntiCorruptionPlugin
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent


object PlayerJoinQuitListener : SuCraftComponent<SuCraftAntiCorruptionPlugin>(SuCraftAntiCorruptionPlugin.getInstance()) {

	// Settings
	private const val backupPlayerFilesAfterJoinDelayInTicks = 1000L * 5 // 5 seconds
	private const val backupPlayerFilesAfterQuitDelayInTicks = 1000L * 90 // 1.5 minutes

	// Events

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onPlayerJoin(event: PlayerJoinEvent) {
		PlayerBackups.schedulePlayerBackup(event.player, backupPlayerFilesAfterJoinDelayInTicks)
		MultiverseBackups.schedulePlayerBackup(event.player, backupPlayerFilesAfterJoinDelayInTicks)
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onPlayerQuit(event: PlayerQuitEvent) {
		PlayerBackups.schedulePlayerBackup(event.player, backupPlayerFilesAfterQuitDelayInTicks)
		MultiverseBackups.schedulePlayerBackup(event.player, backupPlayerFilesAfterQuitDelayInTicks)
	}

}