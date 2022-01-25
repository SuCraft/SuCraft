/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.barriersandlightsdrop.main

import org.sucraft.barriersandlightsdrop.listener.BreakBarrierOrLightListener
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin


class SuCraftBarriersAndLightsDropPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftBarriersAndLightsDropPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		BreakBarrierOrLightListener
	}

}