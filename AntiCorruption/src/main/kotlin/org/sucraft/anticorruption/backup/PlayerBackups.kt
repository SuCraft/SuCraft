/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.anticorruption.backup

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.sucraft.anticorruption.main.SuCraftAntiCorruptionPlugin
import org.sucraft.core.common.bukkit.world.WorldFolders
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent


object PlayerBackups : SuCraftComponent<SuCraftAntiCorruptionPlugin>(SuCraftAntiCorruptionPlugin.getInstance()) {

	// Implementation

	fun schedulePlayerBackup(player: Player, delayInMillis: Long? = null) {
		val uuid = player.uniqueId
		BackupTaskExecutor.scheduleBackup(FileToBackup(WorldFolders.getPlayerDataFile(uuid).path), delayInMillis)
		BackupTaskExecutor.scheduleBackup(FileToBackup(WorldFolders.getAdvancementsFile(uuid).path), delayInMillis)
		BackupTaskExecutor.scheduleBackup(FileToBackup(WorldFolders.getStatsFile(uuid).path), delayInMillis)
	}

	fun scheduleBackupForAllOnlinePlayers(delayInMillis: Long? = null) {
		logger.info("Scheduling backup for all online players...")
		Bukkit.getOnlinePlayers().forEach { schedulePlayerBackup(it, delayInMillis) }
	}

}