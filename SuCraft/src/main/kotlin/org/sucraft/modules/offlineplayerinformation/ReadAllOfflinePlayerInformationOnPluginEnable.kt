/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.offlineplayerinformation

import org.sucraft.common.concurrent.bukkitLaunch
import org.sucraft.common.module.SuCraftComponent
import org.sucraft.modules.offlineplayerinformation.OfflinePlayerInformation.getAllUsedNames

/**
 * Starts pre-reading all offline player information when the plugin is enabled.
 */
object ReadAllOfflinePlayerInformationOnPluginEnable : SuCraftComponent<OfflinePlayerInformation>() {

	// Settings

	private const val readAllNames = true

	// Initialize

	override fun onInitialize() {
		super.onInitialize()
		// Read all names
		if (readAllNames) bukkitLaunch { getAllUsedNames() }
	}

}