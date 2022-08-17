/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.shorttermtps

import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.scheduler.WhilePlayersAreOnlineTimerTask
import org.sucraft.common.time.TimeInMillis
import org.sucraft.common.time.TimeInSeconds
import org.sucraft.common.time.TimeInTicks

/**
 * Measures the TPS constantly and provides a short-term running indication of the TPS
 * (as opposed to the average indication that Bukkit provides).
 */
object ShortTermTPS : SuCraftModule<ShortTermTPS>() {

	// Settings

	private val measureTPSInterval = TimeInTicks(2)
	private const val keepLastTPSMomentum = 0.99
	private val clampSystemTimeDifferenceBelow = TimeInMillis(40)

	private val ignoreSystemTimeDifferenceAbove = TimeInSeconds(5)

	private const val minRealisticTPS = 0.0001
	private const val maxRealisticTPS = 30.0

	// Data

	private var lastMeasuredSystemTime: Long = 0
	private var lastMeasuredTPS = 20.0

	private val listeners: MutableList<ShortTermTPSListener> = ArrayList(0)

	// Initialization

	override fun onInitialize() {
		super.onInitialize()
		// Schedule measuring the TPS
		WhilePlayersAreOnlineTimerTask(this::measureAndUpdateTPS, measureTPSInterval)
	}

	// Implementation

	private fun measureAndUpdateTPS() {
		if (lastMeasuredSystemTime != 0L) {
			var difference = System.currentTimeMillis() - lastMeasuredSystemTime
			if (difference <= ignoreSystemTimeDifferenceAbove.millis) {
				if (difference < clampSystemTimeDifferenceBelow.millis) {
					difference = clampSystemTimeDifferenceBelow.millis
				}
				val timeForTick = difference.toDouble() / measureTPSInterval.millis
				val tps = 1000 / timeForTick
				if (tps in minRealisticTPS..maxRealisticTPS) {
					lastMeasuredTPS = keepLastTPSMomentum * lastMeasuredTPS + (1 - keepLastTPSMomentum) * tps
					if (lastMeasuredTPS < minRealisticTPS) {
						lastMeasuredTPS = minRealisticTPS
					} else if (lastMeasuredTPS > maxRealisticTPS) {
						lastMeasuredTPS = maxRealisticTPS
					}
					listeners.forEach { it.measuredTPSWasUpdated() }
				}
			}
		}
		lastMeasuredSystemTime = System.currentTimeMillis()
	}

	// Provided functionality

	fun getRecentTPS() = lastMeasuredTPS

	fun registerListener(listener: ShortTermTPSListener) {
		listeners += listener
	}

}