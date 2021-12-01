/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.sucraft.player

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.sucraft.core.common.sucraft.delegate.OfflinePlayersInformation
import java.util.*


object PlayerByUUID {

	// Functionality

	fun getOnline(uuid: UUID): Player? = Bukkit.getPlayer(uuid)

	fun getVisibleOnline(uuid: UUID, requester: CommandSender): Player? =
		getOnline(uuid)?.takeIf { (requester as? Player)?.canSee(it) ?: true }

	/**
	 * Only returns a non-null value if the player has played on the server before
	 */
	fun getOffline(uuid: UUID): OfflinePlayer? =
		OfflinePlayersInformation.get().getInformation(uuid)?.getOfflinePlayer()

}