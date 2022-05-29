/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.antilag.performanceadapter

import org.bukkit.Bukkit
import org.sucraft.antilag.main.SuCraftAntiLagPlugin
import org.sucraft.core.common.sucraft.delegate.measuretps.ShortTermMeasureTPS
import org.sucraft.core.common.sucraft.delegate.measuretps.ShortTermMeasuredTPSListener
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.math.max
import kotlin.math.min

object PerformanceAdapter : SuCraftComponent<SuCraftAntiLagPlugin>(SuCraftAntiLagPlugin.getInstance()), ShortTermMeasuredTPSListener {

	// Settings

	private const val printSettingsAtModeChange = false

	internal val tpsMinimaTimeIntervalsInMillis = arrayOf(
		1000L * 60, // 60 seconds
		1000L * 5 // 5 seconds // Increased to 5 from 1 second because the TPS can sometimes be highly unstable over short times under Oracle's OCPU conditions
	)

	val maximumTimeAgoToCheckInMillis get() = tpsMinimaTimeIntervalsInMillis[0]
	val minimumTimeAgoToCheckInMillis get() = tpsMinimaTimeIntervalsInMillis[1]

	private fun getTPSToUse() =
		max(
			0.01,
			min(
				20.0,
				min(
					max(
						ShortTermMeasureTPS.get().getRecentTPS(),
						Bukkit.getTPS()[0]
					),
					ShortTermMeasureTPS.get().getRecentTPS() + 1.0 // Avoid the Bukkit TPS causing the used TPS to lag too much behind when the suddenly drops
				)
			)
		)
		//min(20.0, maxOf(0.01, ShortTermMeasureTPS.get().getRecentTPS(), Bukkit.getTPS()[0]))
		//ShortTermMeasureTPS.get().getRecentTPS()

	private const val minimumDelayBeforeGoingDownAModeInMillis = 1000L * 4 // 4 seconds
	private const val minimumDelayBeforeGoingUpAModeInMillis = 1000L * 7 // 7 seconds

	// Data

	private var currentMode = PerformanceMode.highest

	/**
	 * The last element in this deque is the most recent, every other element is [ShortTermMeasureTPSData.measureTPSIntervalInTicks] before its successor
	 */
	val measurementHistory: ArrayDeque<MeasurementDataPoint> = ArrayDeque()

	/**
	 * For each mode, the last system time in millis we were at this mode
	 */
	val modeLastTimes: MutableMap<PerformanceMode, Long> = EnumMap(PerformanceMode::class.java)

	// Initialization

	init {
		ShortTermMeasureTPS.get().registerListener(this)
	}

	// Implementation

	fun forceApplyCurrentModeSettingValues() = currentMode.applyAllSettingValues()

	fun getMode() = currentMode

	fun setMode(newMode: PerformanceMode) {
		if (newMode == currentMode) return
		val oldMode = currentMode
		currentMode = newMode
		currentMode.applyDifferingSettingValues(oldMode)
		modeLastTimes[currentMode] = System.currentTimeMillis()
		// Log to console
		logger.info("The performance mode has been ${if (currentMode > oldMode) "decreased" else "increased"} to ${currentMode.name} ('${currentMode.displayName}') with current TPS ${getTPSToUse()}")
		if (printSettingsAtModeChange) {
			logger.info("The settings are now:")
			PerformanceSetting.values.forEach { logger.info("* ${it.displayName} = ${it.get()}") }
		}
	}

	/**
	 * Returns 0 if this mode has never occurred
	 */
	private fun lastTimeWasAtMode(mode: PerformanceMode) = modeLastTimes[mode] ?: 0L

	private fun lastTimeWasAtModeOrLower(mode: PerformanceMode) =
		mode.orLower().map(this::lastTimeWasAtMode).maxOrNull()!!

	private fun lastTimeWasAtModeOrHigher(mode: PerformanceMode) =
		mode.orHigher().map(this::lastTimeWasAtMode).maxOrNull()!!

	private fun forMeasurementHistoryTPSAverageOverInterval(
		currentTime: Long,
		modeFromWhichToDeriveDesiredTPS: PerformanceMode,
		/**
		 * If true: the desired TPS is based on linear interpolation
		 * If false: the desired TPS is always that of the maximum interval
		 */
		interpolateToGetDesiredTPS: Boolean,
		loopBreakingAction: (averageTPS: Double, desiredTPS: Double) -> Boolean
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
			if (totalTime in minimumTimeAgoToCheckInMillis..maximumTimeAgoToCheckInMillis) {
				val averageTPS = totalTPSWeightedByMillis / totalTime
				val totalTimeRatioToCheckRange = (totalTime.toDouble() - minimumTimeAgoToCheckInMillis) / (maximumTimeAgoToCheckInMillis - minimumTimeAgoToCheckInMillis)
				val desiredTPS =
					if (interpolateToGetDesiredTPS)
						modeFromWhichToDeriveDesiredTPS.tpsMinima.atMinimumInterval + totalTimeRatioToCheckRange * (modeFromWhichToDeriveDesiredTPS.tpsMinima.atMaximumInterval - modeFromWhichToDeriveDesiredTPS.tpsMinima.atMinimumInterval)
					else
						modeFromWhichToDeriveDesiredTPS.tpsMinima.atMaximumInterval
				if (loopBreakingAction(averageTPS, desiredTPS)) break
			}
		}
	}

	override fun measuredTPSWasUpdated() {

		// Get the TPS
		val tps = getTPSToUse()

		// Get the current system time
		val currentTime = System.currentTimeMillis()

		// Update the mode last times
		modeLastTimes[currentMode] = currentTime

		// Add to the measurement history
		measurementHistory.addLast(MeasurementDataPoint(currentTime, currentMode, tps))

		// Clear the front of the measurement history if it is too long ago
		while (measurementHistory.first().time < currentTime - maximumTimeAgoToCheckInMillis)
			measurementHistory.removeFirst()

		// Go down modes if needed
		while (shouldGoDownAMode(currentTime)) {
			goDownAMode()
		}

		// Go up modes if needed
		while (shouldGoUpAMode(currentTime)) {
			goUpAMode()
		}

	}

	private fun shouldGoDownAMode(currentTime: Long): Boolean {

		// Can't go down if we are at the lowest mode
		if (currentMode == PerformanceMode.lowest) return false

		// Only go down if we have been at the current level or lower for at least a certain amount of time
		if (currentMode != PerformanceMode.highest && lastTimeWasAtModeOrHigher(currentMode.getHigherMode()) >= currentTime - minimumDelayBeforeGoingDownAModeInMillis) return false

		// Only go down if for at least one time ago, between the minimum and maximum interval, the TPS is lower than desired by the current level for the amount of time ago (determined by interpolation)
		var tpsIsTooLow = false
		forMeasurementHistoryTPSAverageOverInterval(currentTime, currentMode, true) { averageTPS, desiredTPS ->
			if (averageTPS < desiredTPS - 1e-2) { // Taking into account long vs double precision along the way, in favor of staying at this mdoe
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
		if (currentMode != PerformanceMode.lowest && lastTimeWasAtModeOrLower(currentMode.getLowerMode()) >= currentTime - minimumDelayBeforeGoingUpAModeInMillis) return false

		// Only go up if for every time ago, between the minimum and maximum interval, the TPS is at least as high as desired by the target (one higher) level for the maximum interval
		var tpsIsHighEnough = false // Default false since no usable measurements means we do not know whether to go up
		forMeasurementHistoryTPSAverageOverInterval(currentTime, currentMode.getHigherMode(), false) { averageTPS, desiredTPS ->
			if (averageTPS < desiredTPS - 1e-2) { // Taking into account long vs double precision along the way, in favor of going up
				tpsIsHighEnough = false
				true
			} else {
				tpsIsHighEnough = true
				false
			}
		}
		return tpsIsHighEnough

	}

	fun goDownAMode() {
		setMode(currentMode.getLowerMode())
	}

	fun goUpAMode() {
		setMode(currentMode.getHigherMode())
	}

}