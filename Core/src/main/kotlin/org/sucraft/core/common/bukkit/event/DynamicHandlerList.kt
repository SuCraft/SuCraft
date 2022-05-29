/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.core.common.bukkit.event

import org.bukkit.event.HandlerList
import org.bukkit.plugin.RegisteredListener

/**
 * A definition for clarity (could be done directly as well) of a Bukkit HandlerList that has an abstract method for returning the registered listeners
 */
abstract class DynamicHandlerList : HandlerList() {

	abstract fun getDynamicRegisteredListeners(): Array<out RegisteredListener>

	override fun getRegisteredListeners() =
		getDynamicRegisteredListeners()

}