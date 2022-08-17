/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.command

import net.minecraft.server.dedicated.DedicatedPlayerList
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_19_R1.CraftServer
import org.bukkit.craftbukkit.v1_19_R1.command.VanillaCommandWrapper
import org.sucraft.common.command.node.root.SuCraftCommandRootNode
import org.sucraft.common.function.letIf
import org.sucraft.common.log.NestedLogger
import org.sucraft.common.module.AbstractSuCraftComponent
import org.sucraft.common.permission.SuCraftPermission

const val suCraftCommandFallbackPrefix = "sucraft"

/**
 * Represents a SuCraft command and its properties.
 */
sealed class SuCraftCommand<RN : SuCraftCommandRootNode<RN>>(
	val component: AbstractSuCraftComponent<*>,
	val label: String,
	val description: String,
	val aliases: Array<String> = emptyArray(),
	val permission: SuCraftPermission? = null,
	private val addFunctionality:
	RN.() -> RN
) {

	var isRegistered = false
		private set

	val logger by lazy {
		NestedLogger.create(component.logger, "/$label command")
	}

	val labelAndAliases
		get() = (sequenceOf(label) + aliases.asSequence())

	private lateinit var rootNode: RN

	protected abstract fun constructRootNode(label: String): RN

	private fun registerWithCommandAPI() {
		rootNode = constructRootNode(label)
			.apply { _command = this@SuCraftCommand }
			.withAliases(*aliases)
			.run { withFullDescription(description) }
			.letIf(permission != null) { it.withPermission(permission!!) }
			.run(addFunctionality)
			.apply { register() }
	}

	fun register() {
		if (isRegistered) return
		labelAndAliases.forEach { lowercaseRegisteredSuCraftCommandLabels.add(it.lowercase()) }
		// Register with CommandAPI
		registerWithCommandAPI()
		// Replace by a version in the Spigot command map
		val vanillaCommandDispatcher =
			((Bukkit.getServer() as CraftServer).handle as DedicatedPlayerList)._getServer().vanillaCommandDispatcher
		val vanillaCommandDispatcherRoot =
			vanillaCommandDispatcher._getDispatcher().root
		labelAndAliases.forEach { aliasOrLabel ->
			val childCommandNode = vanillaCommandDispatcherRoot.getChild(aliasOrLabel)
			val vanillaCommandWrapper =
				VanillaCommandWrapper(
					vanillaCommandDispatcher,
					childCommandNode
				)
			vanillaCommandDispatcherRoot.children.remove(childCommandNode)
			vanillaCommandWrapper.description = description
			Bukkit.getServer().commandMap.register(
				suCraftCommandFallbackPrefix,
				vanillaCommandWrapper
			)
		}
		(Bukkit.getServer() as CraftServer).syncCommands()
		isRegistered = true
	}

	// Direct access to logger methods

	fun info(message: Any?) = logger.info(message)

	fun warning(message: Any?) = logger.warning(message)

	fun severe(message: Any?) = logger.severe(message)

}