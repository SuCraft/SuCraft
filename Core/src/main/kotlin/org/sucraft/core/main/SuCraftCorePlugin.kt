/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.main

import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.core.defaultdelegate.OfflinePlayersInformationByBukkitOfflinePlayerCall


class SuCraftCorePlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftCorePlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		OfflinePlayersInformationByBukkitOfflinePlayerCall
	}

}