/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.antilag.performanceadapter


class HighIntIsBestDelayedPerformanceSetting(
	displayName: String,
	getter: () -> Int,
	setter: (Int) -> Unit,
	delayInTicks: Long,
	defaultHighValue: Int
) : DelayedPerformanceSetting<Int>(
	displayName,
	getter,
	setter,
	Int::compareTo,
	delayInTicks,
	defaultHighValue
)