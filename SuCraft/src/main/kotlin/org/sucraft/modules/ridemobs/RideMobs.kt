/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.ridemobs

import org.bukkit.Material.*
import org.bukkit.entity.Panda
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.permissions.PermissionDefault
import org.sucraft.common.event.on
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.permission.hasPermission

/**
 * Makes it possible to sit on mobs.
 *
 * Currently, only enabled for pandas.
 */
object RideMobs : SuCraftModule<RideMobs>() {

	// Permissions

	object Permissions {

		val ridePandas = permission(
			"sit.panda",
			"Sit on pandas by right-clicking them",
			PermissionDefault.OP
		)

	}

	// Implementation

	private val Player.isRidingMobEnabledByThisModule
		get() = isInsideVehicle && vehicle is Panda

	// Events

	init {

		// Start sitting on the mob when right-clicked
		on(PlayerInteractEntityEvent::class) {
			// Check the entity type
			val panda = rightClicked as? Panda ?: return@on
			// Make sure the player is not using an item with an effect
			val usedItem = player.inventory.getItem(hand)
			when (usedItem.type) {
				BAMBOO,
				CAKE,
				LEAD,
				MILK_BUCKET,
				NAME_TAG,
				PANDA_SPAWN_EGG -> return@on
				else -> {}
			}
			// Make sure the player has permission
			if (!player.hasPermission(Permissions.ridePandas)) return@on
			// Do nothing if the player is already sitting on the target mob
			if (player.isInsideVehicle)
				if (player.vehicle == panda) return@on
			panda.addPassenger(player)
		}

		// Eject the player from a mob they are sitting on when they leave the server
		on(PlayerQuitEvent::class) {
			if (player.isRidingMobEnabledByThisModule)
				player.leaveVehicle()
		}

	}

}