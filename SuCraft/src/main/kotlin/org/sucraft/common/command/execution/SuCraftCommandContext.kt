/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.command.execution

import dev.jorel.commandapi.wrappers.NativeProxyCommandSender
import kotlinx.coroutines.CoroutineScope
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.command.CommandSender
import org.sucraft.common.command.node.AsyncResolvableArgument
import org.sucraft.common.command.node.ResolvableArgument
import org.sucraft.common.command.node.SyncResolvableArgument

class SuCraftCommandContext<Callee : CommandSender>(
	val args: Array<SuCraftCommandContextArgument<*, *>>,
	val caller: CommandSender,
	val callee: Callee,
	val location: Location?,
	val world: World?
) {

	@Suppress("UNCHECKED_CAST")
	constructor(args: Array<SuCraftCommandContextArgument<*, *>>, nativeProxySender: NativeProxyCommandSender) : this(
		args,
		nativeProxySender.caller,
		nativeProxySender.callee as Callee,
		nativeProxySender.location,
		nativeProxySender.world
	)

	private val resolvableArgumentToContextArgumentMap =
		args.associateBy(SuCraftCommandContextArgument<*, *>::resolvableArgument)

	operator fun <T> invoke(argument: SyncResolvableArgument<*, T>) =
		@Suppress("UNCHECKED_CAST")
		(resolvableArgumentToContextArgumentMap[argument] as SyncSuCraftCommandContextArgument<*, T>).resolve()

	suspend operator fun <T> invoke(argument: AsyncResolvableArgument<*, T>) =
		@Suppress("UNCHECKED_CAST")
		(resolvableArgumentToContextArgumentMap[argument] as AsyncSuCraftCommandContextArgument<*, T>).resolve()

}

/**
 * Contains the value of an argument as provided in a [command invocation][SuCraftCommandContext].
 */
sealed class SuCraftCommandContextArgument<CommandAPIT : Any, T>(
	val resolvableArgument: ResolvableArgument<CommandAPIT, T>,
	val index: Int,
	val input: CommandAPIT
)

class SyncSuCraftCommandContextArgument<CommandAPIT : Any, T>(
	val syncResolvableArgument: SyncResolvableArgument<CommandAPIT, T>,
	index: Int,
	input: CommandAPIT
) : SuCraftCommandContextArgument<CommandAPIT, T>(
	syncResolvableArgument,
	index,
	input
) {

	private val value by lazy {
		syncResolvableArgument.resolve(input)
	}

	fun resolve() = value

}

class AsyncSuCraftCommandContextArgument<CommandAPIT : Any, T>(
	val asyncResolvableArgument: AsyncResolvableArgument<CommandAPIT, T>,
	index: Int,
	input: CommandAPIT
) : SuCraftCommandContextArgument<CommandAPIT, T>(
	asyncResolvableArgument,
	index,
	input
) {

	lateinit var executionScope: CoroutineScope

	private val deferredValue by lazy {
		asyncResolvableArgument.createDeferred(executionScope, input)
	}

	suspend fun resolve() =
		deferredValue.await()

}