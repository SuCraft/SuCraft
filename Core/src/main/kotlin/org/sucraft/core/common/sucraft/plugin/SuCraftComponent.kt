/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.sucraft.plugin

import org.bukkit.event.Listener
import org.sucraft.core.common.bukkit.log.NestedLogger
import java.lang.IllegalStateException


abstract class SuCraftComponent<P : SuCraftPlugin>(val plugin: P, val customName: String? = null) : Listener {

	val name: String = customName ?: this::class.java.simpleName

	// Logger

	val logger: NestedLogger

	// Initialization

	init {
		// Make sure the plugin is initialized
		if (!(plugin.enabling || plugin.isEnabled)) {
			throw IllegalStateException("Attempted to initialize component \"" + name + "\" for plugin \"" + plugin.name + "\" that is not enabling or enabled")
		}
		// Create the logger
		logger = NestedLogger.create(plugin.getNestedLogger(), name)
		// Register events
		@Suppress("LeakingThis")
		plugin.registerEvents(this)
	}

}