/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.breakfreeindependententitiesonquit

import org.bukkit.event.player.PlayerQuitEvent
import org.sucraft.common.entity.longDescription
import org.sucraft.common.event.on
import org.sucraft.common.function.runEach
import org.sucraft.common.module.SuCraftModule

/**
 * Ejects passengers and breaks the bond with bound entities that should be independent
 * (such as vehicles that contain another passenger or have other connections) when a player quits.
 */
object BreakFreeIndependentEntitiesOnQuit : SuCraftModule<BreakFreeIndependentEntitiesOnQuit>() {

	// Events

	init {
		// Listen for player quit events to break free entities intended to be independent
		on(PlayerQuitEvent::class) {

			// Make any passengers of the player leave the player
			try {
				player.passengers.runEach {
					try {
						if (!leaveVehicle())
							warning("Failed to make passenger $longDescription leave vehicle player ${player.name}")
					} catch (e: Exception) {
						warning("Exception while making passenger $longDescription leave vehicle player ${player.name}: $e")
					}
				}
			} catch (e: Exception) {
				warning("Exception while making passengers leave vehicle player ${player.name}: $e")
			}

			// Make the player leave a vehicle if there is another passenger inside, or the vehicle is itself in a vehicle
			try {
				if (player.isInsideVehicle) {
					val vehicle = player.vehicle!!
					if (vehicle.passengers.size >= 2 || vehicle.isInsideVehicle) {
						try {
							if (!player.leaveVehicle())
								warning("Failed to make player ${player.name} leave vehicle ${vehicle.longDescription}")
						} catch (e: Exception) {
							warning("Exception while making player ${player.name} leave vehicle ${vehicle.longDescription}: $e")
						}
					}
				}
			} catch (e: Exception) {
				warning("Exception while making player ${player.name} leave vehicle: $e")
			}

		}
	}

}