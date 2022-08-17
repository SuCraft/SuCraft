/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.scheduler

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask
import org.sucraft.common.player.getOnlinePlayer
import org.sucraft.common.time.TimeInTicks
import org.sucraft.main.SuCraftPlugin

fun runLater(delay: TimeInTicks, action: () -> Unit) =
	runLater(delay.ticks, action)

fun runNextTick(action: () -> Unit) =
	runLater(1, action)

private fun runLater(delayInTicks: Long, action: () -> Unit) =
	Bukkit.getScheduler().runTaskLater(SuCraftPlugin.instance, Runnable {
		action()
	}, delayInTicks)

/**
 * Runs a task for the player after the given delay, but only if the player is online
 * at that time. If the player leaves the server and re-joins in the meantime,
 * the action will still be executed.
 *
 * This will not keep a reference to the [Player] instance.
 */
fun runLater(player: Player, delay: TimeInTicks, action: Player.() -> Unit) =
	runLaterEvenIfOffline(player, delay) { this?.action() }

/**
 * Calls [runLater] with a delay of 1 tick.
 */
fun runNextTick(player: Player, action: Player.() -> Unit) =
	runLater(player, TimeInTicks.ONE, action)

/**
 * Similar to [runLater], but will be called with a null [Player] instance if the player is no longer online.
 */
fun runLaterEvenIfOffline(player: Player, delay: TimeInTicks, action: Player?.() -> Unit): BukkitTask {
	// Save the UUID
	val uuid = player.uniqueId
	// Schedule the task
	return runLater(delay) {
		// Execute the action on a newly retrieved Player instance (or null if the player is not online)
		uuid.getOnlinePlayer().action()
	}
}

/**
 * Similar to [runNextTick], but will be called with a null [Player] instance if the player is no longer online.
 */
fun runNextTickEvenIfOffline(player: Player, action: Player?.() -> Unit) =
	runLaterEvenIfOffline(player, TimeInTicks.ONE, action)

fun runTimer(initialDelayAndInterval: TimeInTicks, action: () -> Unit) =
	runTimer(initialDelayAndInterval.ticks, action)

fun runTimer(initialDelay: TimeInTicks, interval: TimeInTicks, action: () -> Unit) =
	runTimer(initialDelay.ticks, interval.ticks, action)

private fun runTimer(initialDelayAndIntervalInTicks: Long, action: () -> Unit) =
	runTimer(initialDelayAndIntervalInTicks, initialDelayAndIntervalInTicks, action)

private fun runTimer(initialDelayInTicks: Long, intervalInTicks: Long, action: () -> Unit) =
	Bukkit.getScheduler().runTaskTimer(SuCraftPlugin.instance, Runnable {
		action()
	}, initialDelayInTicks, intervalInTicks)