/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.giantsspawn

import org.bukkit.World
import org.bukkit.entity.EntityType
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.*
import org.sucraft.common.chunk.ChunkCoordinates.Companion.getChunkCoordinates
import org.sucraft.common.chunk.ChunkRadiusSet
import org.sucraft.common.event.on
import org.sucraft.common.location.spawnEntity
import org.sucraft.common.math.testProbability
import org.sucraft.common.module.SuCraftModule

/**
 * Randomly replaces some spawned zombies by giant zombies.
 */
object GiantsSpawn : SuCraftModule<GiantsSpawn>() {

	// Settings

	private const val chance = 0.0025

	private const val minY = 45
	private const val minLightLevel = 4
	private const val minLightFromSky = 4
	private val validSpawnReasons = arrayOf(
		NATURAL,
		REINFORCEMENTS,
		VILLAGE_INVASION
	)
	private val validEntityTypesToReplace = arrayOf(EntityType.ZOMBIE, EntityType.HUSK)

	private const val maxOneGiantPerSessionChunkRadius = 3

	// Data

	/**
	 * A chunk cap: only one giant can spawn in a specific area per session.
 	 */
	private val giantChunksThisSession = ChunkRadiusSet()

	// Events

	init {
		// Listen for existing creature spawns to spawn a giant instead
		on(CreatureSpawnEvent::class, priority = EventPriority.HIGH) {

			// Check the spawn conditions
			if (location.y < minY) return@on
			if (location.block.lightLevel < minLightLevel) return@on
			if (location.world.environment != World.Environment.NORMAL) return@on
			if (location.block.lightFromSky < minLightFromSky) return@on
			if (entity.isInsideVehicle) return@on
			if (entity.passengers.isNotEmpty()) return@on
			if (spawnReason !in validSpawnReasons) return@on
			if (entity.type !in validEntityTypesToReplace) return@on

			// Make sure we can spawn a giant in this chunk this session
			val chunkCoordinates = location.getChunkCoordinates()
			if (chunkCoordinates in giantChunksThisSession) return@on

			// Evaluate the chance
			if (!testProbability(chance)) return@on

			// Replace the spawn by a giant spawn
			isCancelled = true
			location.spawnEntity(EntityType.GIANT, spawnReason)

			// Increment the giants spawned in this chunk this session
			giantChunksThisSession.addWithinRadius(chunkCoordinates, maxOneGiantPerSessionChunkRadius)

		}
	}

}