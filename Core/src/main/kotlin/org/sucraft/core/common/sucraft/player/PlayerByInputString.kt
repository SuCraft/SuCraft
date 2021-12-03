/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.sucraft.player

import org.bukkit.Bukkit
import org.bukkit.ChatColor.*
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.sucraft.core.common.sucraft.delegate.OfflinePlayersInformation
import java.util.*


@Suppress("MemberVisibilityCanBePrivate")
object PlayerByInputString {

	// Text

	private fun sendOnlinePlayerNotFound(audience: CommandSender, name: String) =
		audience.sendMessage("${WHITE}${name}${RED} is not online.")

	private fun sendOfflinePlayerNotFound(audience: CommandSender, name: String) =
		audience.sendMessage("${RED}No player called ${WHITE}${name}${RED} was found.")

	// Functionality

	fun getOnline(input: String): Player? =
		Bukkit.getPlayerExact(input) ?: try {
			Bukkit.getPlayer(UUID.fromString(input))
		} catch (e: IllegalArgumentException) {
			null
		}

	fun getOnlineOrSendErrorMessage(input: String, requester: CommandSender): Player? {
		val foundPlayer = getOnline(input)
		if (foundPlayer == null) sendOnlinePlayerNotFound(requester, input)
		return foundPlayer
	}

	fun getVisibleOnline(input: String, requester: CommandSender): Player? =
		getOnline(input)?.takeIf { (requester as? Player)?.canSee(it) ?: true }

	fun getVisibleOnlineOrSendErrorMessage(input: String, requester: CommandSender): Player? {
		val foundPlayer = getVisibleOnline(input, requester)
		if (foundPlayer == null) sendOnlinePlayerNotFound(requester, input)
		return foundPlayer
	}

	/**
	 * Only returns a non-null value if the player has played on the server before
	 */
	fun getOfflineInformation(input: String): OfflinePlayersInformation.OfflinePlayerInformation? =
		OfflinePlayersInformation.get().getInformation(input) ?: try {
			OfflinePlayersInformation.get().getInformation(UUID.fromString(input))
		} catch (e: IllegalArgumentException) {
			null
		}

	fun getOfflineInformationOrSendErrorMessage(input: String, requester: CommandSender): OfflinePlayersInformation.OfflinePlayerInformation? {
		val foundPlayerInformation = getOfflineInformation(input)
		if (foundPlayerInformation == null) sendOfflinePlayerNotFound(requester, input)
		return foundPlayerInformation
	}

	/**
	 * Only returns a non-null value if the player has played on the server before
	 */
	fun getOffline(input: String): OfflinePlayer? =
		getOfflineInformation(input)?.getOfflinePlayer()

	fun getOfflineOrSendErrorMessage(input: String, requester: CommandSender) =
		getOfflineInformationOrSendErrorMessage(input, requester)?.getOfflinePlayer()

	/**
	 * Only returns a non-null value if the player has played on the server before
	 */
	fun getPlayerUUID(input: String) =
		getOfflineInformation(input)?.getPlayerUUID()

	fun getPlayerUUIDOrSendErrorMessage(input: String, requester: CommandSender) =
		getOfflineInformationOrSendErrorMessage(input, requester)?.getPlayerUUID()

}