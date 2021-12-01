/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.keepglass.main

import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.keepglass.listener.BreakGlassListener


class SuCraftKeepGlassPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftKeepGlassPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		BreakGlassListener
	}

}