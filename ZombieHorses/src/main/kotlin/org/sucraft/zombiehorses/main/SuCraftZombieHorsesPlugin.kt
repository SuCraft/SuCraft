/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.zombiehorses.main

import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.zombiehorses.listener.CustomJockeyRegistrant
import org.sucraft.zombiehorses.listener.ZombieRiderDeathListener


class SuCraftZombieHorsesPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftZombieHorsesPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		ZombieRiderDeathListener
		CustomJockeyRegistrant
	}

}