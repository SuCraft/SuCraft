/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.raiddifficulty.main

import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.raiddifficulty.raid.RaidStartListener

class SuCraftRaidDifficultyPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftRaidDifficultyPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		RaidStartListener
	}

}