/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.main

import com.github.shynixn.mccoroutine.bukkit.SuspendingJavaPlugin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.bukkit.Bukkit
import org.sucraft.common.log.NestedLogger
import org.sucraft.common.log.SuCraftLogTexts
import org.sucraft.common.pattern.SingletonContainer

class SuCraftPlugin : SuspendingJavaPlugin() {

	// Logger

	lateinit var logger: NestedLogger
		private set

	// Enable and disable

	var isEnabling = false
		private set
	var isDisabling = false
		private set
	var hasFinishedOnEnable = false
		private set
	var hasFinishedOnDisable = false
		private set

	val isEnabledOrEnabling get() = isEnabling || isEnabled

	val isDisabledOrDisabling get() = isDisabling || !isEnabled

	@OptIn(ExperimentalCoroutinesApi::class)
	override fun onEnable() {
		try {
		// Mark as enabling
		isEnabling = true
		// Call super method
		super.onEnable()
		// Set the singleton instance
		instance = this
		// Create the logger
		logger = NestedLogger.create(this)
		// Initialize the modules
		modules.forEach {
			try {
				it.initialize()
			} catch (e: Exception) {
				logger.severe("Exception occurred while initializing module ${it.name}")
				throw e
			}
		}
		// Print to console that we finished
		logger.info(SuCraftLogTexts.pluginEnabled)
		// Mark as finished enabling
		isEnabling = false
		hasFinishedOnEnable = true
		} catch (e: Exception) {
			logger.severe("Exception occurred while initializing plugin")
			Bukkit.shutdown()
			throw e
		}
	}

	override fun onDisable() {
		// Mark as disabling
		isDisabling = true
		// Terminate the modules
		modules.reversed().forEach {
			try {
				it.terminate()
			} catch (e: Exception) {
				logger.severe("Exception occurred while terminating module ${it.name}")
				throw e
			}
		}
		// Call super method
		super.onDisable()
		// Print to console that we finished
		logger.info(SuCraftLogTexts.pluginDisabled)
		// Mark as finished disabling
		isDisabling = false
		hasFinishedOnDisable = true
	}

	// Kotlin default coroutine scopes (as opposed to the MCCoroutine scope)

	val defaultScope = CoroutineScope(Dispatchers.Default)

	val ioScope = CoroutineScope(Dispatchers.IO)

	// Singleton

	companion object : SingletonContainer<SuCraftPlugin>()

}