/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.anticorruption.backup

import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.sucraft.anticorruption.main.SuCraftAntiCorruptionPlugin
import org.sucraft.core.common.bukkit.chunk.RegionCoordinates
import org.sucraft.core.common.bukkit.io.WorldFolders
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent


object RegionBackups : SuCraftComponent<SuCraftAntiCorruptionPlugin>(SuCraftAntiCorruptionPlugin.getInstance()) {

	// Settings

	private const val minimumChunkInhabitedTimeToBackup = 20L * 60 * 10 // 1 player for 10 minutes

	// Implementation

	fun scheduleRegionBackup(region: RegionCoordinates, delayInMillis: Long? = null) {
		BackupTaskExecutor.scheduleBackup(FileToBackup(WorldFolders.getRegionFile(region)!!.path), delayInMillis)
		BackupTaskExecutor.scheduleBackup(FileToBackup(WorldFolders.getEntitiesFile(region)!!.path), delayInMillis)
		BackupTaskExecutor.scheduleBackup(FileToBackup(WorldFolders.getPOIFile(region)!!.path), delayInMillis)
	}

	fun scheduleChunkRegionBackupIfInhabitedLongEnough(chunk: Chunk, delayInMillis: Long? = null) {
		if (chunk.inhabitedTime >= minimumChunkInhabitedTimeToBackup) {
			scheduleRegionBackup(RegionCoordinates.get(chunk), delayInMillis)
		}
	}

	fun scheduleBackupForAllLoadedChunkRegions(delayInMillis: Long? = null) {
		PlayerBackups.logger.info("Scheduling backup for all loaded chunk regions...")
		Bukkit.getWorlds().asSequence().flatMap {
			it.loadedChunks.asSequence()
				.filter { it.inhabitedTime >= minimumChunkInhabitedTimeToBackup }
				.map(RegionCoordinates::get)
				.distinct()
		}.forEach { scheduleRegionBackup(it, delayInMillis) }
	}

}