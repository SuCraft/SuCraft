/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.command.node

import dev.jorel.commandapi.ArgumentTree
import dev.jorel.commandapi.CommandTree
import dev.jorel.commandapi.executors.NativeCommandExecutor
import org.bukkit.command.CommandSender
import org.sucraft.common.permission.SuCraftPermission

/**
 * @param P The type returned from the [withPermission] method and its derived methods.
 * @param E The type returned from the [executesInternally] method and its derived methods.
 */
abstract class SuCraftCommandNodeByCommandTree<P : SuCraftCommandNodeByCommandTree<P, E>,
		E : SuCraftCommandNodeByCommandTree<P, E>>(
	protected val commandTree: CommandTree
) : SuCraftCommandPermissibleNode<P, E>, SuCraftCommandArgumentableNode<E> {

	@Suppress("UNCHECKED_CAST")
	override fun withPermission(permission: SuCraftPermission) = apply {
		commandTree.withPermission(permission.key)
	} as P

	@Suppress("UNCHECKED_CAST")
	override fun <Callee : CommandSender> SuCraftCommandNode<E>.executesInternally(
		commandAPIExecutor: NativeCommandExecutor
	) = apply {
		commandTree.executesNative(commandAPIExecutor)
	} as E

	@Suppress("UNCHECKED_CAST")
	override fun SuCraftCommandArgumentableNode<E>.thenInternally(commandAPIArgumentTree: ArgumentTree) =
		apply {
			commandTree.then(commandAPIArgumentTree)
		} as E

}