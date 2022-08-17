/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.time

/**
 * A wrapper for a [value][hours] that represents an integral number of hours.
 */
@JvmInline
value class TimeInHours(val hours: Long) : TimeLength {

	override val millis
		get() = hours * 3600000L

	override val hoursExact
		get() = hours.toDouble()

	override val hoursFloored
		get() = hours

	override fun asHours() = this

	companion object {

		val ZERO = TimeInHours(0)
		val ONE = TimeInHours(1)

	}

}