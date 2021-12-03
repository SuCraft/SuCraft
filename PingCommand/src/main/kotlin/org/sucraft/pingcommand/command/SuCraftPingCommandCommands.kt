/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.pingcommand.command

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.JoinConfiguration
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.sucraft.core.common.bukkit.command.CommonTabCompletion
import org.sucraft.core.common.general.math.DecimalUtils
import org.sucraft.core.common.sucraft.command.SuCraftCommand
import org.sucraft.core.common.sucraft.command.SuCraftCommands
import org.sucraft.pingcommand.main.SuCraftPingCommandPlugin
import java.text.DecimalFormat
import kotlin.math.min


object SuCraftPingCommandCommands : SuCraftCommands<SuCraftPingCommandPlugin>(SuCraftPingCommandPlugin.getInstance()) {

	private val tpsFormat: DecimalFormat = DecimalUtils.createAmericanDecimalFormat("0.0")

	val PING = SuCraftCommand.createPlayerOnly(
		this,
		"ping",
		{ player, _, _, _ ->
			val tps = Bukkit.getTPS()
			val tps1Minute = min(tps[0], 20.0)
			val tps15Minutes = min(tps[2], 20.0)
			player.sendMessage(
				Component.join(
					JoinConfiguration.noSeparators(),
					Component.text("Pong!").color(NamedTextColor.YELLOW),
					Component.text(" Network ping: ").color(NamedTextColor.GRAY),
					Component.text(player.spigot().ping).color(NamedTextColor.WHITE),
					Component.text(" ms, TPS: ").color(NamedTextColor.GRAY),
					Component.text(tpsFormat.format(tps1Minute)).color(NamedTextColor.WHITE),
					Component.text(" (past minute), ").color(NamedTextColor.GRAY),
					Component.text(tpsFormat.format(tps15Minutes)).color(NamedTextColor.WHITE),
					Component.text(" (past 15 minutes)").color(NamedTextColor.GRAY)
				)
			)
		},
		CommonTabCompletion.EMPTY
	)

	val PONG = SuCraftCommand.createPlayerOnly(
		this,
		"pong",
		{ player, _, _, _ ->
			player.sendMessage(
				Component.text("Hey, that's not how you're supposed to play this game.").color(NamedTextColor.YELLOW)
			)
		},
		CommonTabCompletion.EMPTY
	)

}