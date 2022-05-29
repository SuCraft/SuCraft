/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.ridemobs.main

import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.ridemobs.listener.EjectOnQuitListener
import org.sucraft.ridemobs.listener.RightClickPandaListener

class SuCraftRideMobsPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftRideMobsPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		RightClickPandaListener
		EjectOnQuitListener
	}

}