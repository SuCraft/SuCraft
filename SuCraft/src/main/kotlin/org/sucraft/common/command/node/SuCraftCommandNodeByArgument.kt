/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.command.node

import dev.jorel.commandapi.arguments.Argument
import org.sucraft.common.permission.SuCraftPermission

// Abstract class with type parameters

/**
 * @param A The type of the [Argument].
 * @param P The type returned from the [withPermission] method and its derived methods.
 * @param E The type returned from the [executesInternally] method and its derived methods.
 */
abstract class SuCraftCommandNodeByArgument<A : Argument<*>,
		P : SuCraftCommandNodeByArgument<A, P, E>,
		E : SuCraftCommandNodeByArgumentTree<E>>(
	parent: SuCraftCommandArgumentableNode<*>,
	private val commandAPIArgument: A,
	thisNodeResolvableArgument: ResolvableArgument<*, *>
) : SuCraftCommandNodeByArgumentTree<E>(parent, commandAPIArgument, thisNodeResolvableArgument),
	SuCraftCommandPermissibleNode<P, E> {

	@Suppress("UNCHECKED_CAST")
	override fun withPermission(permission: SuCraftPermission) = apply {
		commandAPIArgument.withPermission(permission.key)
	} as P

}

// Concrete final class that returns its own type

class FinalSuCraftCommandNodeByArgument<A : Argument<*>>(
	parent: SuCraftCommandArgumentableNode<*>,
	commandAPIArgument: A,
	thisNodeResolvableArgument: ResolvableArgument<*, *>
) : SuCraftCommandNodeByArgument<A,
		FinalSuCraftCommandNodeByArgument<A>,
		FinalSuCraftCommandNodeByArgument<A>>(
	parent,
	commandAPIArgument,
	thisNodeResolvableArgument
)