/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.anticorruption.backup

import org.bukkit.Bukkit
import org.bukkit.World
import org.sucraft.anticorruption.main.SuCraftAntiCorruptionPlugin
import org.sucraft.core.common.bukkit.io.WorldFolders
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import java.io.File

object WorldBackups : SuCraftComponent<SuCraftAntiCorruptionPlugin>(SuCraftAntiCorruptionPlugin.getInstance()) {

	// Implementation

	fun scheduleWorldDataBackup(world: World, delayInMillis: Long? = null) {
		BackupTaskExecutor.scheduleBackup(FileToBackup(WorldFolders.getLevelFile(world).path), delayInMillis)
		WorldFolders.getDataFolder(world).takeIf(File::isDirectory)?.listFiles()?.forEach {
			BackupTaskExecutor.scheduleBackup(FileToBackup(it.path), delayInMillis)
		}
	}

	fun scheduleBackupForAllWorldData(delayInMillis: Long? = null) {
		logger.info("Scheduling backup for all world data...")
		Bukkit.getWorlds().forEach { scheduleWorldDataBackup(it, delayInMillis) }
	}

}