/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.sucraft.player

import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.sucraft.core.common.sucraft.delegate.OfflinePlayersInformation
import java.util.*


/**
 * A UUID that is confirmed to belong to a player that has played before
 */
@JvmInline
value class PlayerUUID private constructor(val uuid: UUID) {

	// Construction

	companion object {

		fun get(uuid: UUID) = if (OfflinePlayersInformation.get().hasPlayedBefore(uuid)) PlayerUUID(uuid) else null

		fun get(player: Player): PlayerUUID = getAlreadyConfirmed(player.uniqueId)

		fun getAlreadyConfirmed(uuid: UUID) = PlayerUUID(uuid)

		fun getAlreadyConfirmed(player: OfflinePlayer) = getAlreadyConfirmed(player.uniqueId)

		/**
		 * @throws[IllegalArgumentException] When the given string is not a valid UUID, or the resulting UUID does not belong to a player that has played before
		 */
		fun fromString(string: String): PlayerUUID =
			get(UUID.fromString(string)) ?: throw IllegalArgumentException("The UUID does not belong to a player that has played before")

	}

	// Convenience methods

	fun getOnlinePlayer() = PlayerByUUID.getOnline(uuid)

	fun getVisibleOnline(requester: CommandSender) = PlayerByUUID.getVisibleOnline(uuid, requester)

	fun getOfflinePlayer() = PlayerByUUID.getOffline(uuid)

	fun isOnline() = getOnlinePlayer()?.isOnline ?: false

	// Record methods (hashCode and equals no longer used due to turning PlayerUUID into a value class)

//	override fun hashCode() = uuid.hashCode()
//
//	override fun equals(other: Any?) =
//		if (other is PlayerUUID)
//			uuid == other.uuid
//		else
//			uuid == other

	override fun toString() = uuid.toString()

}