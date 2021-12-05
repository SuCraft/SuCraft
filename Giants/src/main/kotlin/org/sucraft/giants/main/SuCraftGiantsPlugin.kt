/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.giants.main

import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.giants.data.GiantData
import org.sucraft.giants.listener.ZombieSpawnListener


class SuCraftGiantsPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftGiantsPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		GiantData
		ZombieSpawnListener
	}

}