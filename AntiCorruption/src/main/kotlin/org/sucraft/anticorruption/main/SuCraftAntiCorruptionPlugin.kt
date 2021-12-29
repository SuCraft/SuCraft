/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.anticorruption.main

import org.sucraft.anticorruption.backup.*
import org.sucraft.anticorruption.listener.ChunkLoadUnloadListener
import org.sucraft.anticorruption.listener.PlayerJoinQuitListener
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin


class SuCraftAntiCorruptionPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftAntiCorruptionPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Schedule removing old backup files and old long backup files
		OldBackupRemoval.scheduleRemoveOldBackups()
		// Initialize components
		PlayerJoinQuitListener
		ChunkLoadUnloadListener
		// Schedule initial backups
		PlayerBackups.scheduleBackupForAllOnlinePlayers()
		RegionBackups.scheduleBackupForAllLoadedChunkRegions()
		WorldBackups.scheduleBackupForAllWorldData()
		MultiverseBackups.scheduleBackupForAllGeneralData()
		MultiverseBackups.scheduleBackupForAllOnlinePlayers()
		MoreProtectBackups.scheduleBackupForAllData()
	}

	override fun onSuCraftPluginDisable() {
		// Schedule final backups
		PlayerBackups.scheduleBackupForAllOnlinePlayers()
		RegionBackups.scheduleBackupForAllLoadedChunkRegions()
		MultiverseBackups.scheduleBackupForAllOnlinePlayers()
		// Execute all scheduled actions
		BackupTaskExecutor.executeAllInQueueSynchronously()
	}

}