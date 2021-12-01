/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.sucraft.player

import org.bukkit.Bukkit
import org.bukkit.ChatColor.*
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*


object PlayerByInputString {

	fun getOnline(input: String): Player? =
		Bukkit.getPlayerExact(input) ?: try {
			Bukkit.getPlayer(UUID.fromString(input))
		} catch (e: IllegalArgumentException) {
			null
		}

	fun getOnlineOrSendErrorMessage(input: String, requester: CommandSender): Player? {
		val foundPlayer = getOnline(input)
		if (foundPlayer == null) requester.sendMessage("${WHITE}${input}${RED} is not online.")
		return foundPlayer
	}

	/**
	 * Only returns a non-null value if the player has played on the server before
	 */
	fun getOffline(input: String): OfflinePlayer? =
		Bukkit.getOfflinePlayerIfCached(input) ?: try {
			Bukkit.getOfflinePlayer(UUID.fromString(input))
		} catch (e: IllegalArgumentException) {
			null
		}?.takeIf { it.hasPlayedBefore() }

	fun getOfflineOrSendErrorMessage(input: String, requester: CommandSender): OfflinePlayer? {
		val foundPlayer = getOffline(input)
		if (foundPlayer == null) requester.sendMessage("${RED}No player called ${WHITE}${input}${RED} was found.")
		return foundPlayer
	}

}