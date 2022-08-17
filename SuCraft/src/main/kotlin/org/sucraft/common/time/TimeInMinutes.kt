/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.time

/**
 * A wrapper for a [value][minutes] that represents an integral number of minutes.
 */
@JvmInline
value class TimeInMinutes(val minutes: Long) : TimeLength {

	override val millis
		get() = minutes * 60000L

	override val minutesExact
		get() = minutes.toDouble()

	override val minutesFloored
		get() = minutes

	override fun asMinutes() = this

	companion object {

		val ZERO = TimeInMinutes(0)
		val ONE = TimeInMinutes(1)

	}

}