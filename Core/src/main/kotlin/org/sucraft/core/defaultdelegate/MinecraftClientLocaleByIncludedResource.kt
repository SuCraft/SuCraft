/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.defaultdelegate

import org.json.JSONException
import org.json.JSONObject
import org.sucraft.core.common.sucraft.delegate.MinecraftClientLocale
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.core.main.SuCraftCorePlugin
import java.io.IOException


object MinecraftClientLocaleByIncludedResource : MinecraftClientLocale, SuCraftComponent<SuCraftCorePlugin>(SuCraftCorePlugin.getInstance()) {

	override fun getJSON(): JSONObject =
		try {
			plugin.getResource("en_us.json")?.let { JSONObject(String(it.readAllBytes(), Charsets.UTF_8)) } ?: throw IllegalStateException("Could not load Minecraft client locale JSON resource")
		} catch (e: Exception) {
			logger.warning("An exception occurred while opening the file containing the locale!")
			throw e
		}

}