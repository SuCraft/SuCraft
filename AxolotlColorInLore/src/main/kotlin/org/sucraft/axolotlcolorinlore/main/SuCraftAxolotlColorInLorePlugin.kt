/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.axolotlcolorinlore.main

import org.sucraft.axolotlcolorinlore.listener.ScoopAxolotlListener
import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer

class SuCraftAxolotlColorInLorePlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftAxolotlColorInLorePlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		ScoopAxolotlListener
	}

}