/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.dynamicmotd

import kotlinx.coroutines.launch
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.Component.join
import net.kyori.adventure.text.Component.text
import org.bukkit.Bukkit
import org.sucraft.common.concurrent.bukkitScope
import org.sucraft.common.math.testProbability
import org.sucraft.modules.donators.Donators
import org.sucraft.modules.donators.PerpetualDonatorRank
import org.sucraft.modules.offlineplayerinformation.OfflinePlayerInformation.getDetailedOfflinePlayer
import org.sucraft.modules.offlineplayerinformation.OfflinePlayerInformation.getName
import java.net.InetAddress

private fun pickFocusColor() = focusColors.random()

fun pickMOTD(address: InetAddress): Component {
	// A special message
	if (wonderfulIPAddresses.any { it in "$address" } && testProbability(wonderfulMessageProbability))
		return wonderfulMOTD.color(pickFocusColor())
	// Pick a MOTD based on the number of players online
	val onlinePlayers = Bukkit.getOnlinePlayers()
	return when (onlinePlayers.size) {
		1 -> pickMOTDFor1Player(onlinePlayers.first().name)
		in 2..4 -> pickMOTDForFewPlayers(onlinePlayers.map { it.name })
		else -> pickMOTDIndependentOfPlayers()
	}
}

private fun pickMOTDFor1Player(name: String) =
	for1PlayerMOTDs.random()
		.colorIfAbsent(for1PlayerBaseColor)
		.compile(arrayOf(text(name, pickFocusColor())))

private fun pickMOTDForFewPlayers(names: List<String>) =
	join(forFewPlayersJoinConfiguration, names.map { text(it, pickFocusColor()) })
		.colorIfAbsent(forFewPlayersBaseColor)

private fun pickMOTDIndependentOfPlayers(): Component {
	// Show a donator MOTD (only if donator names have been computed already)
	if (testProbability(donatorMOTDProbability))
		pickDonatorMOTD()/*?*/.let { return it }
	// Show a randomly picked MOTD
	return miscMOTDs.random()
		.colorIfAbsent(pickFocusColor())
}

private fun pickDonatorMOTD() =
	getDonatorMOTDsIfComputed()/*?*/.random()
		/*?*/.colorIfAbsent(pickFocusColor())