/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.performanceadaptation.model

import org.sucraft.common.scheduler.runLater
import org.sucraft.common.time.TimeInTicks

/**
 * A performance setting that uses the lowest value over an interval of time.
 */
open class DelayedPerformanceSetting<T>(
	displayName: String,
	getter: () -> T,
	setter: (T) -> Unit,
	private val comparator: Comparator<T>,
	private val delay: TimeInTicks,
	private val defaultHighValue: T
) : PerformanceSetting<T>(
	displayName,
	getter,
	setter
) {

	private val minimumAgo = TimeInTicks(delay.ticks - 1)

	private val lastValueTimestamp: MutableMap<T, Long> = HashMap(0)

	override fun set(value: T) {
		lastValueTimestamp[value] = System.currentTimeMillis()
		updateValue()
		runLater(delay, ::updateValue)
	}

	private fun updateValue() {
		var minValue = defaultHighValue
		val iterator = lastValueTimestamp.iterator()
		while (iterator.hasNext()) {
			val (value, timestamp) = iterator.next()
			if (timestamp < System.currentTimeMillis() - minimumAgo.millis) {
				iterator.remove()
				continue
			}
			if (comparator.compare(value, minValue) < 0) {
				minValue = value
			}
		}
		super.set(minValue)
	}

}