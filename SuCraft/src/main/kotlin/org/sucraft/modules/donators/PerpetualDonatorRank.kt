/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.donators

import kotlinx.coroutines.launch
import org.bukkit.entity.Player
import org.sucraft.common.concurrent.bukkitScope
import org.sucraft.common.permission.permissionsBukkitInstance
import org.sucraft.modules.offlineplayerinformation.OfflinePlayerInformation.getDetailedOfflinePlayer
import org.sucraft.modules.offlineplayerinformation.OfflinePlayerInformation.getName
import java.util.*

/**
 * A donator rank given to players in perpetuity for donating before subscription donations are introduced.
 */
enum class PerpetualDonatorRank(val permissionsGroupName: String) {

	STONE("stone_donator"),
	IRON("iron_donator"),
	GOLD("gold_donator"),
	DIAMOND("diamond_donator");

	val permissionsGroup by lazy {
		permissionsBukkitInstance.getGroup(permissionsGroupName)!!
	}

	/**
	 * The [UUID]s of the players with this rank.
	 * Only computed once, so may become incomplete during a server session.
	 */
	val playerUUIDs by lazy { permissionsGroup.playerUUIDs?.toTypedArray() ?: emptyArray() }

	private lateinit var playerNames: Array<String>

	/**
	 * The [UUID]s of the players with this rank.
	 * Only computed once, so may become incomplete during a server session.
	 *
	 * This will be computed asynchronously due to the need of loading player files.
	 *
	 * Returns null if not finished computing asynchronously yet.
	 */
	fun getPlayerNamesIfComputed() = if (::playerNames.isInitialized) playerNames else null

	/**
	 * Must only be called in [Donators.onInitialize], exactly once for each [rank][PerpetualDonatorRank].
	 */
	fun startComputingPlayerNames() {
		bukkitScope.launch {
			playerNames = playerUUIDs.mapNotNull {
				it.getDetailedOfflinePlayer()?.getName()
			}.toTypedArray()
		}
	}

	companion object {

		val lowest = values()[0]

		val valuesReversed = values().reversed().toTypedArray()

	}

}

fun Player.hasPerpetualDonatorRank(rank: PerpetualDonatorRank) = uniqueId in rank.permissionsGroup.playerUUIDs

val Player.perpetualDonatorRank: PerpetualDonatorRank?
	get() = PerpetualDonatorRank.valuesReversed.firstOrNull { hasPerpetualDonatorRank(it) }

val Player.hasPerpetualDonatorRank
	get() = perpetualDonatorRank != null