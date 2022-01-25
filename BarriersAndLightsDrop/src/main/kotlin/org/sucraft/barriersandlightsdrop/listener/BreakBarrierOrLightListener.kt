/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.barriersandlightsdrop.listener

import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import org.sucraft.barriersandlightsdrop.main.SuCraftBarriersAndLightsDropPlugin
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent


object BreakBarrierOrLightListener : SuCraftComponent<SuCraftBarriersAndLightsDropPlugin>(SuCraftBarriersAndLightsDropPlugin.getInstance()) {

	// Events

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onBlockBreak(event: BlockBreakEvent) {
		val player = event.player
		// Make sure the player is in survival mode
		if (player.gameMode != GameMode.SURVIVAL) return
		// Either the player must be breaking and holding a barrier, or breaking and holding a light
		if (!(event.block.type == Material.BARRIER || event.block.type == Material.LIGHT)) return
		if (player.inventory.itemInMainHand.type != event.block.type) return
		// Drop the item
		val dropLocation = event.block.location
		dropLocation.world.dropItem(dropLocation.clone().add(0.5, 0.5, 0.5), ItemStack(event.block.type))
	}

}