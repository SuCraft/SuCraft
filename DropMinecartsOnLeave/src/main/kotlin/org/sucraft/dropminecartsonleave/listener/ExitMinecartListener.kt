/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.dropminecartsonleave.listener

import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.Minecart
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.vehicle.VehicleExitEvent
import org.bukkit.inventory.ItemStack
import org.sucraft.core.common.bukkit.scheduler.RunInFuture
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.dropminecartsonleave.main.SuCraftDropMinecartsOnLeavePlugin

object ExitMinecartListener : SuCraftComponent<SuCraftDropMinecartsOnLeavePlugin>(SuCraftDropMinecartsOnLeavePlugin.getInstance()) {

	// Events

	@Suppress("NestedLambdaShadowedImplicitParameter")
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onVehicleExit(event: VehicleExitEvent) {
		val player = event.exited as? Player ?: return
		val minecart = event.vehicle as? Minecart ?: return
		if (minecart.isInvulnerable) return
		val location = player.location.clone().add(0.0, 0.51, 0.0)
		RunInFuture.forPlayerIfOnline(plugin, player, {
			if (!minecart.isInvulnerable) {
				if (it.gameMode != GameMode.CREATIVE) location.world.dropItem(location, ItemStack(Material.MINECART)) {
					it.velocity = it.velocity.setX(0).setZ(0)
				}
				if (!minecart.isDead) {
					minecart.remove()
					it.teleport(location)
				}
			}
		})
	}

}