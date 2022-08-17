/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.time

/**
 * A wrapper for a [value][weeks] that represents an integral number of weeks.
 */
@JvmInline
value class TimeInWeeks(val weeks: Long) : TimeLength {

	override val millis
		get() = weeks * 604800000L

	override val weeksExact
		get() = weeks.toDouble()

	override val weeksFloored
		get() = weeks

	override fun asWeeks() = this

	companion object {

		val ZERO = TimeInWeeks(0)
		val ONE = TimeInWeeks(1)

	}

}