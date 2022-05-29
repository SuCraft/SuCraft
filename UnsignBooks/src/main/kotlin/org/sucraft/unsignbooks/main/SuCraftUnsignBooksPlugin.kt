/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.unsignbooks.main

import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.unsignbooks.command.SuCraftUnsignBooksCommands

class SuCraftUnsignBooksPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftUnsignBooksPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		SuCraftUnsignBooksCommands
	}

}