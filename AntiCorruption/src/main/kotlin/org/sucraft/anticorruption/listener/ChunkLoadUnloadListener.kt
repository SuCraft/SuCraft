/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.anticorruption.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.world.ChunkLoadEvent
import org.bukkit.event.world.ChunkUnloadEvent
import org.sucraft.anticorruption.backup.RegionBackups
import org.sucraft.anticorruption.main.SuCraftAntiCorruptionPlugin
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent


object ChunkLoadUnloadListener : SuCraftComponent<SuCraftAntiCorruptionPlugin>(SuCraftAntiCorruptionPlugin.getInstance()) {

	// Settings

	private const val backupRegionFilesAfterChunkLoadDelayInMillis = 1000L * 10 // 10 seconds
	private const val backupRegionFilesAfterChunkUnloadDelayInMillis = 1000L * 60 * 2 // 2 minutes

	// Events

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onChunkLoad(event: ChunkLoadEvent) {
		RegionBackups.scheduleChunkRegionBackupIfInhabitedLongEnough(event.chunk, backupRegionFilesAfterChunkLoadDelayInMillis)
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onChunkUnload(event: ChunkUnloadEvent) {
		RegionBackups.scheduleChunkRegionBackupIfInhabitedLongEnough(event.chunk, backupRegionFilesAfterChunkUnloadDelayInMillis)
	}

}