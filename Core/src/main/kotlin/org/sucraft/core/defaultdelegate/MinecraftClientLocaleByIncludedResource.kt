/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.defaultdelegate

import org.json.JSONObject
import org.sucraft.core.common.general.log.AbstractLogger
import org.sucraft.core.common.sucraft.delegate.MinecraftClientLocale
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.core.main.SuCraftCorePlugin


object MinecraftClientLocaleByIncludedResource : MinecraftClientLocale<SuCraftCorePlugin>, SuCraftComponent<SuCraftCorePlugin>(SuCraftCorePlugin.getInstance()) {

	// Initialization

	init {
		MinecraftClientLocale.registerImplementation(this)
	}

	// Delegate overrides

	override fun getDelegatePlugin(): SuCraftCorePlugin = plugin

	override fun getDelegateLogger(): AbstractLogger = logger

	// Implementation

	override fun getJSON(): JSONObject =
		try {
			plugin.getResource("en_us.json")?.let { JSONObject(String(it.readAllBytes(), Charsets.UTF_8)) } ?: throw IllegalStateException("Could not load Minecraft client locale JSON resource")
		} catch (e: Exception) {
			logger.warning("An exception occurred while opening the file containing the locale!")
			throw e
		}

}