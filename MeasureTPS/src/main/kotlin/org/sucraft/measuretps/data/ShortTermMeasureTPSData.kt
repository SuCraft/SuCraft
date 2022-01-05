/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.measuretps.data

import org.sucraft.core.common.bukkit.scheduler.WhilePlayersAreOnlineTimerTask
import org.sucraft.core.common.sucraft.delegate.measuretps.ShortTermMeasuredTPSListener
import org.sucraft.core.common.sucraft.log.SuCraftLogTexts
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.measuretps.main.SuCraftMeasureTPSPlugin


object ShortTermMeasureTPSData : SuCraftComponent<SuCraftMeasureTPSPlugin>(SuCraftMeasureTPSPlugin.getInstance()) {

	// Settings

	private const val measureTPSIntervalInTicks = 2L
	private const val keepLastTPSMomentum = 0.965
	private const val clampSystemTimeDifferenceBelowInMillis = 40L // 1/25th second

	private const val ignoreSystemTimeDifferenceAboveInMillis = 1000L * 5 // 5 seconds

	private const val minRealisticTPS = 0.0001
	private const val maxRealisticTPS = 30.0

	// Data

	private var lastMeasuredSystemTime: Long = 0
	private var lastMeasuredTPS = 20.0

	private val listeners: MutableList<ShortTermMeasuredTPSListener> = ArrayList()

	// Initialization

	init {
		logger.info(SuCraftLogTexts.schedulingTasks)
		WhilePlayersAreOnlineTimerTask(SuCraftMeasureTPSPlugin.getInstance(), this::measureAndUpdateTPS, measureTPSIntervalInTicks)
	}

	// Implementation

	fun measureAndUpdateTPS() {
		if (lastMeasuredSystemTime != 0L) {
			var difference = System.currentTimeMillis() - lastMeasuredSystemTime
			if (difference <= ignoreSystemTimeDifferenceAboveInMillis) {
				if (difference < clampSystemTimeDifferenceBelowInMillis) {
					difference = clampSystemTimeDifferenceBelowInMillis
				}
				val timeForTick = (difference / measureTPSIntervalInTicks).toDouble()
				val tps = 1000 / timeForTick
				if (tps >= minRealisticTPS && tps <= maxRealisticTPS) {
					lastMeasuredTPS = keepLastTPSMomentum * lastMeasuredTPS + (1 - keepLastTPSMomentum) * tps
					if (lastMeasuredTPS < minRealisticTPS) {
						lastMeasuredTPS = minRealisticTPS
					} else if (lastMeasuredTPS > maxRealisticTPS) {
						lastMeasuredTPS = maxRealisticTPS
					}
					for (listener in listeners) {
						listener.measuredTPSWasUpdated()
					}
				}
			}
		}
		lastMeasuredSystemTime = System.currentTimeMillis()
	}

	fun getRecentTPS() = lastMeasuredTPS

	fun registerListener(listener: ShortTermMeasuredTPSListener) {
		listeners.add(listener)
	}

}