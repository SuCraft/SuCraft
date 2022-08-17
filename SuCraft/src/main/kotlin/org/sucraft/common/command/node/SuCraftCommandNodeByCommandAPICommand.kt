/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.command.node

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.executors.NativeCommandExecutor
import org.bukkit.command.CommandSender
import org.sucraft.common.permission.SuCraftPermission

/**
 * @param P The type returned from the [withPermission] method and its derived methods.
 * @param E The type returned from the [executesInternally] method and its derived methods.
 */
abstract class SuCraftCommandNodeByCommandAPICommand<P : SuCraftCommandNodeByCommandAPICommand<P, E>,
		E : SuCraftCommandNodeByCommandAPICommand<P, E>>(
	protected val commandAPICommand: CommandAPICommand
) : SuCraftCommandPermissibleNode<P, E> {

	@Suppress("UNCHECKED_CAST")
	override fun withPermission(permission: SuCraftPermission) = apply {
		commandAPICommand.withPermission(permission.key)
	} as P

	@Suppress("UNCHECKED_CAST")
	override fun <Callee : CommandSender> SuCraftCommandNode<E>.executesInternally(
		commandAPIExecutor: NativeCommandExecutor
	) = apply {
		commandAPICommand.executesNative(commandAPIExecutor)
	} as E

	override fun getResolvableArguments(): Sequence<ResolvableArgument<*, *>> =
		// CommandAPICommand-based SuCraft commands do not support resolvable arguments yet
		emptySequence()

}