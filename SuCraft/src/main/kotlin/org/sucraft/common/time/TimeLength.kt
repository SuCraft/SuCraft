/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.time

import kotlin.math.round

/**
 * A common interface to classes that represent a time length.
 *
 * The smallest atomic unit of time is a millisecond.
 */
interface TimeLength : Comparable<TimeLength> {

	/**
	 * The length of this time, in milliseconds.
	 */
	val millis: Long

	/**
	 * The length of this time, in Minecraft server ticks.
	 */
	val ticksExact: Double
		get() = millis / 50.0

	/**
	 * The length of this time, in Minecraft server ticks, floored.
	 */
	val ticksFloored
		get() = millis / 50L

	/**
	 * The length of this time, in Minecraft server ticks, rounded.
	 */
	val ticksRounded
		get() = round(ticksExact).toLong()

	/**
	 * The length of this time, in seconds.
	 */
	val secondsExact: Double
		get() = millis / 1000.0

	/**
	 * The length of this time, in seconds, floored.
	 */
	val secondsFloored
		get() = millis / 1000L

	/**
	 * The length of this time, in seconds, rounded.
	 */
	val secondsRounded
		get() = round(secondsExact).toLong()

	/**
	 * The length of this time, in minutes.
	 */
	val minutesExact: Double
		get() = millis / 60000.0

	/**
	 * The length of this time, in minutes, floored.
	 */
	val minutesFloored
		get() = millis / 60000L

	/**
	 * The length of this time, in minutes, rounded.
	 */
	val minutesRounded
		get() = round(minutesExact).toLong()

	/**
	 * The length of this time, in hours.
	 */
	val hoursExact: Double
		get() = millis / 3600000.0

	/**
	 * The length of this time, in hours, floored.
	 */
	val hoursFloored
		get() = millis / 3600000L

	/**
	 * The length of this time, in hours, rounded.
	 */
	val hoursRounded
		get() = round(hoursExact).toLong()

	/**
	 * The length of this time, in days.
	 */
	val daysExact: Double
		get() = millis / 86400000.0

	/**
	 * The length of this time, in days, floored.
	 */
	val daysFloored
		get() = millis / 86400000L

	/**
	 * The length of this time, in days, rounded.
	 */
	val daysRounded
		get() = round(daysExact).toLong()

	/**
	 * The length of this time, in weeks.
	 */
	val weeksExact: Double
		get() = millis / 604800000.0

	/**
	 * The length of this time, in weeks, floored.
	 */
	val weeksFloored
		get() = millis / 604800000L

	/**
	 * The length of this time, in weeks, rounded.
	 */
	val weeksRounded
		get() = round(weeksExact).toLong()

	/**
	 * This time, backed by milliseconds.
	 */
	fun asMillis() = TimeInMillis(millis)

	/**
	 * This time, backed by Minecraft server ticks, floored.
	 */
	fun asTicks() = TimeInTicks(ticksFloored)

	/**
	 * This time, backed by seconds, floored.
	 */
	fun asSeconds() = TimeInSeconds(secondsFloored)

	/**
	 * This time, backed by minutes, floored.
	 */
	fun asMinutes() = TimeInMinutes(minutesFloored)

	/**
	 * This time, backed by hours, floored.
	 */
	fun asHours() = TimeInHours(hoursFloored)

	/**
	 * This time, backed by days, floored.
	 */
	fun asDays() = TimeInDays(daysFloored)

	/**
	 * This time, backed by weeks, floored.
	 */
	fun asWeeks() = TimeInWeeks(weeksFloored)

	/**
	 * This time, backed by Minecraft server ticks, rounded.
	 */
	fun asTicksRounded() = TimeInTicks(ticksRounded)

	/**
	 * This time, backed by seconds, rounded.
	 */
	fun asSecondsRounded() = TimeInSeconds(secondsRounded)

	/**
	 * This time, backed by minutes, rounded.
	 */
	fun asMinutesRounded() = TimeInMinutes(minutesRounded)

	/**
	 * This time, backed by hours, rounded.
	 */
	fun asHoursRounded() = TimeInHours(hoursRounded)

	/**
	 * This time, backed by days, rounded.
	 */
	fun asDaysRounded() = TimeInDays(daysRounded)

	/**
	 * This time, backed by weeks, rounded.
	 */
	fun asWeeksRounded() = TimeInWeeks(weeksRounded)

	infix operator fun plus(other: TimeLength): TimeLength =
		TimeInMillis(millis + other.millis)

	override fun compareTo(other: TimeLength) =
		millis.compareTo(other.millis)

	companion object {

		val ZERO: TimeLength = TimeInMillis.ZERO

	}

}