/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.command.node

import com.mojang.brigadier.exceptions.CommandExceptionType
import dev.jorel.commandapi.ArgumentTree
import dev.jorel.commandapi.arguments.*
import kotlinx.coroutines.*
import kotlinx.coroutines.future.asCompletableFuture
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.sucraft.common.command.ComponentCommandSyntaxException
import org.sucraft.common.command.execution.AsyncSuCraftCommandContextArgument
import org.sucraft.common.command.execution.SuCraftCommandContextArgument
import org.sucraft.common.command.execution.SyncSuCraftCommandContextArgument
import org.sucraft.common.concurrent.ioAsync
import org.sucraft.common.player.getOnlinePlayer
import org.sucraft.common.text.NOT_FOUND_ERROR
import org.sucraft.common.text.NOT_FOUND_ERROR_FOCUS
import org.sucraft.common.text.patternAndCompile
import org.sucraft.common.text.times
import org.sucraft.modules.offlineplayerinformation.DetailedOfflinePlayer
import org.sucraft.modules.offlineplayerinformation.OfflinePlayerInformation
import org.sucraft.modules.offlineplayerinformation.OfflinePlayerInformation.getDetailedOfflinePlayer
import org.sucraft.modules.offlineplayerinformation.OfflinePlayerInformation.hasDetailedOfflinePlayer
import java.util.*

// Exception types

object NoPlayerWithNameWasFoundExceptionType {

	private val type = object : CommandExceptionType {

		override fun toString() = "NO_PLAYER_WITH_NAME_WAS_FOUND_TYPE"

	}

	fun doThrow(input: String): Nothing {
		throw ComponentCommandSyntaxException(
			type,
			patternAndCompile(
				338495001928333384L, NOT_FOUND_ERROR,
				input
			) {
				+"No player named" + NOT_FOUND_ERROR_FOCUS * variable + "was found."
			}
		)
	}

}

object NoPlayerWithUUIDWasFoundExceptionType {

	private val type = object : CommandExceptionType {

		override fun toString() = "NO_PLAYER_WITH_UUID_WAS_FOUND_TYPE"

	}

	fun doThrow(input: UUID): Nothing {
		throw ComponentCommandSyntaxException(
			type,
			patternAndCompile(
				483990789442887013L, NOT_FOUND_ERROR,
				input
			) {
				+"No player with UUID" + NOT_FOUND_ERROR_FOCUS * variable + "was found."
			}
		)
	}

}

// Default argument names

private const val defaultPlayerArgumentName = "player"

private const val defaultUUIDArgumentName = "player"

// Resolvers

@Throws(ComponentCommandSyntaxException::class)
suspend fun resolveOfflinePlayerByInputName(name: String) =
	name.getDetailedOfflinePlayer()
		?: NoPlayerWithNameWasFoundExceptionType.doThrow(name)

@Throws(ComponentCommandSyntaxException::class)
fun resolveOnlinePlayerByInputUUID(uuid: UUID) =
	uuid.getOnlinePlayer()
		?: NoPlayerWithUUIDWasFoundExceptionType.doThrow(uuid)

@Throws(ComponentCommandSyntaxException::class)
suspend fun resolveOfflinePlayerByInputUUID(uuid: UUID) =
	uuid.takeIf { it.hasDetailedOfflinePlayer() }
		?: NoPlayerWithUUIDWasFoundExceptionType.doThrow(uuid)

// Arguments

private fun offlinePlayerArgument(name: String) = StringArgument(name)
	.replaceSuggestions(
		ArgumentSuggestions.stringsAsync {
			// TODO cache array instead of using toTypedArray every time
			ioAsync {
				withContext(NonCancellable) {
					OfflinePlayerInformation.getAllUsedNames().toTypedArray()
				}
			}.asCompletableFuture()
		}
	)

private fun onlinePlayerUUIDArgument(name: String) = UUIDArgument(name)
	.replaceSuggestions(
		ArgumentSuggestions.strings {
			Bukkit.getOnlinePlayers().map { "${it.uniqueId}" }.toTypedArray()
		}
	)

private fun offlinePlayerUUIDArgument(name: String) = UUIDArgument(name)
	.replaceSuggestions(
		ArgumentSuggestions.stringsAsync {
			// TODO cache array instead of using toTypedArray every time
			ioAsync {
				withContext(NonCancellable) {
					OfflinePlayerInformation.getAllUsedUUIDs().map(UUID::toString).toTypedArray()
				}
			}.asCompletableFuture()
		}
	)

// Argument values can be acquired in this way

sealed class ResolvableArgument<CommandAPIT : Any, T>(
	/**
	 * A default value for this resolvable argument, when CommandAPI will not count this as input:
	 * a notable example is the LiteralArgument, which is a subclass of Argument<String>, but will not
	 * be included in the args provided by CommandAPI to our instance of NativeCommandExecutor that we
	 * registered for the command.
	 *
	 * This value itself can never be null, but null must be passed for this parameter if this
	 * is irrelevant because CommandAPI will provide some value for this argument in the args passed to the
	 * NativeCommandExecutor.
	 */
	private val defaultValueNotProvidedByCommandAPI: CommandAPIT? = null
) {

	/**
	 * Will be initialized upon command execution.
	 */
	protected var _commandAPIValue: CommandAPIT? = null

	fun setCommandAPIValue(arg: Any?) {
		@Suppress("UNCHECKED_CAST")
		_commandAPIValue = arg as CommandAPIT
	}

	fun setCommandAPIValueByDefaultValue() {
		_commandAPIValue = defaultValueNotProvidedByCommandAPI as CommandAPIT
	}

	val hasDefaultValueNotProvidedByCommandAPI
		get() = defaultValueNotProvidedByCommandAPI != null

	abstract fun toContextArgument(index: Int, input: Any?): SuCraftCommandContextArgument<CommandAPIT, T>

}

class SyncResolvableArgument<CommandAPIT : Any, T>(
	val resolve: (CommandAPIT) -> T,
	defaultValueNotProvidedByCommandAPI: CommandAPIT? = null
) : ResolvableArgument<CommandAPIT, T>(defaultValueNotProvidedByCommandAPI) {

	override fun toContextArgument(index: Int, input: Any?) =
		@Suppress("UNCHECKED_CAST")
		SyncSuCraftCommandContextArgument(this, index, input as CommandAPIT)

}

class AsyncResolvableArgument<CommandAPIT : Any, T>(
	private val resolve: suspend (CommandAPIT) -> T,
	defaultValueNotProvidedByCommandAPI: CommandAPIT? = null
) : ResolvableArgument<CommandAPIT, T>(defaultValueNotProvidedByCommandAPI) {

	fun createDeferred(executionScope: CoroutineScope, commandAPIValue: CommandAPIT) =
		executionScope.async {
			resolve(commandAPIValue)
		}

	override fun toContextArgument(index: Int, input: Any?) =
		@Suppress("UNCHECKED_CAST")
		AsyncSuCraftCommandContextArgument(this, index, input as CommandAPIT)

}

class R

// Type alias for add functionality function

typealias SyncAddArgumentFunctionality<T, CommandAPIA> =
		SuCraftCommandNodeByArgument<CommandAPIA, *, *>.
			(SyncResolvableArgument<*, T>) -> SuCraftCommandNodeByArgumentTree<*>

typealias AsyncAddArgumentFunctionality<T, CommandAPIA> =
		SuCraftCommandNodeByArgument<CommandAPIA, *, *>.
			(AsyncResolvableArgument<*, T>) -> SuCraftCommandNodeByArgumentTree<*>

// Class

/**
 * @param E The type returned from the [executes] method and its derived methods.
 */
interface SuCraftCommandArgumentableNode<E : SuCraftCommandArgumentableNode<E>> : SuCraftCommandNode<E> {

	// Implementation details (do not need to be called from outside)

	fun SuCraftCommandArgumentableNode<E>.thenInternally(
		commandAPIArgumentTree: ArgumentTree
	): E

	// Provided functionality

	private fun <CommandAPIT : Any, CommandAPIA : Argument<CommandAPIT>>
			getDefaultValueNotProvidedByCommandAPIForCommandAPIArgument(
		commandAPIArgument: CommandAPIA
	): CommandAPIT? =
		if (commandAPIArgument is LiteralArgument)
			@Suppress("UNCHECKED_CAST")
			commandAPIArgument.literal as CommandAPIT
		else null

	/**
	 * Adds a child node for an argument.
	 */
	fun <CommandAPIT : Any, T, CommandAPIA : Argument<CommandAPIT>> thenSync(
		commandAPIArgument: CommandAPIA,
		resolve: (CommandAPIT) -> T,
		addFunctionality: SyncAddArgumentFunctionality<T, CommandAPIA>
	): E {
		val resolvableArgument = SyncResolvableArgument(
			resolve,
			getDefaultValueNotProvidedByCommandAPIForCommandAPIArgument(commandAPIArgument)
		)
		return thenInternally(
			FinalSuCraftCommandNodeByArgument(this, commandAPIArgument, resolvableArgument)
				.run { addFunctionality(resolvableArgument) }
				.commandAPIArgumentTree
		)
	}

	/**
	 * Adds a child node for an argument.
	 *
	 * The argument resolution will be asynchronous.
	 */
	fun <CommandAPIT : Any, T, CommandAPIA : Argument<CommandAPIT>> thenAsync(
		commandAPIArgument: CommandAPIA,
		resolve: suspend (CommandAPIT) -> T,
		addFunctionality: AsyncAddArgumentFunctionality<T, CommandAPIA>
	): E {
		val resolvableArgument = AsyncResolvableArgument(
			resolve,
			getDefaultValueNotProvidedByCommandAPIForCommandAPIArgument(commandAPIArgument)
		)
		return thenInternally(
			FinalSuCraftCommandNodeByArgument(this, commandAPIArgument, resolvableArgument)
				.run { addFunctionality(resolvableArgument) }
				.commandAPIArgumentTree
		)
	}

	/**
	 * Adds a child node for a [LiteralArgument]
	 * (a string literal that must be provided exactly).
	 */
	fun thenLiteralSync(
		literal: String,
		addFunctionality: SyncAddArgumentFunctionality<Unit, LiteralArgument>
	) = thenSync(
		LiteralArgument(literal),
		{},
		addFunctionality
	)

	/**
	 * The same as [thenLiteralSync], but argument resolution will be asynchronous.
	 */
	fun thenLiteralAsync(
		literal: String,
		addFunctionality: AsyncAddArgumentFunctionality<Unit, LiteralArgument>
	) = thenAsync(
		LiteralArgument(literal),
		{},
		addFunctionality
	)

	/**
	 * Adds a child node for a [StringArgument]
	 * (a string of alphanumeric characters and underscores only).
	 */
	fun thenStringSync(
		name: String,
		addFunctionality: SyncAddArgumentFunctionality<String, StringArgument>
	) = thenSync(
		StringArgument(name),
		{ it },
		addFunctionality
	)

	/**
	 * The same as [thenStringSync], but argument resolution will be asynchronous.
	 */
	fun thenStringAsync(
		name: String,
		addFunctionality: AsyncAddArgumentFunctionality<String, StringArgument>
	) = thenAsync(
		StringArgument(name),
		{ it },
		addFunctionality
	)

	/**
	 * Adds a child node for a [TextArgument]
	 * (a string of alphanumeric characters and underscores only, unless escaped with quotes).
	 */
	fun thenTextSync(
		name: String,
		addFunctionality: SyncAddArgumentFunctionality<String, TextArgument>
	) = thenSync(
		TextArgument(name),
		{ it },
		addFunctionality
	)

	/**
	 * The same as [thenTextSync], but argument resolution will be asynchronous.
	 */
	fun thenTextAsync(
		name: String,
		addFunctionality: AsyncAddArgumentFunctionality<String, TextArgument>
	) = thenAsync(
		TextArgument(name),
		{ it },
		addFunctionality
	)

	/**
	 * Adds a child node for a [GreedyStringArgument].
	 */
	fun thenGreedyStringSync(
		name: String,
		addFunctionality: SyncAddArgumentFunctionality<String, GreedyStringArgument>
	) = thenSync(
		GreedyStringArgument(name),
		{ it },
		addFunctionality
	)

	/**
	 * The same as [thenGreedyStringSync], but argument resolution will be asynchronous.
	 */
	fun thenGreedyStringAsync(
		name: String,
		addFunctionality: AsyncAddArgumentFunctionality<String, GreedyStringArgument>
	) = thenAsync(
		GreedyStringArgument(name),
		{ it },
		addFunctionality
	)

	/**
	 * Adds a child node for an argument for online players.
	 */
	fun thenOnlinePlayerSync(
		name: String = defaultPlayerArgumentName,
		addFunctionality: SyncAddArgumentFunctionality<Player, Argument<Player>>
	) = thenSync(
		EntitySelectorArgument(name, EntitySelector.ONE_PLAYER),
		{ it },
		addFunctionality
	)

	/**
	 * The same as [thenOnlinePlayerSync], but argument resolution will be asynchronous.
	 */
	fun thenOnlinePlayerAsync(
		name: String = defaultPlayerArgumentName,
		addFunctionality: AsyncAddArgumentFunctionality<Player, Argument<Player>>
	) = thenAsync(
		EntitySelectorArgument(name, EntitySelector.ONE_PLAYER),
		{ it },
		addFunctionality
	)

	/**
	 * Adds a child node for an argument for offline player names.
	 */
	fun thenOfflinePlayerSync(
		name: String = defaultPlayerArgumentName,
		addFunctionality: SyncAddArgumentFunctionality<DetailedOfflinePlayer, Argument<String>>
	) = thenSync(
		offlinePlayerArgument(name),
		{
			runBlocking { resolveOfflinePlayerByInputName(it) }
		},
		addFunctionality
	)

	/**
	 * The same as [thenOfflinePlayerSync], but argument resolution will be asynchronous.
	 */
	fun thenOfflinePlayerAsync(
		name: String = defaultPlayerArgumentName,
		addFunctionality: AsyncAddArgumentFunctionality<DetailedOfflinePlayer, Argument<String>>
	) = thenAsync(
		offlinePlayerArgument(name),
		::resolveOfflinePlayerByInputName,
		addFunctionality
	)

	/**
	 * Adds a child node for an argument for UUIDs.
	 */
	fun thenUUIDSync(
		name: String = defaultUUIDArgumentName,
		addFunctionality: SyncAddArgumentFunctionality<UUID, Argument<UUID>>
	) = thenSync(
		UUIDArgument(name),
		{ it },
		addFunctionality
	)

	/**
	 * The same as [thenUUIDSync], but argument resolution will be asynchronous.
	 */
	fun thenUUIDAsync(
		name: String = defaultUUIDArgumentName,
		addFunctionality: AsyncAddArgumentFunctionality<UUID, Argument<UUID>>
	) = thenAsync(
		UUIDArgument(name),
		{ it },
		addFunctionality
	)

	/**
	 * Adds a child node for an argument for UUIDs belonging to online players.
	 */
	fun thenOnlinePlayerUUIDSync(
		name: String = defaultUUIDArgumentName,
		addFunctionality: SyncAddArgumentFunctionality<Player, Argument<UUID>>
	) = thenSync(
		onlinePlayerUUIDArgument(name),
		::resolveOnlinePlayerByInputUUID,
		addFunctionality
	)

	/**
	 * The same as [thenOnlinePlayerUUIDSync], but argument resolution will be asynchronous.
	 */
	fun thenOnlinePlayerUUIDAsync(
		name: String = defaultUUIDArgumentName,
		addFunctionality: AsyncAddArgumentFunctionality<Player, Argument<UUID>>
	) = thenAsync(
		onlinePlayerUUIDArgument(name),
		::resolveOnlinePlayerByInputUUID,
		addFunctionality
	)

	/**
	 * Adds a child node for an argument for offline player names.
	 */
	fun thenOfflinePlayerUUIDSync(
		name: String = defaultUUIDArgumentName,
		addFunctionality: SyncAddArgumentFunctionality<UUID, Argument<UUID>>
	) = thenSync(
		offlinePlayerUUIDArgument(name),
		{
			runBlocking { resolveOfflinePlayerByInputUUID(it) }
		},
		addFunctionality
	)

	/**
	 * The same as [thenOfflinePlayerUUIDSync], but argument resolution will be asynchronous.
	 */
	fun thenOfflinePlayerUUIDAsync(
		name: String = defaultUUIDArgumentName,
		addFunctionality: AsyncAddArgumentFunctionality<UUID, Argument<UUID>>
	) = thenAsync(
		offlinePlayerUUIDArgument(name),
		::resolveOfflinePlayerByInputUUID,
		addFunctionality
	)

}