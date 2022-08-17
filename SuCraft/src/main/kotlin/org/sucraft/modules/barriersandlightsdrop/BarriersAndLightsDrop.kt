/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.barriersandlightsdrop

import org.bukkit.GameMode.SURVIVAL
import org.bukkit.Material.BARRIER
import org.bukkit.Material.LIGHT
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import org.sucraft.common.block.centerLocation
import org.sucraft.common.event.on
import org.sucraft.common.location.dropItem
import org.sucraft.common.module.SuCraftModule

/**
 * Barriers and lights will drop as an item when broken by a player,
 * if they are holding that type of block in their main hand already.
 */
object BarriersAndLightsDrop : SuCraftModule<BarriersAndLightsDrop>() {

	// Events

	init {
		on(BlockBreakEvent::class) {
			// Make sure the player is in survival mode
			if (player.gameMode != SURVIVAL) return@on
			// The block broken must be a barrier or a light
			if (!(block.type == BARRIER || block.type == LIGHT)) return@on
			// The player must be holding the same type of block
			if (player.inventory.itemInMainHand.type != block.type) return@on
			// Drop the item
			block.centerLocation.dropItem(ItemStack(block.type))
		}
	}

}