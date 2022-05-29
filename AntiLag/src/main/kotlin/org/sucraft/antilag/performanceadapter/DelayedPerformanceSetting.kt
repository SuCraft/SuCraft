/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.antilag.performanceadapter

import org.bukkit.Bukkit
import org.sucraft.antilag.main.SuCraftAntiLagPlugin
import org.sucraft.core.common.bukkit.time.TickTime

/**
 * Uses the lowest value over an interval of time
 */
open class DelayedPerformanceSetting<T>(
	displayName: String,
	getter: () -> T,
	setter: (T) -> Unit,
	private val comparator: Comparator<T>,
	private val delayInTicks: Long,
	private val defaultHighValue: T
) : PerformanceSetting<T>(
	displayName,
	getter,
	setter
) {

	private val minimumAgoInMillis = TickTime.ticksToMillis(delayInTicks - 1)

	private val lastValueTimestamp: MutableMap<T, Long> = HashMap(0)

	override fun set(value: T) {
		lastValueTimestamp[value] = System.currentTimeMillis()
		updateValue()
		Bukkit.getScheduler().runTaskLater(SuCraftAntiLagPlugin.getInstance(), ::updateValue, delayInTicks)
	}

	private fun updateValue() {
		var minValue = defaultHighValue
		val iterator = lastValueTimestamp.iterator()
		while (iterator.hasNext()) {
			val (value, timestamp) = iterator.next()
			if (timestamp < System.currentTimeMillis() - minimumAgoInMillis) {
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