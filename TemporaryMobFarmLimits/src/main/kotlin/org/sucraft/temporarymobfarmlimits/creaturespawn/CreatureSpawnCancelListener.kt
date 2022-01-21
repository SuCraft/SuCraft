/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.temporarymobfarmlimits.creaturespawn

import com.destroystokyo.paper.event.entity.PlayerNaturallySpawnCreaturesEvent
import com.destroystokyo.paper.event.entity.PreCreatureSpawnEvent
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.CreatureSpawnEvent
import org.sucraft.core.common.bukkit.chunk.ChunkCoordinates
import org.sucraft.core.common.bukkit.entity.EntityUtils
import org.sucraft.core.common.general.math.RelativeCoordinates
import org.sucraft.core.common.sucraft.delegate.MobFarmWeight
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.temporarymobfarmlimits.main.SuCraftTemporaryMobFarmLimitsPlugin


object CreatureSpawnCancelListener : SuCraftComponent<SuCraftTemporaryMobFarmLimitsPlugin>(SuCraftTemporaryMobFarmLimitsPlugin.getInstance()) {

	// Settings

	// This is faster for those spawn reasons that are supported by PreCreatureSpawnEvent
	private fun isSpawnReasonHandledByPreCreatureSpawnEvent(reason: CreatureSpawnEvent.SpawnReason) =
		when (reason) {
			CreatureSpawnEvent.SpawnReason.NATURAL,
			CreatureSpawnEvent.SpawnReason.REINFORCEMENTS,
			CreatureSpawnEvent.SpawnReason.SPAWNER -> true
			else -> false
		}

	private val entityTypesForWhichToCancelNaturalSpawnsAroundPlayer = arrayOf(
		EntityType.AXOLOTL,
		EntityType.CREEPER,
		EntityType.DROWNED,
		EntityType.ELDER_GUARDIAN,
		EntityType.ENDERMAN,
		EntityType.GHAST,
		EntityType.GLOW_SQUID,
		EntityType.GUARDIAN,
		EntityType.HUSK,
		EntityType.MAGMA_CUBE,
		EntityType.SKELETON,
		EntityType.SLIME,
		EntityType.SPIDER,
		EntityType.SQUID,
		EntityType.STRAY,
		EntityType.WITCH,
		EntityType.WITHER_SKELETON,
		EntityType.ZOMBIE,
		EntityType.ZOMBIE_VILLAGER,
		EntityType.ZOMBIFIED_PIGLIN
	)

	// Events

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	fun onCreatureSpawn(event: CreatureSpawnEvent) {
		if (
			!EntityUtils.canSpawnFromBucket(event.entityType)
			&& EventClassification.isSpawnReasonThatCouldBeMobFarm(event.spawnReason)
			&& (
					!isSpawnReasonHandledByPreCreatureSpawnEvent(event.spawnReason)
							|| MobFarmWeight.get().getWeight() < 1e-6 // If the weight is zero, we don't mind double-cancelling, so let's check just in case some spawn was missed by PreCreatureSpawnEvent
					)
		)
			if (CreatureSpawnMeasurementData.isProbablyFarm(event.entityType, ChunkCoordinates.get(event.location)))
				if (Math.random() >= MobFarmWeight.get().getWeight())
					event.isCancelled = true
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	fun onPreCreatureSpawn(event: PreCreatureSpawnEvent) {
		if (
			!EntityUtils.canSpawnFromBucket(event.type)
			&& isSpawnReasonHandledByPreCreatureSpawnEvent(event.reason)
		)
			if (CreatureSpawnMeasurementData.isProbablyFarm(event.type, ChunkCoordinates.get(event.spawnLocation)))
				if (Math.random() >= MobFarmWeight.get().getWeight())
					event.isCancelled = true
	}

	// Cancelling this event already, when a chunk nearby is classified as a mob farm of certain types, is much faster
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	fun onPlayerNaturallySpawnCreatures(event: PlayerNaturallySpawnCreaturesEvent) {
		if (MobFarmWeight.get().getWeight() >= 1e-6) return
		val chunk = ChunkCoordinates.get(event.player.chunk)
		for (relativeChunk in RelativeCoordinates.relativeWithinSquareRadius(event.spawnRadius.toInt()).map { (dx, dz) -> chunk.getRelative(dx, dz) }) {
			if (entityTypesForWhichToCancelNaturalSpawnsAroundPlayer
					.any { CreatureSpawnMeasurementData.isProbablyFarm(it, relativeChunk) }
			) {
				event.isCancelled = true
				return
			}
		}
	}

}