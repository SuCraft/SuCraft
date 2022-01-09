/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.homequotes.main

import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.homequotes.listener.TeleportToHomeListener


class SuCraftHomeQuotesPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftHomeQuotesPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		TeleportToHomeListener
	}

}