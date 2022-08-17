/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.glassalwaysdrops

import org.bukkit.GameMode.SURVIVAL
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import org.sucraft.common.block.centerLocation
import org.sucraft.common.event.on
import org.sucraft.common.itemstack.hasSilkTouch
import org.sucraft.common.itemstack.material.isUnstainedOrStainedGlass
import org.sucraft.common.itemstack.material.isUnstainedOrStainedGlassPane
import org.sucraft.common.location.dropItem
import org.sucraft.common.module.SuCraftModule

/**
 * Glass will drop as an item when broken by a player.
 */
object GlassAlwaysDrops : SuCraftModule<GlassAlwaysDrops>() {

	// Events

	init {
		on(BlockBreakEvent::class) {
			// Make sure the player is in survival mode
			if (player.gameMode != SURVIVAL) return@on
			// Make sure the player is not using silk touch
			if (player.inventory.itemInMainHand.hasSilkTouch) return@on
			// Make sure the block is glass
			if (!block.type
					.run {
						isUnstainedOrStainedGlassPane || isUnstainedOrStainedGlass
					}
			) return@on
			// Drop the item
			block.centerLocation.dropItem(ItemStack(block.type))
		}
	}

}