/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.invisibleitemframes.main

import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.invisibleitemframes.listener.MakeItemFrameVisibleOnDamageListener
import org.sucraft.invisibleitemframes.listener.RightClickItemFrameListener


class SuCraftInvisibleItemFramesPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftInvisibleItemFramesPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		RightClickItemFrameListener
		MakeItemFrameVisibleOnDamageListener
	}

}