/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.itemsonhead.command

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.JoinConfiguration
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.sucraft.core.common.bukkit.command.CommonTabCompletion
import org.sucraft.core.common.bukkit.material.MaterialGroups
import org.sucraft.core.common.sucraft.chat.SuCraftChatTexts
import org.sucraft.core.common.sucraft.command.SuCraftCommand
import org.sucraft.core.common.sucraft.command.SuCraftCommands
import org.sucraft.core.common.sucraft.delegate.StandardItemStackNames
import org.sucraft.itemsonhead.main.SuCraftItemsOnHeadPlugin
import org.sucraft.itemsonhead.player.permission.SuCraftItemsOnHeadPermissions
import javax.sql.rowset.JoinRowSet


object SuCraftItemsOnHeadCommands : SuCraftCommands<SuCraftItemsOnHeadPlugin>(SuCraftItemsOnHeadPlugin.getInstance()) {

	// Settings

	private const val headCommandName = "head"

	// Commands

	val HEAD = SuCraftCommand.createPlayerOnly(
		this,
		headCommandName,
		onCommand@{ player, _, _, _ ->

			// The description of which types of items this player can put on their head, or null if there are none
			var itemsToPutOnHeadDescription: String? = null
			if (player.hasPermission(SuCraftItemsOnHeadPermissions.ALL_ITEMS)) {
				itemsToPutOnHeadDescription = "an item or block"
			} else {
				if (player.hasPermission(SuCraftItemsOnHeadPermissions.HELMETS)) {
					if (itemsToPutOnHeadDescription == null) {
						itemsToPutOnHeadDescription = "a helmet"
					} else {
						itemsToPutOnHeadDescription += " or helmet"
					}
				}
				if (player.hasPermission(SuCraftItemsOnHeadPermissions.BANNERS)) {
					if (itemsToPutOnHeadDescription == null) {
						itemsToPutOnHeadDescription = "a banner"
					} else {
						itemsToPutOnHeadDescription += " or banner"
					}
				}
			}

			// If the player cannot put any items on their head
			if (itemsToPutOnHeadDescription == null) {
				SuCraftChatTexts.sendNoPermission(player)
				return@onCommand
			}

			// See if the player can use their currently held item
			val itemInHand: ItemStack = player.inventory.itemInMainHand.clone()
			val itemType = itemInHand.type
			var canPutCurrentlyHeldItemOnHead =
				itemType != Material.AIR
						&& (
						player.hasPermission(SuCraftItemsOnHeadPermissions.ALL_ITEMS)
						|| player.hasPermission(SuCraftItemsOnHeadPermissions.HELMETS) && itemType in MaterialGroups.helmet
						|| player.hasPermission(SuCraftItemsOnHeadPermissions.BANNERS) && itemType in MaterialGroups.standingOrWallBanner
						)

			// If the player cannot use their currently held item
			if (!canPutCurrentlyHeldItemOnHead) {
				player.sendMessage(
					Component.join(
						JoinConfiguration.noSeparators(),
						Component.text("Hold $itemsToPutOnHeadDescription first, then use "),
						Component.text("/${headCommandName}").color(NamedTextColor.AQUA),
						Component.text(" to set it on your head.")
					).color(NamedTextColor.WHITE)
				)
				return@onCommand
			}

			// Set the item on the head
			val helmet: ItemStack? = player.inventory.helmet?.clone()
			player.inventory.setItemInMainHand(helmet)
			player.inventory.helmet = itemInHand
			logger.info("${player.name} used /$headCommandName to put $itemInHand on head (was $helmet before)")
			player.sendMessage(
				Component.join(
					JoinConfiguration.noSeparators(),
					Component.text("Changed item on head to "),
					Component.text(StandardItemStackNames.get().get(itemInHand)).color(NamedTextColor.AQUA),
					Component.text(".")
				).color(NamedTextColor.WHITE)
			)
		},
		CommonTabCompletion.EMPTY
	)

}