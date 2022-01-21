/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.temporarymobfarmlimits.creaturespawn.absolutelimit

import org.apache.commons.math3.distribution.PoissonDistribution
import org.bukkit.Bukkit
import org.bukkit.entity.EntityType
import org.bukkit.entity.EntityType.*
import org.bukkit.event.entity.CreatureSpawnEvent
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.temporarymobfarmlimits.main.SuCraftTemporaryMobFarmLimitsPlugin
import java.util.*


object AbsoluteSpawnLimitData : SuCraftComponent<SuCraftTemporaryMobFarmLimitsPlugin>(SuCraftTemporaryMobFarmLimitsPlugin.getInstance()) {

	// Settings

	// Number of spawns is limited per minute
	private const val intervalInTicks = 20 * 60.0

	private val decrementCountDelayDistribution = PoissonDistribution(intervalInTicks)

	fun areAbsoluteNaturalSpawnsLimitedForEntityType(entityType: EntityType) =
		getTargetSpawnsInIntervalForEntityType(entityType) != null

	fun getTargetSpawnsInIntervalForEntityType(entityType: EntityType): Double? =
		when(entityType) {
			ELDER_GUARDIAN,
			GUARDIAN -> 45.0
			GLOW_SQUID,
			SQUID -> 60.0
			DROWNED,
			GHAST,
			HUSK,
			WITHER_SKELETON,
			ZOMBIE,
			ZOMBIE_VILLAGER,
			ZOMBIFIED_PIGLIN -> 90.0
			AXOLOTL,
			CREEPER,
			MAGMA_CUBE,
			SKELETON,
			SLIME,
			SPIDER,
			STRAY,
			WITCH -> 120.0
			ENDERMAN -> 200.0
			else -> null
		}

	fun areSpawnsOfSpawnReasonLimited(spawnReason: CreatureSpawnEvent.SpawnReason) =
		when (spawnReason) {
			CreatureSpawnEvent.SpawnReason.NATURAL,
			CreatureSpawnEvent.SpawnReason.SPAWNER -> true
			else -> false
		}

	// Data

	val recentSpawnCounts: MutableMap<EntityType, Int> = EnumMap(EntityType::class.java)

	// Implementation

	fun incrementCount(entityType: EntityType) {
		recentSpawnCounts[entityType] = recentSpawnCounts.getOrDefault(entityType, 0) + 1
		Bukkit.getScheduler().runTaskLater(plugin, Runnable { decrementCount(entityType) }, decrementCountDelayDistribution.sample().toLong())
	}

	fun decrementCount(entityType: EntityType) {
		recentSpawnCounts[entityType] = recentSpawnCounts[entityType]!! - 1
	}

	fun evaluateChanceForEntityToSpawn(entityType: EntityType) =
		// Multiply by 2 to account for the fact that the chance keeps decreasing linearly, so the average chance is 0.5 (this is true only if the spawn limit is always eventually fully fulfilled, but it's a good enough estimation) (were the chance a constant, we wouldn't need to)
		getTargetSpawnsInIntervalForEntityType(entityType)?.let { targetSpawnsInInterval ->
			recentSpawnCounts[entityType]?.let { Math.random() >= it / (targetSpawnsInInterval * 2) } ?: true
		} ?: true

}