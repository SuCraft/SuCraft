/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.customjockeys.main

import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.customjockeys.data.CustomJockeyData
import org.sucraft.customjockeys.data.IncludedCustomJockeys
import org.sucraft.customjockeys.listener.CreatureSpawnListener


class SuCraftCustomJockeysPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftCustomJockeysPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		CustomJockeyData
		CreatureSpawnListener
		IncludedCustomJockeys
	}

}