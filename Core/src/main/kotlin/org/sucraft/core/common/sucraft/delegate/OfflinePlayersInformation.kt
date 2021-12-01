/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.sucraft.delegate

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.sucraft.core.common.sucraft.player.PlayerUUID
import org.sucraft.core.common.sucraft.plugin.SuCraftDelegateHolder
import java.util.*


interface OfflinePlayersInformation {

	// Companion (implementation)

	companion object : SuCraftDelegateHolder<OfflinePlayersInformation>()

	// Interface

	interface OfflinePlayerInformation {

		fun getName(): String?

		fun isOnline(): Boolean

		fun getOfflinePlayer(): OfflinePlayer

		fun getPlayerUUID(): PlayerUUID

	}

	/**
	 * Returns null if no such player has ever played on the server
	 * The name is case-insensitive, so the resulting information may have a differently capitalized name than the one that was given
	 */
	fun getInformation(name: String) = Bukkit.getPlayerExact(name)?.let { getInformation(it) } ?: Bukkit.getOfflinePlayerIfCached(name)?.let { getInformation(it) } ?: Bukkit.getOfflinePlayer(name)?.let { getInformation(it) }

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

}