/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.mysteryboxes.main

import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.mysteryboxes.command.SuCraftMysteryBoxesCommands
import org.sucraft.mysteryboxes.listener.MysteryBoxFixListener


class SuCraftMysteryBoxesPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftMysteryBoxesPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		MysteryBoxFixListener
		SuCraftMysteryBoxesCommands
	}

}