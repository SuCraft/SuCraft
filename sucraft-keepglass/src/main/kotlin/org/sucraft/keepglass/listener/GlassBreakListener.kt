package org.sucraft.keepglass.listener

import org.sucraft.core.common.bukkit.log.NestedLogger
import org.bukkit.event.EventPriority
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.GameMode
import org.bukkit.inventory.ItemStack
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.sucraft.core.common.bukkit.material.MaterialGroups
import org.sucraft.keepglass.main.SuCraftKeepGlassPlugin
import org.sucraft.core.common.sucraft.log.SuCraftLogTexts

object GlassBreakListener : Listener {

	// Logger

	private val logger: NestedLogger

	// Events

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onBlockBreak(event: BlockBreakEvent) {
		val player = event.player
		// Make sure the player is in survival mode
		if (player.gameMode != GameMode.SURVIVAL) return
		// Make sure the player is not using silk touch
		if (player.inventory.itemInMainHand.enchantments.keys.stream().anyMatch { it == Enchantment.SILK_TOUCH }) return
		// Make sure the block is glass
		val block = event.block
		val type = block.type
		if (!(type in MaterialGroups.stainedOrRegularGlassPane || type in MaterialGroups.stainedOrRegularGlass)) return
		// Drop the item
		val dropLocation = block.location
		dropLocation.world.dropItem(dropLocation, ItemStack(type))
	}

	// Initialization

	init {
		// Make sure the plugin is enabled
		SuCraftKeepGlassPlugin.getInstance()
		// Create the logger
		logger = NestedLogger.create(SuCraftKeepGlassPlugin.getInstance().getNestedLogger(), "Glass break listener")
		// Register events
		logger.info(SuCraftLogTexts.registeringEvents)
		SuCraftKeepGlassPlugin.getInstance().registerEvents(this)
	}

}