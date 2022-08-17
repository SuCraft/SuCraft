/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.time

/**
 * A wrapper for a [value][ticks] that represents an integral number of Minecraft server ticks.
 */
@JvmInline
value class TimeInTicks(val ticks: Long) : TimeLength {

	override val millis
		get() = ticks * 50L

	override val ticksExact
		get() = ticks.toDouble()

	override val ticksFloored
		get() = ticks

	override fun asTicks() = this

	companion object {

		val ZERO = TimeInTicks(0)
		val ONE = TimeInTicks(1)

	}

}