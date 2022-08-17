/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.command.node

import com.mojang.brigadier.exceptions.CommandExceptionType
import com.mojang.brigadier.exceptions.CommandSyntaxException
import dev.jorel.commandapi.exceptions.WrapperCommandSyntaxException
import dev.jorel.commandapi.executors.NativeCommandExecutor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.sucraft.common.command.ComponentCommandSyntaxException
import org.sucraft.common.command.SimpleComponentCommandSyntaxExceptionType
import org.sucraft.common.command.SuCraftCommand
import org.sucraft.common.command.execution.AsyncSuCraftCommandContextArgument
import org.sucraft.common.command.execution.AsyncSuCraftCommandExecutor
import org.sucraft.common.command.execution.SuCraftCommandContext
import org.sucraft.common.command.execution.SyncSuCraftCommandExecutor
import org.sucraft.common.command.sendMessage
import org.sucraft.common.concurrent.bukkitScope
import org.sucraft.common.concurrent.ignoreCancelOrExceptions
import org.sucraft.common.text.SYNTAX_ERROR
import org.sucraft.common.text.component
import org.sucraft.common.text.patternAndCompile
import kotlin.reflect.KClass

// Exception types

val COMMAND_CALLEE_MUST_BE_CALLER_EXCEPTION_TYPE = SimpleComponentCommandSyntaxExceptionType(
	"COMMAND_CALLEE_MUST_BE_CALLER_EXCEPTION_TYPE",
	component(859098564948887124L, SYNTAX_ERROR) { "That command can not be run as another callee." }
)

interface InvalidCalleeTypeExceptionType {

	@Throws(CommandSyntaxException::class)
	fun doThrow(intendedCalleeType: KClass<out CommandSender>)

}

object CommandOnlyUsableByOtherCalleeTypeExceptionType : InvalidCalleeTypeExceptionType {

	private val type = object : CommandExceptionType {

		override fun toString() = "COMMAND_ONLY_USABLE_BY_OTHER_CALLEE_TYPE"

	}

	override fun doThrow(intendedCalleeType: KClass<out CommandSender>) {
		throw ComponentCommandSyntaxException(
			type,
			patternAndCompile(
				449304991394123593L, SYNTAX_ERROR,
				intendedCalleeType.java.name
			) {
				+"That command can only be used by a" + variable - "."
			}
		)
	}

}

object CommandOnlyUsableByPlayerExceptionType : SimpleComponentCommandSyntaxExceptionType(
	"COMMAND_ONLY_USABLE_BY_PLAYER",
	component(940098231859000068L, SYNTAX_ERROR) { "That command can only be used by a player." }
), InvalidCalleeTypeExceptionType {

	override fun doThrow(intendedCalleeType: KClass<out CommandSender>) {
		doThrow()
	}

}

// Class

/**
 * @param E The type returned from the [executesSync] method and its derived methods.
 */
interface SuCraftCommandNode<E : SuCraftCommandNode<E>> {

	// Implementation details (do not need to be called from outside)

	fun <Callee : CommandSender> SuCraftCommandNode<E>.executesInternally(
		commandAPIExecutor: NativeCommandExecutor
	): E

	fun getResolvableArguments(): Sequence<ResolvableArgument<*, *>>

	/**
	 * @return The [command][SuCraftCommand] this node is a part of.
	 * @throws IllegalStateException If this method is called to early (i.e. when the command has not been constructed
	 * and notified this node of being its resulting command yet).
	 */
	@Throws(IllegalStateException::class)
	fun getCommand(): SuCraftCommand<*>

	/**
	 * @return The [command][SuCraftCommand] this node is a part of.
	 * @throws IllegalStateException If this method is called to early (see [getCommand]).
	 */
	@Throws(IllegalStateException::class)
	fun getCommandLogger() = getCommand().logger

	// Provided functionality

	/**
	 * Executes the command up to this point as the given executor.
	 */
	fun <Callee : CommandSender> executesSync(
		calleeType: KClass<Callee>,
		allowFromProxy: Boolean = true,
		invalidCalleeTypeExceptionType: InvalidCalleeTypeExceptionType =
			CommandOnlyUsableByOtherCalleeTypeExceptionType,
		executor: SyncSuCraftCommandExecutor<Callee>
	): E = executesInternally<Callee> { sender, args ->
		try {
			// Check if we are being called from the caller if proxied calls are not allowed
			if (!allowFromProxy && sender.caller != sender.callee)
				throw COMMAND_CALLEE_MUST_BE_CALLER_EXCEPTION_TYPE.create()
			// Check if we are being called from the right type of callee
			if (!calleeType.isInstance(sender.callee))
				invalidCalleeTypeExceptionType.doThrow(calleeType)
			// Prepare argument conversions
			getResolvableArguments()
				.filter { !it.hasDefaultValueNotProvidedByCommandAPI }
				.forEachIndexed { index, resolvableArgument ->
					resolvableArgument.setCommandAPIValue(args[index])
				}
			getResolvableArguments()
				.filter { it.hasDefaultValueNotProvidedByCommandAPI }
				.forEach { it.setCommandAPIValueByDefaultValue() }
			// Call the provided SuCraft executor
			@Suppress("UNCHECKED_CAST")
			(sender.callee as Callee).executor(
				SuCraftCommandContext(
					getResolvableArguments()
						.filter { !it.hasDefaultValueNotProvidedByCommandAPI }
						.mapIndexed { index, resolvableArgument ->
							resolvableArgument.toContextArgument(index, args[index])
						}.toList().toTypedArray(),
					sender
				)
			)
		} catch (e: CommandSyntaxException) {
			// Wrap the CommandSyntaxException with CommandAPI's preferred exception type
			throw WrapperCommandSyntaxException(e)
		}
	}

	/**
	 * The same as [executesSync], but execution will be asynchronous by the given scope.
	 */
	fun <Callee : CommandSender> executesAsync(
		calleeType: KClass<Callee>,
		allowFromProxy: Boolean = true,
		invalidCalleeTypeExceptionType: InvalidCalleeTypeExceptionType =
			CommandOnlyUsableByOtherCalleeTypeExceptionType,
		scope: CoroutineScope = bukkitScope,
		executor: AsyncSuCraftCommandExecutor<Callee>
	): E = executesSync(
		calleeType,
		allowFromProxy,
		invalidCalleeTypeExceptionType
	) { context ->
		context.args.forEach {
			(it as? AsyncSuCraftCommandContextArgument<*, *>)?.apply { executionScope = scope }
		}
		scope.launch {
			ignoreCancelOrExceptions(getCommandLogger()) {
				try {
					executor(context)
				} catch (e: CommandSyntaxException) {
					context.caller.sendMessage(e)
				}
			}
		}
	}

	/**
	 * Executes the command up to this point as the given executor.
	 *
	 * This accepts any type of callee.
	 */
	fun executesSync(
		allowFromProxy: Boolean = true,
		executor: SyncSuCraftCommandExecutor<CommandSender>
	) = executesSync(
		CommandSender::class,
		allowFromProxy,
		CommandOnlyUsableByOtherCalleeTypeExceptionType,
		executor
	)

	/**
	 * The same as [executesSync], but execution will be asynchronous by the given scope.
	 */
	fun executesAsync(
		allowFromProxy: Boolean = true,
		scope: CoroutineScope = bukkitScope,
		executor: AsyncSuCraftCommandExecutor<CommandSender>
	) = executesAsync(
		CommandSender::class,
		allowFromProxy,
		CommandOnlyUsableByOtherCalleeTypeExceptionType,
		scope,
		executor
	)

	/**
	 * Executes the command up to this point as the given executor.
	 *
	 * This only accept [Player] callees.
	 */
	fun executesPlayerSync(
		allowFromProxy: Boolean = true,
		executor: SyncSuCraftCommandExecutor<Player>
	) = executesSync(
		Player::class,
		allowFromProxy,
		CommandOnlyUsableByPlayerExceptionType,
		executor
	)

	/**
	 * The same as [executesPlayerSync], but execution will be asynchronous by the given [scope].
	 */
	fun executesPlayerAsync(
		allowFromProxy: Boolean = true,
		scope: CoroutineScope = bukkitScope,
		executor: AsyncSuCraftCommandExecutor<Player>
	) = executesAsync(
		Player::class,
		allowFromProxy,
		CommandOnlyUsableByPlayerExceptionType,
		scope,
		executor
	)

}