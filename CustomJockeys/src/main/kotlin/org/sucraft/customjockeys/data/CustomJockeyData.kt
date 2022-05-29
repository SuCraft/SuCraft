/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.customjockeys.data

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.event.entity.CreatureSpawnEvent
import org.sucraft.core.common.bukkit.chunk.ChunkCoordinates
import org.sucraft.core.common.bukkit.chunk.ChunkRadiusSet
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.customjockeys.main.SuCraftCustomJockeysPlugin
import java.util.*

@Suppress("MemberVisibilityCanBePrivate")
object CustomJockeyData : SuCraftComponent<SuCraftCustomJockeysPlugin>(SuCraftCustomJockeysPlugin.getInstance()) {

	// Settings

	private const val minY = 30
	private const val minLightLevel = 4
	private const val minLightFromSkyIfOverworld = 4
	private val validSpawnReasons = arrayOf(CreatureSpawnEvent.SpawnReason.NATURAL, CreatureSpawnEvent.SpawnReason.REINFORCEMENTS, CreatureSpawnEvent.SpawnReason.VILLAGE_INVASION)

	internal fun universalPredicateToSpawnJockeyFrom(entity: LivingEntity, location: Location, spawnReason: CreatureSpawnEvent.SpawnReason): Boolean {
		if (location.y < minY) return false
		if (location.block.lightLevel < minLightLevel) return false
		if (location.world.environment == World.Environment.NORMAL && location.block.lightFromSky < minLightFromSkyIfOverworld) return false
		if (entity.isInsideVehicle) return false
		if (entity.passengers.isNotEmpty()) return false
		if (spawnReason !in validSpawnReasons) return false
		if (!canSpawnJockeyInChunkThisSession(ChunkCoordinates.get(location))) return false
		return true
	}

	private const val maxOneJockeyPerSessionChunkRadius = 3

	// Registered jockeys

	internal val registeredJockeysByDetectedType: MutableMap<EntityType, MutableList<CustomJockey>> = EnumMap(EntityType::class.java)

	fun registerJockey(jockey: CustomJockey) =
		jockey.detectedEntityTypes.forEach { registeredJockeysByDetectedType.getOrPut(it, ::ArrayList).add(jockey) }

	// Chunk cap
	// Only one jockey can spawn in a specific area per session

	private val jockeyChunksThisSession = ChunkRadiusSet()

	internal fun incrementChunkJockeySpawns(chunk: ChunkCoordinates) = jockeyChunksThisSession.addWithinRadius(chunk, maxOneJockeyPerSessionChunkRadius)

	internal fun canSpawnJockeyInChunkThisSession(chunk: ChunkCoordinates) = chunk !in jockeyChunksThisSession

}