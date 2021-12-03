/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.itemsonhead.main

import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.itemsonhead.command.SuCraftItemsOnHeadCommands


class SuCraftItemsOnHeadPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftItemsOnHeadPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		SuCraftItemsOnHeadCommands
	}

}