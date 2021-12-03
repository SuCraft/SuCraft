/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.uuidcommand.command

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.ChatColor.*
import org.sucraft.core.common.bukkit.command.CommonTabCompletion
import org.sucraft.core.common.sucraft.command.SuCraftCommand
import org.sucraft.core.common.sucraft.command.SuCraftCommands
import org.sucraft.core.common.sucraft.player.PlayerByInputString
import org.sucraft.uuidcommand.main.SuCraftUUIDCommandPlugin


object SuCraftUUIDCommandCommands : SuCraftCommands<SuCraftUUIDCommandPlugin>(SuCraftUUIDCommandPlugin.getInstance()) {

	// Settings

	private const val uuidCommandName = "uuid"

	// Commands

	val CHECK_UUID = SuCraftCommand.create(
		this,
		uuidCommandName,
		onCommand@{ sender, _, _, arguments ->
			if (arguments.isEmpty()) {
				sender.sendMessage(Component.text("Use /${uuidCommandName} <name>").color(NamedTextColor.WHITE))
				return@onCommand
			}
			val nameInput: String = arguments[0]
			val foundPlayerUUID = PlayerByInputString.getPlayerUUIDOrSendErrorMessage(nameInput, sender) ?: return@onCommand
			sender.sendMessage("${GRAY}The UUID of ${WHITE}${nameInput}${GRAY} is: ${WHITE}${foundPlayerUUID}${GRAY} (${WHITE}${if (foundPlayerUUID.isOnline()) "online" else "offline"}${GRAY})")
		},
		CommonTabCompletion.onlyFirstArgument(CommonTabCompletion.OFFLINE_PLAYERS)
	)

}