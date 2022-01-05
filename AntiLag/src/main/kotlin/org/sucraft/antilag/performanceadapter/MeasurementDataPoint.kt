/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.antilag.performanceadapter


class MeasurementDataPoint(
	/**
	 * System time in millis
	 */
	val time: Long,
	val mode: PerformanceMode,
	val tps: Double
)