/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.includedcustomjockeys

import org.bukkit.World.Environment.NORMAL
import org.bukkit.block.Biome.*
import org.bukkit.entity.EntityType.*
import org.sucraft.common.module.SuCraftModule
import org.sucraft.modules.customjockeys.CustomJockey
import org.sucraft.modules.customjockeys.CustomJockeys
import org.sucraft.modules.customjockeys.CustomJockeys.registerJockey
import java.util.*

/**
 * Uses the [CustomJockeys] utility module to add a number of custom jockeys.
 */
object IncludedCustomJockeys : SuCraftModule<IncludedCustomJockeys>() {

	// Dependencies

	override val dependencies = listOf(
		CustomJockeys
	)

	// Settings

	private val defaultZombies = arrayOf(
		ZOMBIE,
		ZOMBIE_VILLAGER,
		HUSK
	)
	private val defaultSkeletons = arrayOf(
		SKELETON,
		STRAY
	)
	private val defaultDangerousVehicles = arrayOf(
		SPIDER,
		CAVE_SPIDER
	)
	private val defaultBenignVehicles = arrayOf(
		COW,
		PIG,
		SHEEP
	)

	private const val defaultRelativeDangerousChance = 0.825
	private const val defaultRelativeBenignChance = 0.15
	private const val defaultRelativeGoatChance = 1 - defaultRelativeDangerousChance - defaultRelativeBenignChance

	private val biomesGoatCanBeSpawnedIn = EnumSet.of(
		SNOWY_SLOPES,
		FROZEN_PEAKS,
		JAGGED_PEAKS,
		STONY_PEAKS
	)

	// Internal implementation

	private fun registerAll() {
		// Standard overworld jockeys
		for ((passengerTypes, baseChance) in arrayOf(
			defaultZombies to 0.004,
			defaultSkeletons to 0.007,
			arrayOf(CREEPER) to 0.002,
			arrayOf(ENDERMAN) to 0.001
		)) {
			// Dangerous
			registerJockey(
				CustomJockey.CustomJockeyByPassenger(
					defaultRelativeDangerousChance * baseChance,
					passengerTypes,
					defaultDangerousVehicles,
					environmentPredicate = { it == NORMAL }
				))
			// Non-goat benign
			registerJockey(CustomJockey.CustomJockeyByPassenger(
				defaultRelativeBenignChance * baseChance,
				passengerTypes,
				defaultBenignVehicles,
				environmentPredicate = { it == NORMAL }
			))
			// Goat
			registerJockey(CustomJockey.CustomJockeyByPassenger(
				defaultRelativeGoatChance * baseChance,
				passengerTypes,
				arrayOf(GOAT),
				locationPredicate = {
					it.block.biome in biomesGoatCanBeSpawnedIn
				},
				environmentPredicate = { it == NORMAL }
			))
		}

		// Illagers on horses
		registerJockey(CustomJockey.CustomJockeyByVehicle(
			0.001,
			arrayOf(HORSE),
			arrayOf(PILLAGER, VINDICATOR),
			environmentPredicate = { it == NORMAL }
		))

		// Illagers on spiders
		registerJockey(CustomJockey.CustomJockeyByVehicle(
			0.01,
			arrayOf(SPIDER),
			arrayOf(PILLAGER, VINDICATOR),
			locationPredicate = { it.block.biome == DARK_FOREST },
			worldPredicate = { !it.isDayTime },
			environmentPredicate = { it == NORMAL }
		))
	}

	// Initialization

	override fun onInitialize() {
		super.onInitialize()
		// Register the included jockeys
		registerAll()
	}

}