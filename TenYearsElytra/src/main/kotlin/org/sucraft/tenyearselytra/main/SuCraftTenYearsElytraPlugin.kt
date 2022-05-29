/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.tenyearselytra.main

import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.tenyearselytra.command.SuCraftTenYearsElytraCommands
import org.sucraft.tenyearselytra.listener.ElytraBoostListener

class SuCraftTenYearsElytraPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftTenYearsElytraPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		ElytraBoostListener
		SuCraftTenYearsElytraCommands
	}

}