/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.bukkit.command

import org.bukkit.Bukkit
import org.sucraft.core.common.sucraft.command.TabCompleter
import org.sucraft.core.common.bukkit.command.CommandSenderExtensions.getVisiblePlayers
import org.sucraft.core.common.sucraft.command.DeferredPlayerTabCompleter
import org.sucraft.core.common.sucraft.command.DeferredTabCompleter
import org.sucraft.core.common.sucraft.command.PlayerTabCompleter
import org.sucraft.core.common.sucraft.delegate.OfflinePlayersInformation


@Suppress("MemberVisibilityCanBePrivate")
object CommonTabCompletion {

	val EMPTY: TabCompleter = { _, _, _, _ -> emptyList() }
	val ONLINE_PLAYERS: TabCompleter = { _, _, _, _ -> Bukkit.getOnlinePlayers().map { it.name } }
	val VISIBLE_ONLINE_PLAYERS: TabCompleter = { sender, _, _, _ -> sender.getVisiblePlayers().map { it.name } }
	val OFFLINE_PLAYERS: TabCompleter = { _, _, _, _ -> OfflinePlayersInformation.get().getOfflinePlayerNames() }

	fun deferred(completer: DeferredTabCompleter): TabCompleter =
		{ sender, command, label, arguments -> completer(sender, command, label, arguments)(sender, command, label, arguments) }

	fun deferredPlayer(completer: DeferredPlayerTabCompleter): PlayerTabCompleter =
		{ player, command, label, arguments -> completer(player, command, label, arguments)(player, command, label, arguments) }

	fun onlyFirstArgument(completer: TabCompleter): TabCompleter =
		deferred { _, _, _, arguments -> if (arguments.size >= 2) completer else EMPTY }

	fun onlyFirstArgumentPlayer(completer: PlayerTabCompleter): PlayerTabCompleter =
		deferredPlayer { _, _, _, arguments -> if (arguments.size >= 2) completer else EMPTY }

}