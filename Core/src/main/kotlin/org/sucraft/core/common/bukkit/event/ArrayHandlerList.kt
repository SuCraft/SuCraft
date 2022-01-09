/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.core.common.bukkit.event

import org.bukkit.event.HandlerList
import org.bukkit.plugin.RegisteredListener


/**
 * A Bukkit HandlerList backed by a static array of registered listeners
 */
class ArrayHandlerList(private vararg val registeredListenerArray: RegisteredListener) : DynamicHandlerList() {

	constructor(vararg handlerLists: HandlerList): this(*handlerLists.asSequence().flatMap { it.registeredListeners.asSequence() }.toList().toTypedArray())

	override fun getDynamicRegisteredListeners() =
		registeredListenerArray

}