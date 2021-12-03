/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.bukkit.time


@Suppress("MemberVisibilityCanBePrivate")
object TickTime {

	const val ticksPerSecond = 20
	const val millisPerTick = 50

	init {
		// Make sure the values entered are matching and precise
		if (ticksPerSecond * millisPerTick != 1000) throw IllegalStateException("Ticks per second and millis per tick are inconsistent")
	}

	fun ticksToMillis(ticks: Double): Double = ticks * millisPerTick

	fun ticksToMillis(ticks: Long): Long = ticks * millisPerTick

	fun millisToTicks(millis: Double): Double = millis / millisPerTick

	fun millisToTicks(millis: Long): Double = millisToTicks(millis.toDouble())

	fun ticksToSeconds(ticks: Double): Double = ticks / ticksPerSecond

	fun ticksToSeconds(ticks: Long): Double = ticksToSeconds(ticks.toDouble())

	fun secondsToTicks(seconds: Double): Double = seconds * ticksPerSecond

	fun secondsToTicks(seconds: Long): Long = seconds * ticksPerSecond

}