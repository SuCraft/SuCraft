/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.uuidcommand.main

import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.uuidcommand.command.SuCraftUUIDCommandCommands

class SuCraftUUIDCommandPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftUUIDCommandPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		SuCraftUUIDCommandCommands
	}

}