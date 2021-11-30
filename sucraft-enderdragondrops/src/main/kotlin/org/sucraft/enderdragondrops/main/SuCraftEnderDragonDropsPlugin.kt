/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.enderdragondrops.main

import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.enderdragondrops.listener.EnderDragonDeathListener

class SuCraftEnderDragonDropsPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftEnderDragonDropsPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		EnderDragonDeathListener
	}

}