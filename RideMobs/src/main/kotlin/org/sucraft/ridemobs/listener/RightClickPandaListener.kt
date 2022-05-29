/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.ridemobs.listener

import org.bukkit.Material
import org.bukkit.entity.Panda
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.ridemobs.main.SuCraftRideMobsPlugin
import org.sucraft.ridemobs.player.permission.SuCraftRideMobsPermissions

object RightClickPandaListener : SuCraftComponent<SuCraftRideMobsPlugin>(SuCraftRideMobsPlugin.getInstance()) {

	// Events

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	fun onPlayerInteractEntity(event: PlayerInteractEntityEvent) {
		val panda = event.rightClicked as? Panda ?: return
		val player = event.player
		// Make sure the player is not using an item with an effect
		val usedItem = player.inventory.getItem(event.hand)
		if (usedItem != null) {
			when (usedItem.type) {
				Material.BAMBOO,
				Material.CAKE,
				Material.LEAD,
				Material.MILK_BUCKET,
				Material.NAME_TAG,
				Material.PANDA_SPAWN_EGG -> return
				else -> {}
			}
		}
		if (!player.hasPermission(SuCraftRideMobsPermissions.SIT_ON_PANDAS)) return
		if (player.isInsideVehicle) {
			if (player.vehicle == panda) return
			// Eject a player from the current vehicle
			player.eject()
		}
		panda.addPassenger(player)
	}

}