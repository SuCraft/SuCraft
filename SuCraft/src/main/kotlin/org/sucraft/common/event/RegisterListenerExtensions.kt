/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.event

import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.sucraft.main.SuCraftPlugin
import kotlin.reflect.KClass

// Utility functionality for registering the listener

fun SuCraftPlugin.registerEvents(listener: Listener) {
	Bukkit.getPluginManager().registerEvents(listener, this)
}

fun Listener.registerEvents() {
	SuCraftPlugin.instance.registerEvents(this)
}

// Utility functionality for listening to events

fun <E : Event> Listener.on(
	`class`: KClass<E>,
	priority: EventPriority = EventPriority.MONITOR,
	ignoreCancelled: Boolean = true,
	handlerMethod: E.() -> Unit
) = on(
	`class`.java,
	priority,
	ignoreCancelled,
	handlerMethod
)

fun <E : Event> Listener.on(
	`class`: Class<E>,
	priority: EventPriority = EventPriority.MONITOR,
	ignoreCancelled: Boolean = true,
	handlerMethod: E.() -> Unit
) {
	// The class may not have a handler list, in which case we must register
	// as listening to the superclass that does have a handler list
	val handlerListDeclaringClass = `class`.getMethod("getHandlerList").declaringClass
	if (handlerListDeclaringClass != `class`) {
		// If the class we are registering has no handler list, delegate to the subclass
		@Suppress("UNCHECKED_CAST")
		onVia(
			`class`,
			handlerListDeclaringClass as Class<Event>,
			priority,
			ignoreCancelled,
			handlerMethod
		)
	} else {
		// Register with Bukkit
		Bukkit.getPluginManager().registerEvent(`class`, this, priority, { _, event ->
			@Suppress("UNCHECKED_CAST")
			(event as E).handlerMethod()
		}, SuCraftPlugin.instance, ignoreCancelled)
	}
}

fun <E : Event, SuperE : Event> Listener.onVia(
	`class`: Class<E>,
	superClass: Class<SuperE>,
	priority: EventPriority = EventPriority.MONITOR,
	ignoreCancelled: Boolean = true,
	handlerMethod: E.() -> Unit
) = on(
	superClass,
	priority,
	ignoreCancelled
) {
	if (`class`.isInstance(this))
		`class`.cast(this).handlerMethod()
}