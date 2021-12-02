/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.sucraft.delegate

import org.json.JSONObject
import org.sucraft.core.common.sucraft.plugin.SuCraftDelegateHolder


interface MinecraftClientLocale {

	// Companion (implementation)

	companion object : SuCraftDelegateHolder<MinecraftClientLocale>()

	// Interface

	fun getJSON(): JSONObject

}