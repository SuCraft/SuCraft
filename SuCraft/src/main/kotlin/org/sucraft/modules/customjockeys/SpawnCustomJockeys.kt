/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.customjockeys

import org.bukkit.World.Environment.NORMAL
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.*
import org.sucraft.common.chunk.ChunkCoordinates.Companion.getChunkCoordinates
import org.sucraft.common.chunk.ChunkRadiusSet
import org.sucraft.common.event.on
import org.sucraft.common.math.testProbability
import org.sucraft.common.module.SuCraftComponent
import org.sucraft.modules.customjockeys.CustomJockeys.getRegisteredJockeys

/**
 * Listener to spawn the custom jockeys upon an entity that can become the passenger or vehicle spawning.
 */
object SpawnCustomJockeys : SuCraftComponent<CustomJockeys>() {

	// Settings that apply to all custom jockeys

	private const val minY = 30
	private const val minLightLevel = 4
	private const val minLightFromSkyIfOverworld = 4
	private val validSpawnReasons = arrayOf(
		NATURAL,
		REINFORCEMENTS,
		VILLAGE_INVASION
	)

	private const val maxOneJockeyPerSessionChunkRadius = 3

	// Data

	/**
	 * A chunk cap: only one custom jockey can spawn in a specific area per session.
	 */
	private val customJockeyChunksThisSession = ChunkRadiusSet()

	// Events

	init {
		// Listen for creature spawns to turn them into a custom jockey
		on(CreatureSpawnEvent::class) {

			// Check the spawn conditions
			if (location.y < minY) return@on
			if (location.block.lightLevel < minLightLevel) return@on
			if (location.world.environment == NORMAL && location.block.lightFromSky < minLightFromSkyIfOverworld)
				return@on
			if (entity.isInsideVehicle) return@on
			if (entity.passengers.isNotEmpty()) return@on
			if (spawnReason !in validSpawnReasons) return@on

			// Make sure we can spawn a custom jockey in this chunk this session
			val chunkCoordinates = location.getChunkCoordinates()
			if (chunkCoordinates in customJockeyChunksThisSession) return@on

			// Try every type of custom jockey
			for (jockey in getRegisteredJockeys(entityType)) {
				jockey.run attemptJockey@{
					// Check the event circumstances
					if (!matchesSpawnEventCircumstances(entity, location, spawnReason)) return@attemptJockey
					// Evaluate the chance
					if (!testProbability(chance)) return@attemptJockey
					// Attempt to spawn the jockey
					if (spawnJockeyEntity(entity, location, getRandomSpawnedEntityType())) {
						// Increment the custom jockeys spawned in this chunk this session
						customJockeyChunksThisSession.addWithinRadius(
							chunkCoordinates,
							maxOneJockeyPerSessionChunkRadius
						)
						// Do not attempt to spawn any more jockeys
						return@on
					}
				}
			}
		}
	}


}