/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.supporters.chat

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.JoinConfiguration
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player
import org.sucraft.discordinfo.data.DiscordChannel

object SupportingMessages {

	private val discordSupportInfoChannel = DiscordChannel.GUIDE

	fun sendOnlySupportersHaveAbility(player: Player, ability: String) {
		player.sendMessage(
			Component.text("Supporters can ${ability}! \\o/").color(NamedTextColor.AQUA)
		)
		sendSupportingInfoLink(player)
	}

	fun sendSupportingInfoLink(player: Player) =
		player.sendMessage(
			Component.join(
				JoinConfiguration.noSeparators(),
				Component.text("Check the Discord server ${discordSupportInfoChannel.hashtagChannelName} channel:"),
				Component.space(),
				discordSupportInfoChannel.getURLComponent(color = NamedTextColor.GREEN)
			).color(NamedTextColor.WHITE)
		)

}