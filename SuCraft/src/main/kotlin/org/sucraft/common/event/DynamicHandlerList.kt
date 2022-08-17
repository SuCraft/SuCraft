/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.event

import org.bukkit.event.HandlerList
import org.bukkit.plugin.RegisteredListener

/**
 * A definition for clarity (could be done directly as well)
 * of a Bukkit HandlerList that has an abstract method for returning the registered listeners.
 */
abstract class DynamicHandlerList : HandlerList() {

	protected abstract val dynamicRegisteredListeners: Array<out RegisteredListener>

	override fun getRegisteredListeners() = dynamicRegisteredListeners

}