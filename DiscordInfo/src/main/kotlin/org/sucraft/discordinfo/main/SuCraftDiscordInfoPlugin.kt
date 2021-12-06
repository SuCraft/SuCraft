/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.discordinfo.main

import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.discordinfo.command.SuCraftDiscordInfoCommands


class SuCraftDiscordInfoPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftDiscordInfoPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		SuCraftDiscordInfoCommands
	}

}