/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.command

import com.mojang.brigadier.Message
import com.mojang.brigadier.exceptions.CommandExceptionType
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import io.papermc.paper.brigadier.PaperBrigadier
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component

/**
 * A [CommandSyntaxException] for which the [CommandSyntaxException.message] is based on a [Component].
 */
class ComponentCommandSyntaxException(
	type: CommandExceptionType,
	val messageComponent: Component,
	message: Message = PaperBrigadier.message(messageComponent)
) : CommandSyntaxException(
	type,
	message
)

/**
 * @return The message of this [CommandSyntaxException] as a [Component].
 * This is extra efficient if this exception is a [ComponentCommandSyntaxException], since then the original
 * component is used.
 */
fun CommandSyntaxException.getMessageAsComponent() =
	if (this is ComponentCommandSyntaxException)
		messageComponent
	else
		PaperBrigadier.componentFromMessage(rawMessage)

/**
 * A convenience extension to send the [message][getMessageAsComponent] of the exception
 * to this [Audience].
 */
fun Audience.sendMessage(e: CommandSyntaxException) =
	sendMessage(e.getMessageAsComponent())

/**
 * A version of [SimpleCommandExceptionType] that works with [Component] messages directly.
 */
open class SimpleComponentCommandSyntaxExceptionType(
	private val typeString: String,
	val messageComponent: Component,
	val message: Message = PaperBrigadier.message(messageComponent)
) {

	private val internalType = object : CommandExceptionType {

		override fun toString() = typeString

	}

	fun create() =
		ComponentCommandSyntaxException(internalType, messageComponent, message)

	@Throws(ComponentCommandSyntaxException::class)
	fun doThrow() {
		throw create()
	}

}