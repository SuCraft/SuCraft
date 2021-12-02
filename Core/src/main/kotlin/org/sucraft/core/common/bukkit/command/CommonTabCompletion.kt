/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.bukkit.command

import org.bukkit.Bukkit
import org.sucraft.core.common.sucraft.command.TabCompleter
import java.util.*
import org.sucraft.core.common.bukkit.command.CommandSenderExtensions.getVisiblePlayers


object CommonTabCompletion {

	val EMPTY: TabCompleter = { _, _, _, _ -> emptyList() }
	val ONLINE_PLAYERS: TabCompleter = { _, _, _, _ -> Bukkit.getOnlinePlayers().map { it.name } }
	val VISIBLE_ONLINE_PLAYERS: TabCompleter = { sender, _, _, _ -> sender.getVisiblePlayers().map { it.name } }

}