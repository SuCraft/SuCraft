/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.vehicleejectiononquit.main

import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.vehicleejectiononquit.listener.PlayerQuitListener

class SuCraftVehicleEjectionOnQuitPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftVehicleEjectionOnQuitPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		PlayerQuitListener
	}

}