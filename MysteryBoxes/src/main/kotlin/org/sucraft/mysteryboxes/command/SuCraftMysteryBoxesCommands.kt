/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.mysteryboxes.command

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.JoinConfiguration
import net.kyori.adventure.text.format.NamedTextColor
import org.sucraft.core.common.bukkit.command.CommonTabCompletion
import org.sucraft.core.common.bukkit.material.MaterialGroups
import org.sucraft.core.common.sucraft.command.SuCraftCommand
import org.sucraft.core.common.sucraft.command.SuCraftCommands
import org.sucraft.mysteryboxes.data.MysteryBoxData
import org.sucraft.mysteryboxes.main.SuCraftMysteryBoxesPlugin
import org.sucraft.mysteryboxes.player.permission.SuCraftMysteryBoxesPermissions
import org.sucraft.supporters.chat.SupportingMessages


object SuCraftMysteryBoxesCommands : SuCraftCommands<SuCraftMysteryBoxesPlugin>(SuCraftMysteryBoxesPlugin.getInstance()) {

	val CREATE_MYSTERY_BOX = SuCraftCommand.createPlayerOnly(
		this,
		"mysterybox",
		onCommand@{ player, _, _, _ ->

			if (!player.hasPermission(SuCraftMysteryBoxesPermissions.CREATE_MYSTERY_BOX)) {
				SupportingMessages.sendOnlySupportersHaveAbility(player, "create mystery shulker boxes, where the contents are a surprise")
				return@onCommand
			}

			val itemStack = player.inventory.itemInMainHand
			// Check if the player is holding a shulker box
			if (itemStack.type !in MaterialGroups.shulkerBox) {
				player.sendMessage(Component.text("You must be holding a shulker box to make it a mystery box!").color(NamedTextColor.WHITE))
				return@onCommand
			}

			// Check that the player is not already holding a mystery box
			if (MysteryBoxData.isMysteryBox(itemStack)) {
				player.sendMessage(Component.text("You are already holding a mystery box.").color(NamedTextColor.WHITE))
				return@onCommand
			}

			// Make the item a mystery box
			val newItemStack = MysteryBoxData.makeMysteryBox(itemStack)

			// Place the mystery box in the inventory
			player.inventory.setItemInMainHand(newItemStack)

			// Log to console
			logger.info("${player.name} created a mystery shulker box: $itemStack -> $newItemStack")

			// Tell player what happened
			player.sendMessage(
				Component.join(
					JoinConfiguration.noSeparators(),
					Component.text("You created a "),
					MysteryBoxData.getMysteryBoxDisplayName(newItemStack.type),
					Component.text("!")
				).color(NamedTextColor.GRAY)
			)

		},
		CommonTabCompletion.EMPTY
	)

}