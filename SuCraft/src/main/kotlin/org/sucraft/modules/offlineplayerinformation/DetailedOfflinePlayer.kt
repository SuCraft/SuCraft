/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.offlineplayerinformation

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import java.util.*

// Class

/**
 * An alternative to [OfflinePlayer], because [OfflinePlayer] instances can also exist
 * for UUIDs that have never played on the server, that are not even able to represent a
 * player, or that have played on the server but for which basic information (such as a username)
 * is not known. Additionally, [OfflinePlayer] instances may require lookups to the player data files,
 * which can be prevented by an own implementation layer of the operations for which this would happen.
 * At the time of writing, this has not been implemented.
 *
 * Ever instance of this class is confirmed to belong to a player that has played before,
 * and for which some basic information such as a username is known.
 *
 * Because this class replaces [OfflinePlayer], [OfflinePlayer] has been marked as deprecated
 * within this project.
 *
 * For legacy reasons, we may wish to implement a custom username-UUID relation in the future
 * (this will be implemented within the Paper fork's server code).
 * Any previously observed username-UUID pairs should not be relied upon.
 * To prevent plugins breaking, we may have to disallow players
 * for whom a username-UUID relation was established in a server session and
 * who changed their username afterwards, during that server session,
 * from joining the server until the next server restart.
 *
 * This class somewhat resembles [KnownOfflinePlayerInformation], but important to note is that this class
 * provides access to known information stored in [OfflinePlayerInformation], while [KnownOfflinePlayerInformation]
 * is used internally in [OfflinePlayerInformation] to store the cached data and provide methods for updating it.
 * Importantly, instances of [KnownOfflinePlayerInformation] become orphaned and meaningless when all cached data
 * for an offline player is deleted (such as when they join), so instances of [KnownOfflinePlayerInformation] should
 * never be used outside of [OfflinePlayerInformation] or the information retrieving methods provided by this class
 * (which go via [OfflinePlayerInformation] and do not keep any [KnownOfflinePlayerInformation] instances).
 */
data class DetailedOfflinePlayer constructor(val uuid: UUID) {

	// Convenience methods

	private val offlinePlayer
		get() = Bukkit.getOfflinePlayer(uuid)

	fun getPlayer() = Bukkit.getPlayer(uuid)

	// TODO move this: this should be an extension in class responsible for visibilities
//	fun getPlayerIfVisible(observation: PlayerObservationViewpoint) = uuid.getSuCraftPlay

	fun isOnline() = getPlayer()?.isOnline ?: false

	fun isPlayer(player: Player) = uuid == player.uniqueId

	// OfflinePlayer methods for which this class acts only as a proxy

	fun banPlayer(reason: String?) =
		offlinePlayer.banPlayer(reason)

	fun banPlayer(reason: String?, source: String?) =
		offlinePlayer.banPlayer(reason, source)

	fun banPlayer(reason: String?, expires: Date) =
		offlinePlayer.banPlayer(reason, expires)

	fun banPlayer(reason: String?, expires: Date, source: String?) =
		offlinePlayer.banPlayer(reason, expires, source)

	fun banPlayer(reason: String?, expires: Date, source: String?, kickIfOnline: Boolean) =
		offlinePlayer.banPlayer(reason, expires, source, kickIfOnline)

	val isBanned
		get() = offlinePlayer.isBanned

	var isWhitelisted
		get() = offlinePlayer.isWhitelisted
		set(value) {
			offlinePlayer.isWhitelisted = value
		}

	// Override Object methods

	override fun hashCode() = uuid.hashCode()

	override fun equals(other: Any?) = (other as? DetailedOfflinePlayer)?.let { it.uuid == uuid } ?: false

	override fun toString() = "$uuid"

}