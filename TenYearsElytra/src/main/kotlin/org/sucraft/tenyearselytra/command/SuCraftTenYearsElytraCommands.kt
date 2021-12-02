/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.tenyearselytra.command

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.ChatColor.*
import org.bukkit.inventory.ItemStack
import org.sucraft.core.common.bukkit.command.CommonTabCompletion
import org.sucraft.core.common.sucraft.command.SuCraftCommand
import org.sucraft.core.common.sucraft.command.SuCraftCommands
import org.sucraft.tenyearselytra.data.ElytraData
import org.sucraft.tenyearselytra.main.SuCraftTenYearsElytraPlugin
import org.sucraft.tenyearselytra.player.permission.SuCraftTenYearsElytraPermissions


object SuCraftTenYearsElytraCommands : SuCraftCommands<SuCraftTenYearsElytraPlugin>(SuCraftTenYearsElytraPlugin.getInstance()) {

	// Settings

	const val tenYearsElytraCommandName = "10years"
	const val removeTenYearsElytraSubArgument = "remove"

	// Commands

	val TEN_YEARS = SuCraftCommand.createPlayerOnly(
		this,
		tenYearsElytraCommandName,
		onCommand@{ player, _, _, arguments ->
			val itemStack = player.inventory.itemInMainHand
			// Remove the effect from an elytra
			if (arguments.isNotEmpty() && arguments[0].equals(removeTenYearsElytraSubArgument, ignoreCase = true)) {
				if (ElytraData.isFestiveElytra(itemStack)) {
					val regularItemStack: ItemStack = ElytraData.regularizeElytra(itemStack)
					player.inventory.setItemInMainHand(regularItemStack)
					player.sendMessage(Component.text("Your elytra is now regular again.").color(NamedTextColor.WHITE))
					return@onCommand
				}
				player.sendMessage(Component.text("You must be holding a festive elytra to do this.").color(NamedTextColor.WHITE))
				return@onCommand
			}
			// Check if the player has permission
			if (!player.hasPermission(SuCraftTenYearsElytraPermissions.CREATE_FESTIVE_ELYTRA)) {
				player.sendMessage(Component.text("This command existed before to celebrate SuCraft being online for 10 years :)").color(NamedTextColor.GRAY))
				return@onCommand
			}
			// Check if the elytra is festive or can be made festive
			if (!ElytraData.isFestiveOrFestivableElytra(itemStack)) {
				player.sendMessage(Component.text("You must be holding an elytra to make it festive!").color(NamedTextColor.WHITE))
				return@onCommand
			}
			// Check if the elytra is already festive
			if (ElytraData.isFestiveElytra(itemStack)) {
				player.sendMessage("${WHITE}This elytra is already festive!${GRAY} You can remove it with ${WHITE}/${tenYearsElytraCommandName} ${removeTenYearsElytraSubArgument}${GRAY}.")
				return@onCommand
			}
			val festiveItemStack: ItemStack = ElytraData.festivizeElytra(player, itemStack)
			player.inventory.setItemInMainHand(festiveItemStack)
			player.sendTitle("Thank you~", "Thank you for being a part of this wonderful community <3", 10, 100, 45)
			player.sendMessage(MiniMessage.builder().build().parse("<rainbow:2>Enjoy your special festive elytra :)</rainbow>"))
		},
		CommonTabCompletion.EMPTY
	)

}