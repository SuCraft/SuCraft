/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.harmlessentities.main

import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.harmlessentities.delegate.HarmlessEntitiesDelegate
import org.sucraft.harmlessentities.listener.EntityDamageListener


class SuCraftHarmlessEntitiesPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftHarmlessEntitiesPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		HarmlessEntitiesDelegate
		EntityDamageListener
	}

}