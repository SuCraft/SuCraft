/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.bukkitapiextension.event

import org.bukkit.event.Event


abstract class EventAdapter<O: Event, A: BukkitAPIExtensionEvent> : EventDispatcher<A>() {

	fun fire(event: O) {
		adapt(event)?.let(::fire)
	}

	abstract fun adapt(event: O): A?

}