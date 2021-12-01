/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.pingcommand.main

import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.pingcommand.command.SuCraftPingCommandCommands


class SuCraftPingCommandPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftPingCommandPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		SuCraftPingCommandCommands
	}

}