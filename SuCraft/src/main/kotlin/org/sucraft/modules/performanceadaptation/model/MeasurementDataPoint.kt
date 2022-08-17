/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.performanceadaptation.model

import org.sucraft.modules.performanceadaptation.configuration.PerformanceMode

data class MeasurementDataPoint(
	/**
	 * System time in millis
	 */
	val time: Long,
	val mode: PerformanceMode,
	val tps: Double
)