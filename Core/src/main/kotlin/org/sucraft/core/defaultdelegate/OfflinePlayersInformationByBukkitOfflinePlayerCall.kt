/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.defaultdelegate

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.sucraft.core.common.sucraft.delegate.OfflinePlayersInformation
import org.sucraft.core.common.sucraft.player.PlayerUUID
import java.util.*


object OfflinePlayersInformationByBukkitOfflinePlayerCall : OfflinePlayersInformation {

	private class OfflinePlayerInformationByBukkitOfflinePlayerCall(val uuid: UUID) : OfflinePlayersInformation.OfflinePlayerInformation {

		override fun getName() = getOfflinePlayer().name

		override fun isOnline() = getOfflinePlayer().isOnline

		override fun getOfflinePlayer() = Bukkit.getOfflinePlayer(uuid)

		override fun getPlayerUUID() = PlayerUUID.getAlreadyConfirmed(uuid)

	}

	override fun getInformation(uuid: UUID): OfflinePlayersInformation.OfflinePlayerInformation? = getInformation(Bukkit.getOfflinePlayer(uuid))

	override fun getInformation(player: OfflinePlayer): OfflinePlayersInformation.OfflinePlayerInformation? = if (player.hasPlayedBefore()) OfflinePlayerInformationByBukkitOfflinePlayerCall(player.uniqueId) else null

}