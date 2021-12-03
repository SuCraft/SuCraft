/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.sucraft.delegate

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.sucraft.core.common.sucraft.player.PlayerUUID
import org.sucraft.core.common.sucraft.plugin.SuCraftDelegate
import org.sucraft.core.common.sucraft.plugin.SuCraftDelegateHolder
import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import java.util.*


interface OfflinePlayersInformation<P: SuCraftPlugin>: SuCraftDelegate<P> {

	// Companion (implementation)

	companion object : SuCraftDelegateHolder<OfflinePlayersInformation<*>>()

	// Delegate name

	override fun getDelegateInterfaceName() = OfflinePlayersInformation::class.simpleName!!

	// Interface

	interface OfflinePlayerInformation {

		fun getName(): String?

		fun getOfflinePlayer(): OfflinePlayer

		fun getPlayerUUID(): PlayerUUID

		// Convenience methods

		fun getOnlinePlayer() = getPlayerUUID().getOnlinePlayer()

		fun isOnline() = getPlayerUUID().isOnline()

	}

	/**
	 * Returns null if no such player has ever played on the server
	 * The name is case-insensitive, so the resulting information may have a differently capitalized name than the one that was given
	 */
	@Suppress("DEPRECATION")
	fun getInformation(name: String) = Bukkit.getPlayerExact(name)?.let { getInformation(it) } ?: Bukkit.getOfflinePlayerIfCached(name)?.let { getInformation(it) } ?: getInformation(Bukkit.getOfflinePlayer(name))

	/**
	 * Returns null if no such player has ever played on the server
	 */
	fun getInformation(uuid: UUID): OfflinePlayerInformation?

	/**
	 * Returns null if no such player has ever played on the server
	 */
	fun getInformation(player: OfflinePlayer) = getInformation(player.uniqueId)

	fun getInformation(uuid: PlayerUUID) = getInformation(uuid.uuid)

	fun hasPlayedBefore(uuid: UUID) = getInformation(uuid) != null

	fun hasPlayedBefore(player: OfflinePlayer) = getInformation(player) != null

	/**
	 * Returns a list of all the correctly cased names of players that have played before
	 */
	fun getOfflinePlayerNames(): List<String>

}