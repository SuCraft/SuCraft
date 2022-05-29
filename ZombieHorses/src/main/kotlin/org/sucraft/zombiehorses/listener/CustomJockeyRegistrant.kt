/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.zombiehorses.listener

import org.bukkit.World
import org.bukkit.entity.EntityType
import org.sucraft.customjockeys.data.CustomJockey
import org.sucraft.customjockeys.data.CustomJockeyData

object CustomJockeyRegistrant {

	// Settings

	private const val minY = 45
	private const val chance = 0.00014

	// Register the custom jockey

	init {

		CustomJockeyData.registerJockey(CustomJockey.CustomJockeyByPassenger(
			chance,
			arrayOf(EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER, EntityType.HUSK),
			arrayOf(EntityType.ZOMBIE_HORSE),
			locationPredicate = { it.y >= minY },
			environmentPredicate = { it == World.Environment.NORMAL }
		))

	}

}