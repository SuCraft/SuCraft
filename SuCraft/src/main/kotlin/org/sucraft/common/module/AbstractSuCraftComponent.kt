/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.module

import org.bukkit.event.Listener
import org.bukkit.permissions.PermissionDefault
import org.sucraft.common.command.SuCraftCommand
import org.sucraft.common.command.SuCraftCommandByCommandAPICommand
import org.sucraft.common.command.SuCraftCommandByCommandTree
import org.sucraft.common.command.node.root.SuCraftCommandRootNodeByCommandAPICommand
import org.sucraft.common.command.node.root.SuCraftCommandRootNodeByCommandTree
import org.sucraft.common.event.registerEvents
import org.sucraft.common.function.runEach
import org.sucraft.common.itemstack.recipe.CustomRecipe
import org.sucraft.common.log.NestedLogger
import org.sucraft.common.permission.SuCraftPermission
import org.sucraft.main.SuCraftPlugin

/**
 * An abstract class for a [component][SuCraftComponent] that [modules][SuCraftModule] can also extend.
 */
abstract class AbstractSuCraftComponent<M : SuCraftModule<M>>() : Listener {

	open val name: String = this::class.java.simpleName
	lateinit var logger: NestedLogger
		private set
	var hasInitializationStarted = false
		private set
	var hasInitializationFinished = false
		private set
	var hasTerminationStarted = false
		private set
	var hasTerminationFinished = false
		private set

	abstract val module: M
	abstract val parentLogger: NestedLogger

	protected open val dependencies: List<AbstractSuCraftComponent<*>> = emptyList()

	private val reverseDependencies: MutableList<AbstractSuCraftComponent<*>> = ArrayList(0)

	private fun addReverseDependency(reverseDependency: AbstractSuCraftComponent<*>) {
		reverseDependencies += reverseDependency
	}

	protected open fun onInitialize() {}

	/**
	 * @return Whether this component's initialization was indeed completed
	 * (which is false when this method is called on a component
	 * for which initialization [has already started][hasInitializationStarted]).
	 */
	fun initialize(): Boolean {
		// Make sure the plugin is initialized
		if (!SuCraftPlugin.instance.isEnabledOrEnabling)
			throw IllegalStateException(
				"Attempted to initialize SuCraft component $name while the plugin is not enabling or enabled"
			)
		// Make sure this component is not initialized
		if (hasInitializationStarted) return false
		// Mark initialization as started
		hasInitializationStarted = true
		// Create the logger
		logger = NestedLogger.create(parentLogger, name)
		// First make sure all components this component depends on are initialized
		dependencies.runEach { initialize() }
		// Mark this component as a reverse dependency in all dependencies
		dependencies.forEach { it.addReverseDependency(this) }
		// Run other subclass-specific initialization
		onInitialize()
		// Call hashCode() on all nested objects to make sure they are initialized
		// and any contained permission or command initializations are performed
		this::class.nestedClasses.runEach { objectInstance?.hashCode() }
		// Register Permissions
		declaredPermissions.runEach { register() }
		// Register Commands
		declaredCommands.runEach { register() }
		// Register custom recipes
		customRecipes.runEach { register() }
		// Register regular event methods
		registerEvents()
		// Mark initialization as finished
		hasInitializationFinished = true
		return true
	}

	protected open fun onTerminate() {}

	/**
	 * @return Whether this component's termination was indeed completed
	 * (which is false when this method is called on a component
	 * for which termination [has already started][hasTerminationStarted]).
	 */
	fun terminate(): Boolean {
		// Make sure the plugin is disabling
		if (!SuCraftPlugin.instance.isDisabling)
			throw IllegalStateException(
				"Attempted to terminate SuCraft component $name while the plugin is not disabling"
			)
		// Make sure this component is not terminated
		if (hasTerminationStarted) return false
		// Mark termination as started
		hasTerminationStarted = true
		// First make sure all components that depend on this component are terminated
		reverseDependencies.runEach { terminate() }
		// Run other subclass-specific termination
		onTerminate()
		// Mark termination as finished
		hasInitializationFinished = true
		return true
	}

	// Declaring Permissions

	protected open val permissionPrefix by lazy {
		"${name.lowercase()}."
	}

	private val declaredPermissions: MutableList<SuCraftPermission> = ArrayList(0)

	/**
	 * @return A [SuCraftPermission] with the given [unprefixedKey] prefixed with this component's [permissionPrefix].
	 *
	 * The permission will be [registered][SuCraftPermission.register] upon initialization of this module or
	 * the invocation of this function, whichever comes latest.
	 */
	protected fun permission(
		unprefixedKey: String,
		description: String?,
		default: PermissionDefault
	) = SuCraftPermission(
		"$permissionPrefix$unprefixedKey",
		description,
		default
	).apply(declaredPermissions::add)
		.apply {
			if (hasInitializationFinished)
				register()
		}

	protected fun commandPermission(
		commandLabel: String,
		default: PermissionDefault
	) = permission(
		commandLabel,
		"Use the /$commandLabel command",
		default
	)

	// Declaring Commands

	private val declaredCommands: MutableList<SuCraftCommand<*>> = ArrayList(0)

	/**
	 * @return A [SuCraftCommand] with the given [label].
	 *
	 * The command will be [registered][SuCraftCommand.register] upon initialization of this module or
	 * the invocation of this function, whichever comes latest.
	 */
	protected fun command(
		label: String,
		description: String,
		aliases: Array<String> = emptyArray(),
		permission: SuCraftPermission? = null,
		addCommandFunctionality:
		SuCraftCommandRootNodeByCommandAPICommand.() -> SuCraftCommandRootNodeByCommandAPICommand
	) = SuCraftCommandByCommandAPICommand(
		this,
		label,
		description,
		aliases,
		permission,
		addCommandFunctionality
	).apply(declaredCommands::add)
		.apply {
			if (hasInitializationFinished)
				register()
		}

	/**
	 * Creates a command with no aliases.
	 *
	 * @see [command]
	 */
	protected fun command(
		label: String,
		description: String,
		permission: SuCraftPermission? = null,
		addCommandFunctionality:
		SuCraftCommandRootNodeByCommandAPICommand.() -> SuCraftCommandRootNodeByCommandAPICommand
	) = command(
		label,
		description,
		emptyArray(),
		permission,
		addCommandFunctionality
	)

	/**
	 * Creates a command with an auto-generated permission.
	 *
	 * @see [command]
	 */
	protected fun command(
		label: String,
		description: String,
		aliases: Array<String> = emptyArray(),
		permissionDefault: PermissionDefault,
		addCommandFunctionality:
		SuCraftCommandRootNodeByCommandAPICommand.() -> SuCraftCommandRootNodeByCommandAPICommand
	) = command(
		label,
		description,
		aliases,
		commandPermission(
			label,
			permissionDefault
		),
		addCommandFunctionality
	)

	/**
	 * Creates a command with no aliases, and an auto-generated permission.
	 *
	 * @see [command]
	 */
	protected fun command(
		label: String,
		description: String,
		permissionDefault: PermissionDefault,
		addCommandFunctionality:
		SuCraftCommandRootNodeByCommandAPICommand.() -> SuCraftCommandRootNodeByCommandAPICommand
	) = command(
		label,
		description,
		emptyArray(),
		permissionDefault,
		addCommandFunctionality
	)

	/**
	 * @return A [SuCraftCommand] with the given [label].
	 *
	 * The command will be [registered][SuCraftCommand.register] upon initialization of this module or
	 * the invocation of this function, whichever comes latest.
	 */
	protected fun commandTree(
		label: String,
		description: String,
		aliases: Array<String> = emptyArray(),
		permission: SuCraftPermission? = null,
		addCommandFunctionality:
		SuCraftCommandRootNodeByCommandTree.() -> SuCraftCommandRootNodeByCommandTree
	) = SuCraftCommandByCommandTree(
		this,
		label,
		description,
		aliases,
		permission,
		addCommandFunctionality
	).apply(declaredCommands::add)
		.apply {
			if (hasInitializationFinished)
				register()
		}

	/**
	 * Creates a command with no aliases.
	 *
	 * @see [commandTree]
	 */
	protected fun commandTree(
		label: String,
		description: String,
		permission: SuCraftPermission? = null,
		addCommandFunctionality:
		SuCraftCommandRootNodeByCommandTree.() -> SuCraftCommandRootNodeByCommandTree
	) = commandTree(
		label,
		description,
		emptyArray(),
		permission,
		addCommandFunctionality
	)

	/**
	 * Creates a command with an auto-generated permission.
	 *
	 * @see [command]
	 */
	protected fun commandTree(
		label: String,
		description: String,
		aliases: Array<String> = emptyArray(),
		permissionDefault: PermissionDefault,
		addCommandFunctionality:
		SuCraftCommandRootNodeByCommandTree.() -> SuCraftCommandRootNodeByCommandTree
	) = commandTree(
		label,
		description,
		aliases,
		commandPermission(
			label,
			permissionDefault
		),
		addCommandFunctionality
	)

	/**
	 * Creates a command with no aliases, and an auto-generated permission.
	 *
	 * @see [command]
	 */
	protected fun commandTree(
		label: String,
		description: String,
		permissionDefault: PermissionDefault,
		addCommandFunctionality:
		SuCraftCommandRootNodeByCommandTree.() -> SuCraftCommandRootNodeByCommandTree
	) = commandTree(
		label,
		description,
		emptyArray(),
		permissionDefault,
		addCommandFunctionality
	)

	// Declaring custom recipes

	protected open val customRecipes: List<CustomRecipe<*>> = emptyList()

	// Direct access to logger methods

	protected fun info(message: Any?) = logger.info(message)

	protected fun warning(message: Any?) = logger.warning(message)

	protected fun severe(message: Any?) = logger.severe(message)

}