/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.main

import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.core.defaultdelegate.MinecraftClientLocaleByIncludedResource
import org.sucraft.core.defaultdelegate.OfflinePlayersInformationByBukkitOfflinePlayerCall
import org.sucraft.core.defaultdelegate.StandardItemStackNamesByClientLocale


class SuCraftCorePlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftCorePlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		MinecraftClientLocaleByIncludedResource
		OfflinePlayersInformationByBukkitOfflinePlayerCall
		StandardItemStackNamesByClientLocale
	}

}