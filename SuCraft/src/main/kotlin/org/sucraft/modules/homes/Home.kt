/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.homes

import org.bukkit.entity.Player
import org.sucraft.modules.offlineplayerinformation.DetailedOfflinePlayer

/**
 * An interface that represents a home.
 */
interface Home {

	val owner: DetailedOfflinePlayer

	fun isOwnedBy(player: DetailedOfflinePlayer) = owner == player

	fun isOwnedBy(player: Player) = owner.isPlayer(player)

}