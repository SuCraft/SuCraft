/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.mysteryboxes.listener

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.JoinConfiguration
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.block.BlockPlaceEvent
import org.sucraft.core.common.bukkit.block.BlockCoordinates
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.mysteryboxes.command.SuCraftMysteryBoxesCommands
import org.sucraft.mysteryboxes.data.MysteryBoxData
import org.sucraft.mysteryboxes.main.SuCraftMysteryBoxesPlugin

object MysteryBoxPlaceMessageListener : SuCraftComponent<SuCraftMysteryBoxesPlugin>(SuCraftMysteryBoxesPlugin.getInstance()) {

	// Events

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onBlockPlace(event: BlockPlaceEvent) {

		if (!MysteryBoxData.isMysteryBox(event.itemInHand)) return

		// Log to console
		SuCraftMysteryBoxesCommands.logger.info("${event.player.name} placed a mystery shulker box: ${event.itemInHand} -> ${BlockCoordinates.get(event.blockPlaced)}")

		// Tell player what happened
		event.player.sendMessage(
			Component.join(
				JoinConfiguration.noSeparators(),
				Component.text("The contents of your "),
				MysteryBoxData.getMysteryBoxDisplayName(event.itemInHand.type),
				Component.text(" have been revealed!")
			).color(NamedTextColor.GRAY)
		)

	}

}