/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.sucraft.delegate

import org.json.JSONObject
import org.sucraft.core.common.sucraft.plugin.SuCraftDelegate
import org.sucraft.core.common.sucraft.plugin.SuCraftDelegateHolder
import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
interface MinecraftClientLocale<P: SuCraftPlugin> : SuCraftDelegate<P> {

	// Companion (implementation)

	companion object : SuCraftDelegateHolder<MinecraftClientLocale<*>>()

	// Delegate name

	override fun getDelegateInterfaceName() = MinecraftClientLocale::class.simpleName!!

	// Interface

	fun getJSON(): JSONObject

}