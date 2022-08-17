/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.event

import org.bukkit.event.HandlerList
import org.bukkit.plugin.RegisteredListener

/**
 * A Bukkit HandlerList backed by a static array of registered listeners.
 */
class ArrayHandlerList(private vararg val registeredListenerArray: RegisteredListener) : DynamicHandlerList() {

	constructor(vararg handlerLists: HandlerList) :
			this(*handlerLists.flatMap { it.registeredListeners.asSequence() }.toTypedArray())

	override val dynamicRegisteredListeners = registeredListenerArray

}

/**
 * Syntactical sugar construction for [ArrayHandlerList].
 */
infix fun HandlerList.and(handlerList: HandlerList) =
	ArrayHandlerList(this, handlerList)