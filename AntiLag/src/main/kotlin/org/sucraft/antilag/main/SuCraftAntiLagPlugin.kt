/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.antilag.main

import org.sucraft.antilag.performanceadapter.PerformanceAdapter
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin

class SuCraftAntiLagPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftAntiLagPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		PerformanceAdapter
		// Force-apply the settings of the initial mode
		PerformanceAdapter.forceApplyCurrentModeSettingValues()
	}

}