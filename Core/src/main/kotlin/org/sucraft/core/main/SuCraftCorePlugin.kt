/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.main

import it.unimi.dsi.fastutil.longs.LongOpenHashSet
import org.bukkit.Bukkit
import org.sucraft.core.common.bukkit.chunk.ChunkCoordinates
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.defaultdelegate.GlobalTickCounterUsingScheduledTask
import org.sucraft.core.defaultdelegate.MinecraftClientLocaleByIncludedResource
import org.sucraft.core.defaultdelegate.OfflinePlayersInformationByBukkitOfflinePlayerCall
import org.sucraft.core.defaultdelegate.StandardItemStackNamesByClientLocale

class SuCraftCorePlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftCorePlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		GlobalTickCounterUsingScheduledTask
		MinecraftClientLocaleByIncludedResource
		OfflinePlayersInformationByBukkitOfflinePlayerCall
		StandardItemStackNamesByClientLocale
		// Verify there are no collisions caused by ChunkCoordinates.longKeyWithWorldWorldRangeSize
		if (Bukkit.getWorlds().mapTo(LongOpenHashSet(Bukkit.getWorlds().size * 5)) { ChunkCoordinates.getLongKeyWithWorldWorldPart(it) }.size != Bukkit.getWorlds().size) {
			throw IllegalStateException("There exists a world long key part collision caused by ChunkCoordinates.longKeyWithWorldWorldRangeSize")
		}
	}

}