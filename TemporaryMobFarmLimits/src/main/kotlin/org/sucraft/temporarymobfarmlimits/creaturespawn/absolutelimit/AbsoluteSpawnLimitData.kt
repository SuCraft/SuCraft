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

	// 60 spawns per minute
	private const val intervalInTicks = 20 * 60.0
	private const val targetSpawnsInInterval = 60.0

	private val decrementCountDelayDistribution = PoissonDistribution(intervalInTicks)

	fun areAbsoluteNaturalSpawnsLimitedForEntityType(entityType: EntityType) =
		when(entityType) {
			AXOLOTL,
			CREEPER,
			DROWNED,
			ELDER_GUARDIAN,
			ENDERMAN,
			GHAST,
			GLOW_SQUID,
			GUARDIAN,
			HUSK,
			MAGMA_CUBE,
			SKELETON,
			SLIME,
			SPIDER,
			SQUID,
			STRAY,
			WITCH,
			WITHER_SKELETON,
			ZOMBIE,
			ZOMBIE_VILLAGER,
			ZOMBIFIED_PIGLIN -> true
			else -> false
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
		recentSpawnCounts[entityType]?.let { Math.random() >= it / (targetSpawnsInInterval * 2) } ?: true

}