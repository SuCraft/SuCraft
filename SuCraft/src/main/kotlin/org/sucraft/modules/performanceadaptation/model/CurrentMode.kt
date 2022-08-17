/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.performanceadaptation.model

import org.sucraft.common.function.runEach
import org.sucraft.common.module.SuCraftComponent
import org.sucraft.modules.performanceadaptation.PerformanceAdaptation
import org.sucraft.modules.performanceadaptation.configuration.PerformanceMode
import org.sucraft.modules.performanceadaptation.configuration.performanceSettings
import org.sucraft.modules.performanceadaptation.model.ModeLastTimes.updateModeLastTime
import org.sucraft.modules.performanceadaptation.model.PerformanceAdapter.getTPSToUse

/**
 * Stores the current performance mode of the server, and provides functionality for changing it.
 */
object CurrentMode : SuCraftComponent<PerformanceAdaptation>() {

	// Settings

	private const val printSettingsAtModeChange = false

	// Data

	var currentMode = PerformanceMode.highest
		private set

	// Provided functionality

	fun forceApplyCurrentModeSettingValues() = currentMode.applyAllSettingValues()

	fun setMode(newMode: PerformanceMode) {
		if (newMode == currentMode) return
		val oldMode = currentMode
		currentMode = newMode
		currentMode.applyDifferingSettingValues(oldMode)
		updateModeLastTime()
		// Log to console
		info(
			"The performance mode has been ${if (currentMode > oldMode) "decreased" else "increased"} " +
					"to ${currentMode.name} ('${currentMode.displayName}') with current TPS ${getTPSToUse()}"
		)
		if (printSettingsAtModeChange) {
			info("The settings are now:")
			performanceSettings.runEach { info("* $displayName = ${get()}") }
		}
	}

	fun goDownAMode() {
		setMode(currentMode.getLowerMode())
	}

	fun goUpAMode() {
		setMode(currentMode.getHigherMode())
	}

}