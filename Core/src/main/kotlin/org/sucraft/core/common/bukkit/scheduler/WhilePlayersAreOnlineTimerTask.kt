/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.bukkit.scheduler

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin

/**
 * A task that runs at a certain interval in ticks, but only while players are online
 * This task attempts to turn on immediately after creation (in case it is created when players are already online)
 *
 * Note: this task registers as an event listener with Bukkit, so do not keep creating these tasks, because they will all stay registered
 */
open class WhilePlayersAreOnlineTimerTask(plugin: JavaPlugin, run: () -> Any?, interval: Long, firstTimeDelay: Long = interval, minimumDelayAfterTurningOn: Long = 0) : AutoToggledTimerTask(plugin, run, { !Bukkit.getOnlinePlayers().isEmpty() }, interval, firstTimeDelay, minimumDelayAfterTurningOn), Listener {

	// Initialization

	init {
		// Register events
		@Suppress("LeakingThis")
		Bukkit.getPluginManager().registerEvents(this, plugin)
		attemptToTurnOn()
	}

	// Implementation

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onPlayerJoin(@Suppress("UNUSED_PARAMETER") event: PlayerJoinEvent) {
		attemptToTurnOn()
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onPlayerQuit(@Suppress("UNUSED_PARAMETER") event: PlayerQuitEvent) {
		Bukkit.getScheduler().runTaskLater(plugin, ::attemptToTurnOff, 1)
	}

}