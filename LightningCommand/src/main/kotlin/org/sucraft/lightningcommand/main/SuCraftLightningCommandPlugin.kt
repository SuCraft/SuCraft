/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.lightningcommand.main

import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.lightningcommand.command.SuCraftLightningCommandCommands

class SuCraftLightningCommandPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftLightningCommandPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		SuCraftLightningCommandCommands
	}

}