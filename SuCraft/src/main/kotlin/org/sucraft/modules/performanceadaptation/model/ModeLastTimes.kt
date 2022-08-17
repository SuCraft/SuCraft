/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.performanceadaptation.model

import org.sucraft.modules.performanceadaptation.configuration.PerformanceMode
import org.sucraft.modules.performanceadaptation.model.CurrentMode.currentMode
import java.util.*

/**
 * Stores the last time a performance mode was used.
 */
object ModeLastTimes {

	// Data

	/**
	 * For each mode, the last system time in millis we were at this mode.
	 */
	private val modeLastTimes: MutableMap<PerformanceMode, Long> = EnumMap(PerformanceMode::class.java)

	// Implementation

	/**
	 * Returns 0 if this mode has never occurred
	 */
	private fun lastTimeWasAtMode(mode: PerformanceMode) = modeLastTimes[mode] ?: 0L

	// Provided functionality

	fun updateModeLastTime(mode: PerformanceMode = currentMode, time: Long? = null) {
		modeLastTimes[mode] = time ?: System.currentTimeMillis()
	}

	fun lastTimeWasAtModeOrLower(mode: PerformanceMode) =
		mode.orLower().map(::lastTimeWasAtMode).maxOrNull()!!

	fun lastTimeWasAtModeOrHigher(mode: PerformanceMode) =
		mode.orHigher().map(::lastTimeWasAtMode).maxOrNull()!!

}