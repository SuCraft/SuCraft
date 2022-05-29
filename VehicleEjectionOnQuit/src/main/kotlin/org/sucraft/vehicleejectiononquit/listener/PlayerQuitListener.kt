/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.vehicleejectiononquit.listener

import org.bukkit.entity.Entity
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerQuitEvent
import org.sucraft.core.common.bukkit.entity.PrintableEntityDescription
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.vehicleejectiononquit.main.SuCraftVehicleEjectionOnQuitPlugin

object PlayerQuitListener : SuCraftComponent<SuCraftVehicleEjectionOnQuitPlugin>(SuCraftVehicleEjectionOnQuitPlugin.getInstance()) {

	// Events

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onPlayerQuit(event: PlayerQuitEvent) {
		val player = event.player
		// Make any passengers of the player leave the player
		try {
			for (passenger in player.passengers) {
				try {
					if (!passenger.leaveVehicle())
						logger.warning("Failed to make passenger ${PrintableEntityDescription.get(passenger)} leave vehicle player ${player.name}")
				} catch (e: Exception) {
					logger.warning("Exception while making passenger ${PrintableEntityDescription.get(passenger)} leave vehicle player ${player.name}: $e")
				}
			}
		} catch (e: Exception) {
			logger.warning("Exception while making passengers leave vehicle player ${player.name}: $e")
		}
		// Make the player leave a vehicle if there is another passenger inside, or the vehicle is itself in a vehicle
		try {
			if (player.isInsideVehicle) {
				val vehicle: Entity = player.vehicle!!
				if (vehicle.passengers.size >= 2 || vehicle.isInsideVehicle) {
					try {
						if (!player.leaveVehicle())
							logger.warning("Failed to make player ${player.name} leave vehicle ${PrintableEntityDescription.get(vehicle)}")
					} catch (e: Exception) {
						logger.warning("Exception while making player ${player.name} leave vehicle ${PrintableEntityDescription.get(vehicle)}: $e")
					}
				}
			}
		} catch (e: Exception) {
			logger.warning("Exception while making player ${player.name} leave vehicle: $e")
		}
	}

}