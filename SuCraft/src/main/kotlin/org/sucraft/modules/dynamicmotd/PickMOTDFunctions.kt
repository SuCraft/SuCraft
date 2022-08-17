/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.dynamicmotd

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.Component.join
import net.kyori.adventure.text.Component.text
import org.bukkit.Bukkit
import org.sucraft.common.math.testProbability
import java.net.InetAddress

private fun pickFocusColor() = focusColors.random()

fun pickMOTD(address: InetAddress): Component {
	// A special message
	if (wonderfulIPAddress in "$address" && testProbability(wonderfulMessageProbability))
		return wonderfulMOTD.color(pickFocusColor())
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

private fun pickMOTDIndependentOfPlayers() =
	miscMOTDs.random()
		.colorIfAbsent(pickFocusColor())