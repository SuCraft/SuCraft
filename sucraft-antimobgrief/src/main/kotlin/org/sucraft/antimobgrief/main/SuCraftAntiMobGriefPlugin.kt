/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.antimobgrief.main

import org.sucraft.antimobgrief.listener.ExplosionListener
import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer


class SuCraftAntiMobGriefPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftAntiMobGriefPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		ExplosionListener
	}

}