package org.sucraft.keepglass.main

import lombok.Getter
import org.sucraft.core.common.plugin.SuCraftPlugin
import org.sucraft.keepglass.main.SuCraftKeepGlassPlugin
import org.sucraft.keepglass.listener.GlassBreakListener
import org.sucraft.core.common.plugin.PluginUtils
import org.sucraft.core.main.SuCraftCorePlugin

class SuCraftKeepGlassPlugin : SuCraftPlugin() {

	// Singleton

	companion object {
		private var instance: SuCraftKeepGlassPlugin? = null
//		fun getInstance() = instance!!
		fun isInitialized() = instance != null
		fun getInstance() = if (isInitialized()) instance!! else throw IllegalStateException("Plugin has not been initialized yet")
	}

	// Initialization

	public override fun onPluginPreEnable() {
		instance = this
	}

	// Enable

	public override fun onPluginEnable() {
		// Initialize components
		GlassBreakListener
	}

}