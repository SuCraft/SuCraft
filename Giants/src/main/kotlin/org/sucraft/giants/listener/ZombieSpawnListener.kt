/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.giants.listener

import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.CreatureSpawnEvent
import org.sucraft.core.common.bukkit.chunk.ChunkCoordinates
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.giants.data.GiantData
import org.sucraft.giants.main.SuCraftGiantsPlugin

object ZombieSpawnListener : SuCraftComponent<SuCraftGiantsPlugin>(SuCraftGiantsPlugin.getInstance()) {

	// Events

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	fun onCreatureSpawn(event: CreatureSpawnEvent) {
		if (!GiantData.canTurnIntoGiant(event.entity, event.location, event.spawnReason)) return
		if (!GiantData.evaluateChance()) return
		event.isCancelled = true
		event.location.world.spawnEntity(event.location, EntityType.GIANT, event.spawnReason)
		GiantData.incrementChunkGiantSpawns(ChunkCoordinates.get(event.location))
	}

}