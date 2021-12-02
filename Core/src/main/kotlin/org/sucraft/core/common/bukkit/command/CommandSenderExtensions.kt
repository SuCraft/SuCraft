/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.bukkit.command

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

object CommandSenderExtensions {

	// Visibility (for players)

	fun CommandSender.getVisiblePlayers() =
		Bukkit.getOnlinePlayers().let {
			if (this is Player)
				it.filter { this.canSee(it) }
			else it
		}

	fun CommandSender.getHiddenPlayers() =
		if (this is Player)
			Bukkit.getOnlinePlayers().filter { !this.canSee(it) }
		else
			emptyList()

}