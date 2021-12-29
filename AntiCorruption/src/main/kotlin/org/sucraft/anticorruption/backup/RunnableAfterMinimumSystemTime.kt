/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.anticorruption.backup


internal data class RunnableAfterMinimumSystemTime(
	val runnable: Runnable,
	val minimumSystemTime: Long
) : Comparable<RunnableAfterMinimumSystemTime> {

	override fun compareTo(other: RunnableAfterMinimumSystemTime) =
		minimumSystemTime.compareTo(other.minimumSystemTime)

	fun isDue() =
		System.currentTimeMillis() >= minimumSystemTime

	fun run() =
		runnable.run()

	companion object {

		fun after(millis: Long, runnable: Runnable) =
			RunnableAfterMinimumSystemTime(runnable, System.currentTimeMillis() + millis)

		fun anytime(runnable: Runnable) =
			after(0, runnable)

	}

}