/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.bukkitapiextension.main

import com.google.common.reflect.ClassPath
import org.sucraft.bukkitapiextension.event.playerscoopentity.PlayerScoopEntityEventDispatcher
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import java.io.IOException
import java.lang.reflect.InvocationTargetException


class SuCraftBukkitAPIExtensionPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftBukkitAPIExtensionPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize event dispatchers
		PlayerScoopEntityEventDispatcher
	}

}