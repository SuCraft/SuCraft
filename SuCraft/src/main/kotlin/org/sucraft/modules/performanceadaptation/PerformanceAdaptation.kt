/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.performanceadaptation

import org.sucraft.common.module.SuCraftModule
import org.sucraft.modules.performanceadaptation.model.CurrentMode
import org.sucraft.modules.performanceadaptation.model.PerformanceAdapter
import org.sucraft.modules.shorttermtps.ShortTermTPS

/**
 * Automatically changes the server settings (particularly those that may influence the
 * performance of the server, notably the quality-TPS tradeoff) based on the historical TPS.
 */
object PerformanceAdaptation : SuCraftModule<PerformanceAdaptation>() {

	// Dependencies

	override val dependencies = listOf(
		ShortTermTPS
	)

	// Components

	override val components = listOf(
		CurrentMode,
		PerformanceAdapter
	)

}