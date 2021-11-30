package org.sucraft.invisibleitemframes.listener

import org.bukkit.ChatColor.*;
import org.bukkit.Material
import org.bukkit.entity.ItemFrame
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.invisibleitemframes.main.SuCraftInvisibleItemFramesPlugin

object RightClickItemFrameListener : SuCraftComponent<SuCraftInvisibleItemFramesPlugin>(SuCraftInvisibleItemFramesPlugin.getInstance(), "Right-click item frame listener") {

	// Events

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onPlayerInteractEntity(event: PlayerInteractEntityEvent) {
		if (event.rightClicked !is ItemFrame) return
		if (!event.player.isSneaking) return
		val itemFrame = event.rightClicked as ItemFrame
		if (itemFrame.item.type == Material.AIR) return
		// Rotate back (undo the normal action)
		itemFrame.rotation = itemFrame.rotation.rotateCounterClockwise()
		// Switch visibility
		itemFrame.isVisible = !itemFrame.isVisible
		event.player.sendMessage("${WHITE}The item frame is now ${if (itemFrame.isVisible) "visible" else "invisible"}${GRAY} (shift-right-click it to ${if (itemFrame.isVisible) "hide" else "show"} it again)${WHITE}.")
	}

}