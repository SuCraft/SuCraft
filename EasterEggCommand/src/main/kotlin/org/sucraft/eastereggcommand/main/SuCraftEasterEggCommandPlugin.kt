/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.eastereggcommand.main

import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.eastereggcommand.command.SuCraftEasterEggCommandCommands

class SuCraftEasterEggCommandPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftEasterEggCommandPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		SuCraftEasterEggCommandCommands
	}

}