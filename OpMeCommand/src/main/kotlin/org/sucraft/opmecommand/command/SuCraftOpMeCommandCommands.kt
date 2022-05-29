/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.opmecommand.command

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.JoinConfiguration
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.sucraft.core.common.bukkit.command.CommonTabCompletion
import org.sucraft.core.common.sucraft.command.SuCraftCommand
import org.sucraft.core.common.sucraft.command.SuCraftCommands
import org.sucraft.opmecommand.main.SuCraftOpMeCommandPlugin

object SuCraftOpMeCommandCommands : SuCraftCommands<SuCraftOpMeCommandPlugin>(SuCraftOpMeCommandPlugin.getInstance()) {

	val OP_ME = SuCraftCommand.createPlayerOnly(
		this,
		"opme",
		{ player, _, _, _ ->
			player.sendMessage(
				Component.join(
					JoinConfiguration.noSeparators(),
					Component.text("[Server: Made ${player.name} a server operator]").color(NamedTextColor.GRAY).decorate(TextDecoration.ITALIC),
					Component.text(" *SIKE*").color(NamedTextColor.DARK_GRAY)
				)
			)
		},
		CommonTabCompletion.EMPTY
	)

}