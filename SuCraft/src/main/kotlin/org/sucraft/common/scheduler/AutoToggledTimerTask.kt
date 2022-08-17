/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.common.scheduler

import org.bukkit.scheduler.BukkitTask
import org.sucraft.common.time.TimeInMillis
import org.sucraft.common.time.TimeLength

/**
 * A task that runs at a certain interval in ticks, but can be temporarily turned off if a certain condition fails.
 *
 * @param run The task to be executed.
 * @param condition The condition for this task to run.
 */
@Suppress("MemberVisibilityCanBePrivate")
open class AutoToggledTimerTask(
	val run: () -> Any?,
	val condition: () -> Boolean,
	val interval: TimeLength,
	val firstTimeDelay: TimeLength = interval,
	val minimumDelayAfterTurningOn: TimeLength = TimeLength.ZERO
) {

	val turnedOn get() = bukkitTask != null

	/**
	 * The system time at which this task was attempted to be turned on the first time, or null if not attempted yet.
	 */
	var firstRunAttemptInMillis: Long? = null
		private set

	/**
	 * The system time when the task was last run, or null if not run before.
	 */
	var lastRunInMillis: Long? = null
		private set

	/**
	 * The current Bukkit task that is scheduled, or null if there is none.
	 */
	private var bukkitTask: BukkitTask? = null

	/**
	 * Run the task to be executed, and save the time this happened.
	 */
	private fun doRun() {
		lastRunInMillis = System.currentTimeMillis()
		run()
	}

	/**
	 * Attempt to turn this task on, if it is not turned on already, and if the condition to run is true.
	 * @return Whether this task was changed from turned off to turned on.
	 */
	fun attemptToTurnOn(): Boolean {
		if (turnedOn) return false
		if (firstRunAttemptInMillis == null) firstRunAttemptInMillis = System.currentTimeMillis()
		if (!condition()) return false
		val intervalUntilNextRun =
			TimeInMillis(
				if (lastRunInMillis == null) {
					firstRunAttemptInMillis!! + firstTimeDelay.millis - System.currentTimeMillis()
				} else {
					lastRunInMillis!! + interval.millis - System.currentTimeMillis()
				}
			).coerceAtLeast(minimumDelayAfterTurningOn).asTicksRounded()
		if (intervalUntilNextRun.ticks == 0L) doRun()
		bukkitTask = runTimer(
			if (intervalUntilNextRun.ticks == 0L) interval.asTicksRounded() else intervalUntilNextRun,
			interval.asTicksRounded(),
			::doIntervalCheckAndRun
		)
		return true
	}

	/**
	 * Turns this task off until [attemptToTurnOn] is called again.
	 */
	fun turnOff() {
		if (!turnedOn) return
		bukkitTask!!.cancel()
		bukkitTask = null
	}

	/**
	 * Attempt to turn this task off, if it is not turned off already, and if the condition to run is false.
	 * @return Whether this task was changed from turned on to turned off.
	 */
	fun attemptToTurnOff(): Boolean {
		if (!turnedOn) return false
		if (condition()) return false
		turnOff()
		return true
	}

	private fun doIntervalCheckAndRun() {
		if (attemptToTurnOff()) return
		doRun()
	}

}