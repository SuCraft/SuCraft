/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.bukkit.scheduler

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask
import org.sucraft.core.common.sucraft.player.PlayerUUID

object RunInFuture {

	fun forPlayerIfOnline(plugin: JavaPlugin, player: Player, action: (Player) -> Any?, delay: Long): BukkitTask {
		val uuid = PlayerUUID.get(player)
		return Bukkit.getScheduler().runTaskLater(plugin, Runnable {
			uuid.getPlayer()?.let(action)
		}, delay)
	}

}