/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.command.node

import dev.jorel.commandapi.ArgumentTree
import dev.jorel.commandapi.executors.NativeCommandExecutor
import org.bukkit.command.CommandSender

/**
 * @param E The type returned from the [executesInternally] method and its derived methods.
 */
open class SuCraftCommandNodeByArgumentTree<E : SuCraftCommandNodeByArgumentTree<E>>(
	protected val parent: SuCraftCommandArgumentableNode<*>,
	val commandAPIArgumentTree: ArgumentTree,
	protected val thisNodeResolvableArgument: ResolvableArgument<*, *>
) : SuCraftCommandArgumentableNode<E> {

	@Suppress("UNCHECKED_CAST")
	override fun <Callee : CommandSender> SuCraftCommandNode<E>.executesInternally(
		commandAPIExecutor: NativeCommandExecutor
	) = apply {
		commandAPIArgumentTree.executesNative(commandAPIExecutor)
	} as E

	override fun getResolvableArguments(): Sequence<ResolvableArgument<*, *>> =
		parent.getResolvableArguments() + listOf(thisNodeResolvableArgument)

	override fun getCommand() = parent.getCommand()

	@Suppress("UNCHECKED_CAST")
	override fun SuCraftCommandArgumentableNode<E>.thenInternally(commandAPIArgumentTree: ArgumentTree) =
		apply {
			this@SuCraftCommandNodeByArgumentTree.commandAPIArgumentTree.then(commandAPIArgumentTree)
		} as E

}