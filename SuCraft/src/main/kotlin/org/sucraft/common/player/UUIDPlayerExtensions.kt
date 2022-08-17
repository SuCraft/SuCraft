/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.player

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

/**
 * @return A [Player] instance with this UUID, or null if none exists.
 *
 * Note that this may return a non-null value even if the player has already left the server.
 */
@Deprecated("Use getOnlinePlayer() instead", replaceWith = ReplaceWith("getOnlinePlayer()"))
fun UUID.getPlayer() = Bukkit.getPlayer(this)

/**
 * @return An online [Player] instance with this UUID, or null if no such instance exists
 * (for instance because no player with this UUID is online).
 */
fun UUID.getOnlinePlayer() =
	@Suppress("DEPRECATION")
	getPlayer()?.takeIfOnline()

/**
 * @return Whether a [Player] with this UUID is online.
 */
fun UUID.isPlayerOnline() =
	@Suppress("DEPRECATION")
	getPlayer()?.isOnline ?: false