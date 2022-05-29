/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.anvilmechanics.main

import org.sucraft.anvilmechanics.listener.AnvilListener
import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer

class SuCraftAnvilMechanicsPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftAnvilMechanicsPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		AnvilListener
	}

}