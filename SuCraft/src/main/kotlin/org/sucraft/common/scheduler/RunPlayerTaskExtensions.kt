/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.scheduler

import org.bukkit.entity.Player
import org.sucraft.common.time.TimeInSeconds
import org.sucraft.common.time.TimeInTicks

private val maxRunLaterForPlayerWhileKeepingReferenceDelay = TimeInSeconds(10).asTicks()

/**
 * Runs a task for the player after the given delay, but only if the player is online
 * at that time. If the player leaves the server and re-joins in the meantime,
 * the action will still be executed.
 *
 * This will potentially keep a reference to the [Player] instance.
 * @throws IllegalArgumentException If the given [delay] is larger than
 * [maxRunLaterForPlayerWhileKeepingReferenceDelay], because a reference to the Player instance is potentially kept,
 * preventing the server from unloading some of the player's data in memory,
 * so we do not allow using this method for large delays.
 */
@Throws(IllegalArgumentException::class)
fun Player.runLater(delay: TimeInTicks, action: Player.() -> Unit) =
	if (delay > maxRunLaterForPlayerWhileKeepingReferenceDelay)
		throw IllegalArgumentException(
			"Called Player.runLater with a delay ($delay) that is larger than the" +
					"maximum allowed ($maxRunLaterForPlayerWhileKeepingReferenceDelay)"
		)
	else
		runLater(this, delay, action)

/**
 * Calls [Player.runLater] with a delay of 1 tick.
 */
fun Player.runNextTick(action: Player.() -> Unit) =
	runLater(TimeInTicks.ONE, action)

/**
 * Similar to [Player.runLater], but will be called with a null [Player] instance if the player is no longer online.
 */
@Throws(IllegalArgumentException::class)
fun Player.runLaterEvenIfOffline(delay: TimeInTicks, action: Player?.() -> Unit) =
	if (delay > maxRunLaterForPlayerWhileKeepingReferenceDelay)
		throw IllegalArgumentException(
			"Called Player.runLaterEvenIfOffline with a delay ($delay) that is larger than the" +
					"maximum allowed ($maxRunLaterForPlayerWhileKeepingReferenceDelay)"
		)
	else
		runLaterEvenIfOffline(this, delay, action)

/**
 * Similar to [Player.runNextTick], but will be called with a null [Player] instance if the player is no longer online.
 */
fun Player.runNextTickEvenIfOffline(action: Player?.() -> Unit) =
	runLaterEvenIfOffline(TimeInTicks.ONE, action)