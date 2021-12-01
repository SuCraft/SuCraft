/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.opmecommand.main

import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.opmecommand.command.SuCraftOpMeCommandCommands


class SuCraftOpMeCommandPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftOpMeCommandPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		SuCraftOpMeCommandCommands
	}

}