/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.bukkitapiextension.main

import org.sucraft.bukkitapiextension.event.playerscoopentity.PlayerScoopEntityEventDispatcher
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin


class SuCraftBukkitAPIExtensionPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftBukkitAPIExtensionPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize event dispatchers
		PlayerScoopEntityEventDispatcher
	}

}