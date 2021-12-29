/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.sucraft.plugin

import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.sucraft.core.common.bukkit.log.NestedLogger
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.core.common.sucraft.log.SuCraftLogTexts


@Suppress("MemberVisibilityCanBePrivate")
abstract class SuCraftPlugin : JavaPlugin() {

	// Enable and disable

	override fun onEnable() {

		// Mark as enabling
		enabling = true

		// Set the singleton instance
		@Suppress("UNCHECKED_CAST")
		(this::class.java.declaredClasses.first { it.simpleName.equals("Companion") }!!.kotlin.objectInstance as SingletonContainer<out SuCraftPlugin>).setInstanceUntyped(this)
		// Create the logger
		nestedLogger = NestedLogger.create(this)

		// Print to console and call enable subroutine
		//nestedLogger!!.info(SuCraftLogTexts.pluginEnabling) // Commented out because Bukkit already prints this for us (to reduce unnecessary messages)
		onSuCraftPluginEnable()
		nestedLogger!!.info(SuCraftLogTexts.pluginEnabled)

		// Mark as finished enabling
		enabling = false

	}

	override fun onDisable() {

		// Mark as disabling
		disabling = true

		// Print to console and call disable subroutine
		//nestedLogger!!.info(SuCraftLogTexts.pluginDisabling) // Commented out because Bukkit already prints this for us (to reduce unnecessary messages)
		onSuCraftPluginDisable()
		nestedLogger!!.info(SuCraftLogTexts.pluginDisabled)

		// Mark as finished disabling
		disabling = false

	}

	protected open fun onSuCraftPluginEnable() {}
	protected open fun onSuCraftPluginDisable() {}

	var enabling = false
		private set
	var disabling = false
		private set

	val isEnabledOrEnabling get() = enabling || isEnabled

	val isDisabledOrDisabling get() = disabling || !isEnabled

	// Logger

	private var nestedLogger: NestedLogger? = null
	fun getNestedLogger() = nestedLogger ?: throw IllegalStateException("The nested logger has not been initialized yet")

	// Convenience methods for doing plugin-based things

	fun registerEvents(listener: Listener) = Bukkit.getPluginManager().registerEvents(listener, this)

	fun getNamespacedKey(key: String) = NamespacedKey(this, key)

}