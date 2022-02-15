/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.defaultdelegate

import org.bukkit.Bukkit
import org.sucraft.core.common.general.log.AbstractLogger
import org.sucraft.core.common.sucraft.delegate.GlobalTickCounter
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.core.main.SuCraftCorePlugin


object GlobalTickCounterUsingScheduledTask : GlobalTickCounter<SuCraftCorePlugin>, SuCraftComponent<SuCraftCorePlugin>(SuCraftCorePlugin.getInstance()) {

	// Data

	private var value: Long = 0

	// Initialization

	init {
		GlobalTickCounter.registerImplementation(this)
		Bukkit.getScheduler().runTaskTimer(SuCraftCorePlugin.getInstance(), Runnable { value++ }, 1, 1)
	}

	// Delegate overrides

	override fun getDelegatePlugin(): SuCraftCorePlugin = plugin

	override fun getDelegateLogger(): AbstractLogger = logger

	// Implementation

	override fun getValue(): Long = value

}