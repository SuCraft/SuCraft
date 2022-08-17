/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.log

import org.bukkit.plugin.java.JavaPlugin

/**
 * A nested logger belongs to either a plugin (representing the plugin as a whole), or a parent logger and own name
 */
abstract class NestedLogger : AbstractLogger {

	// Abstract methods

	protected abstract fun turnGivenMessageToDelegatableString(message: Any?): String
	protected abstract fun delegateInfo(message: String)
	protected abstract fun delegateWarning(message: String)
	protected abstract fun delegateSevere(message: String)

	// Exposed methods

	override fun info(message: Any?) = delegateInfo(turnGivenMessageToDelegatableString(message))
	override fun warning(message: Any?) = delegateWarning(turnGivenMessageToDelegatableString(message))
	override fun severe(message: Any?) = delegateSevere(turnGivenMessageToDelegatableString(message))

	/**
	 * Convenience method to either call info or warning depending on the given boolean
	 * @param message The message
	 * @param useWarning Whether to call warning
	 */
	fun infoOrWarning(message: Any?, useWarning: Boolean) = if (useWarning) warning(message) else info(message)

	// Subclasses

	private class PluginNestedLogger(private val plugin: JavaPlugin) : NestedLogger() {

		override fun turnGivenMessageToDelegatableString(message: Any?) = "$message"
		override fun delegateInfo(message: String) = plugin.logger.info(message)
		override fun delegateWarning(message: String) = plugin.logger.warning(message)
		override fun delegateSevere(message: String) = plugin.logger.severe(message)

	}

	private class ParentNestedLogger(private val parentLogger: NestedLogger, private val name: String) : NestedLogger() {

		override fun turnGivenMessageToDelegatableString(message: Any?) = "[$name] $message"
		override fun delegateInfo(message: String) = parentLogger.info(message)
		override fun delegateWarning(message: String) = parentLogger.warning(message)
		override fun delegateSevere(message: String) = parentLogger.severe(message)

	}

	// Static instantiation methods (subclasses are not exposed)

	companion object {

		fun create(plugin: JavaPlugin): NestedLogger = PluginNestedLogger(plugin)
		fun create(parentLogger: NestedLogger, name: String): NestedLogger = ParentNestedLogger(parentLogger, name)

	}

}