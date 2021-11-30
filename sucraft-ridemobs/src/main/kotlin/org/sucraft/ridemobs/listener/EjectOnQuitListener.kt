/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.ridemobs.listener

import org.bukkit.entity.Panda
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerQuitEvent
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.ridemobs.main.SuCraftRideMobsPlugin

object EjectOnQuitListener : SuCraftComponent<SuCraftRideMobsPlugin>(SuCraftRideMobsPlugin.getInstance(), "Eject on quit listener") {

	// Events

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	fun onPlayerQuit(event: PlayerQuitEvent) {
		val player = event.player
		if (!player.isInsideVehicle) return
		// Eject the player from a panda if they are riding one
		if (player.vehicle is Panda) player.eject()
	}

}