package org.sucraft.keepglass.listener

import org.sucraft.core.common.log.SuCraftLogger
import org.bukkit.event.EventPriority
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.entity.Player
import org.bukkit.GameMode
import org.bukkit.inventory.ItemStack
import org.bukkit.enchantments.Enchantment
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.sucraft.core.common.material.MaterialGroups
import org.sucraft.keepglass.listener.GlassBreakListener
import org.sucraft.core.common.plugin.PluginUtils
import org.sucraft.keepglass.main.SuCraftKeepGlassPlugin
import org.sucraft.core.common.log.DefaultLogTexts
import java.lang.IllegalStateException

object GlassBreakListener : Listener {

	// Logger

	private val logger: SuCraftLogger

	// Events

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onBlockBreak(event: BlockBreakEvent) {
		val player = event.player
		if (player.gameMode != GameMode.SURVIVAL) {
			return
		}
		val itemInHand = player.inventory.itemInMainHand
		// Make sure the player is not using silk touch
		if (itemInHand != null && itemInHand.enchantments.keys.stream().anyMatch { enchantment: Enchantment -> enchantment == Enchantment.SILK_TOUCH }) {
			return
		}
		val block = event.block
		val type = block.type
		if (MaterialGroups.isStainedOrRegularGlassPane(type) || MaterialGroups.isStainedOrRegularGlass(type)) {
			val dropLocation = block.location
			dropLocation.world.dropItem(dropLocation, ItemStack(type))
		}
	}

	// Initialization

	init {
		// Make sure the plugin is enabled
		SuCraftKeepGlassPlugin.getInstance()
		// Create the logger
		logger = SuCraftLogger(SuCraftKeepGlassPlugin.getInstance(), "Glass break listener")
		// Register events
		logger.info(DefaultLogTexts.registeringEvents)
		SuCraftKeepGlassPlugin.getInstance().registerEvents(this)
	}

}