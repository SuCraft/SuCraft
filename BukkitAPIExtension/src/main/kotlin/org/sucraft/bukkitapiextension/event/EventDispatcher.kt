/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.bukkitapiextension.event

import org.bukkit.Bukkit
import org.sucraft.bukkitapiextension.main.SuCraftBukkitAPIExtensionPlugin
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent

abstract class EventDispatcher<E: BukkitAPIExtensionEvent> : SuCraftComponent<SuCraftBukkitAPIExtensionPlugin>(SuCraftBukkitAPIExtensionPlugin.getInstance()) {

	fun fire(event: E) {
		Bukkit.getPluginManager().callEvent(event)
	}

}