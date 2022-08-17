/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.time

/**
 * A wrapper for a [value][seconds] that represents an integral number of seconds.
 */
@JvmInline
value class TimeInSeconds(val seconds: Long) : TimeLength {

	override val millis
		get() = seconds * 1000L

	override val secondsExact
		get() = seconds.toDouble()

	override val secondsFloored
		get() = seconds

	override fun asSeconds() = this

	companion object {

		val ZERO = TimeInSeconds(0)
		val ONE = TimeInSeconds(1)

	}

}