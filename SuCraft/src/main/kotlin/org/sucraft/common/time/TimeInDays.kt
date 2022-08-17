/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.time

/**
 * A wrapper for a [value][days] that represents an integral number of days.
 */
@JvmInline
value class TimeInDays(val days: Long) : TimeLength {

	override val millis
		get() = days * 86400000L

	override val daysExact
		get() = days.toDouble()

	override val daysFloored
		get() = days

	override fun asDays() = this

	companion object {

		val ZERO = TimeInDays(0)
		val ONE = TimeInDays(1)

	}

}