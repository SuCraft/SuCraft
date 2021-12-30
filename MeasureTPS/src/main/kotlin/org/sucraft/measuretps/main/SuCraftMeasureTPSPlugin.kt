/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.measuretps.main

import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.measuretps.data.ShortTermMeasureTPSData
import org.sucraft.measuretps.delegate.ShortTermMeasureTPSDelegate


class SuCraftMeasureTPSPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftMeasureTPSPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		ShortTermMeasureTPSData
		ShortTermMeasureTPSDelegate
	}

}