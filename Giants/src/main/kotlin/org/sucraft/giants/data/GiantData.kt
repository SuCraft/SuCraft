/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.giants.data

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.event.entity.CreatureSpawnEvent
import org.sucraft.core.common.bukkit.chunk.ChunkCoordinates
import org.sucraft.core.common.bukkit.chunk.ChunkRadiusSet
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.giants.main.SuCraftGiantsPlugin


@Suppress("MemberVisibilityCanBePrivate")
object GiantData : SuCraftComponent<SuCraftGiantsPlugin>(SuCraftGiantsPlugin.getInstance()) {

	// Settings

	private const val chance = 0.0025

	private const val minY = 45
	private const val minLightLevel = 4
	private const val minLightFromSky = 4
	private val validSpawnReasons = arrayOf(CreatureSpawnEvent.SpawnReason.NATURAL, CreatureSpawnEvent.SpawnReason.REINFORCEMENTS, CreatureSpawnEvent.SpawnReason.VILLAGE_INVASION)
	private val validEntityTypes = arrayOf(EntityType.ZOMBIE, EntityType.HUSK)

	internal fun canTurnIntoGiant(entity: LivingEntity, location: Location, spawnReason: CreatureSpawnEvent.SpawnReason): Boolean {
		if (location.y < minY) return false
		if (location.block.lightLevel < minLightLevel) return false
		if (location.world.environment != World.Environment.NORMAL) return false
		if (location.block.lightFromSky < minLightFromSky) return false
		if (entity.isInsideVehicle) return false
		if (entity.passengers.isNotEmpty()) return false
		if (spawnReason !in validSpawnReasons) return false
		if (entity.type !in validEntityTypes) return false
		if (!canSpawnGiantInChunkThisSession(ChunkCoordinates.get(location))) return false
		return true
	}

	private const val maxOneGiantPerSessionChunkRadius = 3

	// Check chance

	fun evaluateChance() = Math.random() < chance

	// Chunk cap
	// Only one giant can spawn in a specific area per session

	private val giantChunksThisSession = ChunkRadiusSet()

	internal fun incrementChunkGiantSpawns(chunk: ChunkCoordinates) = giantChunksThisSession.addWithinRadius(chunk, maxOneGiantPerSessionChunkRadius)

	internal fun canSpawnGiantInChunkThisSession(chunk: ChunkCoordinates) = giantChunksThisSession.contains(chunk)

}