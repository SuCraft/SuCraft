/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.keepglass.listener

import org.bukkit.GameMode
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import org.sucraft.core.common.bukkit.material.MaterialGroups
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.keepglass.main.SuCraftKeepGlassPlugin

object BreakGlassListener : SuCraftComponent<SuCraftKeepGlassPlugin>(SuCraftKeepGlassPlugin.getInstance()) {

	// Events

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onBlockBreak(event: BlockBreakEvent) {
		val player = event.player
		// Make sure the player is in survival mode
		if (player.gameMode != GameMode.SURVIVAL) return
		// Make sure the player is not using silk touch
		if (player.inventory.itemInMainHand.enchantments.keys.any { it == Enchantment.SILK_TOUCH }) return
		// Make sure the block is glass
		val block = event.block
		val type = block.type
		if (!(type in MaterialGroups.stainedOrRegularGlassPane || type in MaterialGroups.stainedOrRegularGlass)) return
		// Drop the item
		val dropLocation = block.location
		dropLocation.world.dropItem(dropLocation.clone().add(0.5, 0.5, 0.5), ItemStack(type))
	}

}