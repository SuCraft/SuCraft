/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.dropminecartsonleave.main

import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.dropminecartsonleave.listener.ExitMinecartListener

class SuCraftDropMinecartsOnLeavePlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftDropMinecartsOnLeavePlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		ExitMinecartListener
	}

}