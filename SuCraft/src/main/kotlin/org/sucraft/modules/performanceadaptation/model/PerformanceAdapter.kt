/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.performanceadaptation.model

import org.bukkit.Bukkit
import org.sucraft.common.module.SuCraftComponent
import org.sucraft.common.time.TimeInSeconds
import org.sucraft.modules.performanceadaptation.PerformanceAdaptation
import org.sucraft.modules.performanceadaptation.configuration.PerformanceMode
import org.sucraft.modules.performanceadaptation.model.CurrentMode.currentMode
import org.sucraft.modules.performanceadaptation.model.CurrentMode.goDownAMode
import org.sucraft.modules.performanceadaptation.model.CurrentMode.goUpAMode
import org.sucraft.modules.performanceadaptation.model.ModeLastTimes.lastTimeWasAtModeOrHigher
import org.sucraft.modules.performanceadaptation.model.ModeLastTimes.lastTimeWasAtModeOrLower
import org.sucraft.modules.performanceadaptation.model.ModeLastTimes.updateModeLastTime
import org.sucraft.modules.shorttermtps.ShortTermTPS
import org.sucraft.modules.shorttermtps.ShortTermTPSListener
import kotlin.math.max
import kotlin.math.min

/**
 * Listens to TPS changes and automatically adapts the current performance mode accordingly.
 */
object PerformanceAdapter : SuCraftComponent<PerformanceAdaptation>(), ShortTermTPSListener {

	// Settings

	private val tpsMinimaTimeIntervals = arrayOf(
		TimeInSeconds(60),
		// Increased to 5 from 8 seconds because the TPS can sometimes
		// be highly unstable over short times under Oracle's OCPU conditions
		TimeInSeconds(8),
	)

	private val maximumTimeAgoToCheck get() = tpsMinimaTimeIntervals[0]
	private val minimumTimeAgoToCheck get() = tpsMinimaTimeIntervals[1]

	fun getTPSToUse() = min(
		max(
			ShortTermTPS.getRecentTPS(), Bukkit.getTPS()[0]
		),
		// Avoid the Bukkit TPS causing the used TPS to lag too much behind when the TPS suddenly drops
		ShortTermTPS.getRecentTPS() + 1.0
	).coerceAtMost(20.0).coerceAtLeast(0.01)

	private val minimumDelayBeforeGoingDownAMode = TimeInSeconds(7)
	private val minimumDelayBeforeGoingUpAMode = TimeInSeconds(9)

	// Data

	/**
	 * The last element in this deque is the most recent,
	 * every other element is [ShortTermTPS.measureTPSInterval] before its successor.
	 */
	private val measurementHistory: ArrayDeque<MeasurementDataPoint> = ArrayDeque()

	// Initialization

	override fun onInitialize() {
		super.onInitialize()
		// Register to listen for TPS changes
		ShortTermTPS.registerListener(this)
	}

	// Implementation

	private fun forMeasurementHistoryTPSAverageOverInterval(
		currentTime: Long, modeFromWhichToDeriveDesiredTPS: PerformanceMode,
		/**
		 * If true: the desired TPS is based on linear interpolation;
		 * if false: the desired TPS is always that of the maximum interval.
		 */
		interpolateToGetDesiredTPS: Boolean, loopBreakingAction: (averageTPS: Double, desiredTPS: Double) -> Boolean
	) {
		val historyIterator = measurementHistory.asReversed().iterator()
		var lastTime = currentTime
		var totalTPSWeightedByMillis = 0.0
		while (historyIterator.hasNext()) {
			val dataPoint = historyIterator.next()
			val timeIntervalOfDataPoint = lastTime - dataPoint.time
			lastTime = dataPoint.time
			if (timeIntervalOfDataPoint > 0) totalTPSWeightedByMillis += dataPoint.tps * timeIntervalOfDataPoint
			val totalTime = currentTime - dataPoint.time
			if (totalTime in minimumTimeAgoToCheck.millis..maximumTimeAgoToCheck.millis) {
				val averageTPS = totalTPSWeightedByMillis / totalTime
				val totalTimeRatioToCheckRange =
					(totalTime.toDouble() - minimumTimeAgoToCheck.millis) /
							(maximumTimeAgoToCheck.millis - minimumTimeAgoToCheck.millis)
				val desiredTPS =
					if (interpolateToGetDesiredTPS) modeFromWhichToDeriveDesiredTPS.tpsMinima.atMinimumInterval +
							totalTimeRatioToCheckRange *
							(modeFromWhichToDeriveDesiredTPS.tpsMinima.atMaximumInterval -
									modeFromWhichToDeriveDesiredTPS.tpsMinima.atMinimumInterval)
					else modeFromWhichToDeriveDesiredTPS.tpsMinima.atMaximumInterval
				if (loopBreakingAction(averageTPS, desiredTPS)) break
			}
		}
	}

	private fun shouldGoDownAMode(currentTime: Long): Boolean {

		// Can't go down if we are at the lowest mode
		if (currentMode == PerformanceMode.lowest) return false

		// Only go down if we have been at the current level or lower for at least a certain amount of time
		if (currentMode != PerformanceMode.highest &&
			lastTimeWasAtModeOrHigher(currentMode.getHigherMode()) >=
			currentTime - minimumDelayBeforeGoingDownAMode.millis
		) return false

		// Only go down if for at least one time ago, between the minimum and maximum interval,
		// the TPS is lower than desired by the current level for the amount of time ago (determined by interpolation)
		var tpsIsTooLow = false
		forMeasurementHistoryTPSAverageOverInterval(
			currentTime,
			currentMode,
			true
		) { averageTPS, desiredTPS ->
			// Taking into account long vs double precision along the way, in favor of staying at this mode
			if (averageTPS < desiredTPS - 1e-2) {
				tpsIsTooLow = true
				true
			} else false
		}
		return tpsIsTooLow

	}

	private fun shouldGoUpAMode(currentTime: Long): Boolean {

		// Can't go up if we are at the highest mode
		if (currentMode == PerformanceMode.highest) return false

		// Only go up if we have been at the current level or higher for at least a certain amount of time
		if (currentMode != PerformanceMode.lowest &&
			lastTimeWasAtModeOrLower(currentMode.getLowerMode()) >=
			currentTime - minimumDelayBeforeGoingUpAMode.millis
		) return false

		// Only go up if for every time ago, between the minimum and maximum interval,
		// the TPS is at least as high as desired by the target (one higher) level for the maximum interval
		var tpsIsHighEnough = false // Default false, since no usable measurements means we do not know whether to go up
		forMeasurementHistoryTPSAverageOverInterval(
			currentTime, currentMode.getHigherMode(), false
		) { averageTPS, desiredTPS ->
			// Taking into account long vs double precision along the way, in favor of going up
			if (averageTPS < desiredTPS - 1e-2) {
				tpsIsHighEnough = false
				true
			} else {
				tpsIsHighEnough = true
				false
			}
		}
		return tpsIsHighEnough

	}

	override fun measuredTPSWasUpdated() {

		// Get the TPS
		val tps = getTPSToUse()

		// Get the current system time
		val currentTime = System.currentTimeMillis()

		// Update the mode last times
		updateModeLastTime(time = currentTime)

		// Add to the measurement history
		measurementHistory.addLast(MeasurementDataPoint(currentTime, currentMode, tps))

		// Clear the front of the measurement history if it is too long ago
		while (measurementHistory.first().time < currentTime - maximumTimeAgoToCheck.millis) measurementHistory.removeFirst()

		// Go down modes if needed
		while (shouldGoDownAMode(currentTime)) {
			goDownAMode()
		}

		// Go up modes if needed
		while (shouldGoUpAMode(currentTime)) {
			goUpAMode()
		}

	}


}