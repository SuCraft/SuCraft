/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.player

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.sucraft.common.function.runEach

/**
 * Performs the given [action] for each player that is [online][Player.isOnline].
 */
fun forEachPlayer(action: (Player) -> Unit) = Bukkit.getOnlinePlayers().filter { it.isOnline }.forEach(action)

/**
 * Performs the given [action] for each player that is [online][Player.isOnline].
 */
fun runEachPlayer(action: Player.() -> Unit) = Bukkit.getOnlinePlayers().filter { it.isOnline }.runEach(action)