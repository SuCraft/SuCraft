/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.supporters.chat

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.JoinConfiguration
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player


object SupportingMessages {

	const val discordSupportInfoChannelName = "guide"
	const val discordSupportInfoURL = "https://discord.gg/egH7dGX"

	fun sendOnlySupportersHaveAbility(player: Player, ability: String) =
		player.sendMessage(
			Component.text("Supporters can ${ability}!").color(NamedTextColor.AQUA)
		)

	fun sendSupportingInfoLink(player: Player) =
		player.sendMessage(
			Component.join(
				JoinConfiguration.noSeparators(),
				Component.text("Check the Discord server #${discordSupportInfoChannelName} channel :)").color(NamedTextColor.WHITE),
				Component.text(" "),
				Component.text(discordSupportInfoURL).color(NamedTextColor.GREEN)
			)
		)

}