/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.zombiehorsejockeys

import org.bukkit.World.Environment.NORMAL
import org.bukkit.entity.EntityType.*
import org.sucraft.common.module.SuCraftComponent
import org.sucraft.modules.customjockeys.CustomJockey
import org.sucraft.modules.customjockeys.CustomJockeys.registerJockey

/**
 * Registers the zombie horse jockey as a custom jockey that can spawn.
 */
object SpawnZombieHorseJockeys : SuCraftComponent<ZombieHorseJockeys>() {

	// Settings

	private const val minY = 45
	private const val chance = 0.00014

	val passengerTypes = arrayOf(ZOMBIE, ZOMBIE_VILLAGER, HUSK)

	// Initialization

	override fun onInitialize() {
		super.onInitialize()
		// Register the zombie horse custom jockey
		registerJockey(
			CustomJockey.CustomJockeyByPassenger(
				chance,
				passengerTypes,
				arrayOf(ZOMBIE_HORSE),
				locationPredicate = { it.y >= minY },
				environmentPredicate = { it == NORMAL }
			))
	}

}