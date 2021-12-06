/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.discordinfo.command

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.JoinConfiguration
import net.kyori.adventure.text.format.NamedTextColor
import org.sucraft.core.common.bukkit.command.CommonTabCompletion
import org.sucraft.core.common.sucraft.command.SuCraftCommand
import org.sucraft.core.common.sucraft.command.SuCraftCommands
import org.sucraft.discordinfo.data.DiscordChannel
import org.sucraft.discordinfo.main.SuCraftDiscordInfoPlugin


object SuCraftDiscordInfoCommands : SuCraftCommands<SuCraftDiscordInfoPlugin>(SuCraftDiscordInfoPlugin.getInstance()) {

	val SEE_DISCORD_URL = SuCraftCommand.createPlayerOnly(
		this,
		"discord",
		{ player, _, _, _ ->
			player.sendMessage(
				Component.join(
					JoinConfiguration.noSeparators(),
					Component.text("Join our Discord server at:"),
					Component.space(),
					DiscordChannel.GENERAL.getURLComponent(color = NamedTextColor.AQUA)
				).color(NamedTextColor.WHITE)
			)
		},
		CommonTabCompletion.EMPTY
	)

}