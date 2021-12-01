/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.playercompass.main

import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.playercompass.command.SuCraftPlayerCompassCommands
import org.sucraft.playercompass.tracker.PlayerCompassTracker


class SuCraftPlayerCompassPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftPlayerCompassPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		PlayerCompassTracker
		SuCraftPlayerCompassCommands
	}

}