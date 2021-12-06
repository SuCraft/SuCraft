/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.unsignbooks.command

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.ChatColor.*
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BookMeta
import org.sucraft.core.common.bukkit.command.CommonTabCompletion
import org.sucraft.core.common.sucraft.command.SuCraftCommand
import org.sucraft.core.common.sucraft.command.SuCraftCommands
import org.sucraft.unsignbooks.main.SuCraftUnsignBooksPlugin
import org.sucraft.unsignbooks.player.permission.SuCraftUnsignBooksPermissions


object SuCraftUnsignBooksCommands : SuCraftCommands<SuCraftUnsignBooksPlugin>(SuCraftUnsignBooksPlugin.getInstance()) {

	val UNSIGN_BOOK = SuCraftCommand.createPlayerOnly(
		this,
		"unsign",
		onCommand@{ player, _, _, _ ->
			val itemStack = player.inventory.itemInMainHand
			if (itemStack.type != Material.WRITTEN_BOOK) {
				player.sendMessage(
					Component.text(
						when (itemStack.type) {
							Material.WRITABLE_BOOK -> "This book is not signed."
							Material.BOOK -> "This book has no text."
							Material.ENCHANTED_BOOK -> "A magical force rests on the words in this book."
							Material.KNOWLEDGE_BOOK -> "Removing information from this book would violate the no-hiding theorem in quantum physics."
							else -> "You are not holding a written book."
						}
					).color(NamedTextColor.RED)
				)
				return@onCommand
			}
			if (!itemStack.hasItemMeta()) {
				player.sendMessage(Component.text("This book is missing data.").color(NamedTextColor.RED))
				return@onCommand
			}
			val meta = (itemStack.itemMeta as? BookMeta) ?: run {
				player.sendMessage(Component.text("This book is missing data.").color(NamedTextColor.RED))
				return@onCommand
			}
			val permissionToUnsign = !meta.hasAuthor() || meta.author.equals(player.name, ignoreCase = true) || player.hasPermission(SuCraftUnsignBooksPermissions.UNSIGN_OTHER_PLAYERS_BOOKS)
			if (!permissionToUnsign) {
				player.sendMessage(Component.text("You can only unsign books signed by yourself.").color(NamedTextColor.GRAY))
				return@onCommand
			}
			// Unsign the book
			val newMeta = meta.clone()
			val newItemStack = ItemStack(Material.WRITABLE_BOOK)
			newItemStack.itemMeta = newMeta
			logger.info("${player.name} unsigned a book ${meta.title?.let { "titled '${it}'" } ?: "without a title"}: $itemStack")
			player.inventory.setItemInMainHand(newItemStack)
			player.sendMessage("${GREEN}Unsigned ${meta.title?.let { "\"${WHITE}${it}${GREEN}\"" } ?: "${WHITE}untitled book"}${GREEN}!")
		},
		CommonTabCompletion.EMPTY
	)

}