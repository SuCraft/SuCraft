/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.customjockeys.data

import org.bukkit.World
import org.bukkit.block.Biome
import org.bukkit.entity.EntityType
import org.sucraft.core.common.bukkit.chunk.ChunkCoordinates


object IncludedCustomJockeys {

	// Settings

	private val defaultZombies = arrayOf(EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER, EntityType.HUSK)
	private val defaultSkeletons = arrayOf(EntityType.SKELETON, EntityType.STRAY)
	private val defaultDangerousVehicles = arrayOf(EntityType.SPIDER, EntityType.CAVE_SPIDER)
	private val defaultBenignVehicles = arrayOf(EntityType.COW, EntityType.PIG, EntityType.SHEEP)

	private const val defaultRelativeDangerousChance = 0.825
	private const val defaultRelativeBenignChance = 0.15
	private const val defaultRelativeGoatChance = 1 - defaultRelativeDangerousChance - defaultRelativeBenignChance

	// Register all included custom jockeys

	init {

		// Standard overworld jockeys
		for ((passengerTypes, baseChance) in arrayOf(
			Pair(defaultZombies, 0.004),
			Pair(defaultSkeletons, 0.007),
			Pair(arrayOf(EntityType.CREEPER), 0.002),
			Pair(arrayOf(EntityType.ENDERMAN), 0.001)
		)) {
			// Dangerous
			CustomJockeyData.registerJockey(CustomJockey.CustomJockeyByPassenger(
				defaultRelativeDangerousChance * baseChance,
				passengerTypes,
				defaultDangerousVehicles,
				environmentPredicate = { it == World.Environment.NORMAL }
			))
			// Non-goat benign
			CustomJockeyData.registerJockey(CustomJockey.CustomJockeyByPassenger(
				defaultRelativeBenignChance * baseChance,
				passengerTypes,
				defaultBenignVehicles,
				environmentPredicate = { it == World.Environment.NORMAL }
			))
			// Goat
			CustomJockeyData.registerJockey(CustomJockey.CustomJockeyByPassenger(
				defaultRelativeGoatChance * baseChance,
				passengerTypes,
				arrayOf(EntityType.GOAT),
				locationPredicate = { it.block.biome.name.lowercase().let { it.contains("slopes") || it.contains("peaks") } },
				environmentPredicate = { it == World.Environment.NORMAL }
			))
		}

		// Illagers on horses
		CustomJockeyData.registerJockey(CustomJockey.CustomJockeyByVehicle(
		0.001,
			arrayOf(EntityType.HORSE),
			arrayOf(EntityType.PILLAGER, EntityType.VINDICATOR),
			environmentPredicate = { it == World.Environment.NORMAL }
		))

		// Illagers on spiders
		CustomJockeyData.registerJockey(CustomJockey.CustomJockeyByVehicle(
			0.01,
			arrayOf(EntityType.SPIDER),
			arrayOf(EntityType.PILLAGER, EntityType.VINDICATOR),
			locationPredicate = { it.block.biome == Biome.DARK_FOREST },
			worldPredicate = { !it.isDayTime },
			environmentPredicate = { it == World.Environment.NORMAL }
		))

	}

}