/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.eastereggcommand.command

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.JoinConfiguration
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.sucraft.core.common.bukkit.command.CommonTabCompletion
import org.sucraft.core.common.sucraft.command.SuCraftCommand
import org.sucraft.core.common.sucraft.command.SuCraftCommands
import org.sucraft.eastereggcommand.main.SuCraftEasterEggCommandPlugin


object SuCraftEasterEggCommandCommands : SuCraftCommands<SuCraftEasterEggCommandPlugin>(SuCraftEasterEggCommandPlugin.getInstance()) {

	val EASTER_EGG = SuCraftCommand.create(
		this,
		"easteregg",
		{ sender, _, _, _ ->
			sender.sendMessage(
				Component.join(
					JoinConfiguration.noSeparators(),
					Component.text("I found what you are looking for: "),
					Component.text("♩♫♪♫♪").color(NamedTextColor.WHITE).decorate(TextDecoration.UNDERLINED)
						.clickEvent(ClickEvent.openUrl("https://www.youtube.com/watch?v=bHwzYJMaroQ"))
				).color(NamedTextColor.GRAY)
			)
		},
		CommonTabCompletion.EMPTY
	)

}