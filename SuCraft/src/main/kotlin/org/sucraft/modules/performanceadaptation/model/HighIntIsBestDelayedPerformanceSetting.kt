/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.performanceadaptation.model

import org.sucraft.common.time.TimeInTicks

/**
 * A performance setting with an integer value, where a higher integer denotes a higher quality,
 * that uses the lowest value over an interval of time.
 */
class HighIntIsBestDelayedPerformanceSetting(
	displayName: String,
	getter: () -> Int,
	setter: (Int) -> Unit,
	delay: TimeInTicks,
	defaultHighValue: Int
) : DelayedPerformanceSetting<Int>(
	displayName,
	getter,
	setter,
	Int::compareTo,
	delay,
	defaultHighValue
)