/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.shorttermbackups

import org.sucraft.anticorruption.listener.ChunkLoadUnloadListener
import org.sucraft.anticorruption.listener.PlayerJoinQuitListener
import org.sucraft.common.io.inside
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.time.TimeInDays
import org.sucraft.modules.shorttermbackups.BackupTaskExecutor.waitForAllTasksToComplete
import org.sucraft.modules.shorttermbackups.MoreProtectBackups.scheduleBackupForAllMoreProtectData
import org.sucraft.modules.shorttermbackups.MultiverseBackupExtensions.scheduleBackupForAllGeneralMultiverseData
import org.sucraft.modules.shorttermbackups.MultiverseBackupExtensions.scheduleBackupForAllOnlinePlayerMultiverseFiles
import org.sucraft.modules.shorttermbackups.OldBackupRemoval.scheduleRemovingOldBackups
import org.sucraft.modules.shorttermbackups.PlayerServerDataBackupExtensions.scheduleBackupForAllOnlinePlayerServerDataFiles
import org.sucraft.modules.shorttermbackups.RegionBackupExtensions.scheduleBackupForAllLoadedChunkRegionFiles
import org.sucraft.modules.shorttermbackups.WorldBackupExtensions.scheduleBackupForAllWorldServerDataFiles

/**
 * Makes constant backups of chunks, player data and some plugin data
 * to aid recovery from corruption in the case of sudden unexpected shutdowns.
 */
object ShortTermBackups : SuCraftModule<ShortTermBackups>() {

	// Settings

	val airlockFolder get() = """airlock""" inside folder
	val backupFolder get() = """backup""" inside folder
	val longBackupFolder get() = """longbackup""" inside folder

	const val maxFileSizeInBytes = 1024L * 1024 * 50 // 50 MB

	val keepBackupFileInterval = TimeInDays(2)
	val keepLongBackupFileInterval = TimeInDays(7)

	// Components

	override val components = listOf(
		BackupTaskExecutor,
		OldBackupRemoval,
		RegionBackupExtensions,
		PlayerServerDataBackupExtensions,
		MultiverseBackupExtensions,
		MoreProtectBackups,
		WorldBackupExtensions,
		ChunkLoadUnloadListener,
		PlayerJoinQuitListener
	)

	// Initialization and termination

	override fun onInitialize() {
		super.onInitialize()
		// Schedule removing old backup files and old long backup files
		scheduleRemovingOldBackups()
		// Schedule initial backups
		scheduleBackupForAllOnlinePlayerServerDataFiles()
		scheduleBackupForAllLoadedChunkRegionFiles()
		scheduleBackupForAllWorldServerDataFiles()
		scheduleBackupForAllGeneralMultiverseData()
		scheduleBackupForAllOnlinePlayerMultiverseFiles()
		scheduleBackupForAllMoreProtectData()
	}

	override fun onTerminate() {
		super.onTerminate()
		// Schedule final backups
		scheduleBackupForAllOnlinePlayerServerDataFiles()
		scheduleBackupForAllLoadedChunkRegionFiles()
		scheduleBackupForAllOnlinePlayerMultiverseFiles()
		// Finish all scheduled tasks before closing server
		waitForAllTasksToComplete()
	}

}