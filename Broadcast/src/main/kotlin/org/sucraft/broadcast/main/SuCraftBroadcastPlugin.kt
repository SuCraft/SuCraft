/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.broadcast.main

import org.sucraft.broadcast.chat.IntervalBroadcaster
import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer


class SuCraftBroadcastPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftBroadcastPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		IntervalBroadcaster
	}

}