/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.easydifficultyspiderpoison.main

import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.easydifficultyspiderpoison.listener.SpiderDamageListener

class SuCraftEasyDifficultySpiderPoisonPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftEasyDifficultySpiderPoisonPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		SpiderDamageListener
	}

}