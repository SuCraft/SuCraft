/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.portabletoolblocks.main

import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.portabletoolblocks.command.SuCraftPortableToolBlocksCommands

class SuCraftPortableToolBlocksPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftPortableToolBlocksPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		SuCraftPortableToolBlocksCommands
	}

}