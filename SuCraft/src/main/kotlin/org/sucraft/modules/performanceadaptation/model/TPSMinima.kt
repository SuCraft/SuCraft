/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.performanceadaptation.model

/**
 * A pair of a minimum TPS to maintain at the maximum measurement interval,
 * and a minimum TPS to maintain at the minimum measurement interval.
 */
data class TPSMinima(
	val atMaximumInterval: Double,
	val atMinimumInterval: Double
)