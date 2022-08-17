/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.time

/**
 * A wrapper for a [value][millis] that represents an integral number of milliseconds.
 */
@JvmInline
value class TimeInMillis(override val millis: Long) : TimeLength {

	override fun asMillis() = this

	companion object {

		val ZERO = TimeInMillis(0)
		val ONE = TimeInMillis(1)

	}

}