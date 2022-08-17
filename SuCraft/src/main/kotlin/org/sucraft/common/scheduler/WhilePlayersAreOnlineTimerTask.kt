/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.common.scheduler

import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.sucraft.common.event.on
import org.sucraft.common.event.registerEvents
import org.sucraft.common.time.TimeLength

/**
 * A task that runs at a certain interval in ticks, but only while players are online
 * This task attempts to turn on immediately after creation (in case it is created when players are already online)
 *
 * Note: this task registers as an event listener with Bukkit, so do not keep creating these tasks, because they will all stay registered
 */
open class WhilePlayersAreOnlineTimerTask(
	run: () -> Any?,
	interval: TimeLength,
	firstTimeDelay: TimeLength = interval,
	minimumDelayAfterTurningOn: TimeLength = TimeLength.ZERO
) : AutoToggledTimerTask(
	run,
	{ !Bukkit.getOnlinePlayers().isEmpty() },
	interval,
	firstTimeDelay,
	minimumDelayAfterTurningOn
), Listener {

	// Initialization

	init {
		// Register events
		@Suppress("LeakingThis")
		registerEvents()
		attemptToTurnOn()
	}

	// Events

	init {
		// Listen for player joins to turn on the scheduling
		on(PlayerJoinEvent::class) {
			attemptToTurnOn()
		}
		// Listen for player quits to turn off the scheduling
		on(PlayerQuitEvent::class) {
			runNextTick(::attemptToTurnOff)
		}
	}

}