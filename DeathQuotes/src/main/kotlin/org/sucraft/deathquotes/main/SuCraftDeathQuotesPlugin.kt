/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.deathquotes.main

import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.deathquotes.listener.DeathListener


class SuCraftDeathQuotesPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftDeathQuotesPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		DeathListener
	}

}