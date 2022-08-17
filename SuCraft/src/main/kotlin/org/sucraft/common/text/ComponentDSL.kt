/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.text

import com.mojang.brigadier.Message
import io.papermc.paper.brigadier.PaperBrigadier
import it.unimi.dsi.fastutil.longs.Long2ObjectMap
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.Component.*
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.text.JoinConfiguration
import net.kyori.adventure.text.JoinConfiguration.noSeparators
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.sucraft.common.function.runIfNotNull
import org.sucraft.common.text.ComponentPattern.Companion.SPACE
import java.util.function.LongFunction

fun valueToComponent(value: Any?, variables: Array<out Any?>? = null) = when (value) {
	null -> text("null")
	is ComponentLike -> value.asComponent()
	is ComponentPattern -> value.compile(variables)
	is Byte -> text(value.toInt())
	is Short -> text(value.toInt())
	is Int -> text(value)
	is Long -> text(value)
	is Float -> text(value)
	is Double -> text(value)
	is Boolean -> text(value)
	is Char -> text(value)
	is String -> text(value)
	else -> text("$value")
}

fun valueToComponentPattern(value: Any?) =
	if (value is ComponentPattern)
		value
	else SingleComponentPattern(valueToComponent(value))

interface ComponentPattern {

	val isStaticWithoutCompression: Boolean

	fun compress(): ComponentPattern

	fun compile(variables: Array<out Any?>?): Component

	fun color(color: TextColor?) = ProcessedComponentPattern(this) {
		it.color(color)
	}

	fun colorIfAbsent(color: TextColor?) = ProcessedComponentPattern(this) {
		it.colorIfAbsent(color)
	}

	fun decorate(decoration: TextDecoration) = ProcessedComponentPattern(this) {
		it.decorate(decoration)
	}

	fun decoration(decoration: TextDecoration, state: Boolean) = ProcessedComponentPattern(this) {
		it.decoration(decoration, state)
	}

	fun nonItalic() = ProcessedComponentPattern(this) {
		it.nonItalic()
	}

	companion object {

		val SPACE = SingleComponentPattern(space())

	}

}

class SingleComponentPattern(
	private val component: Component
) : ComponentPattern, ComponentLike {

	override val isStaticWithoutCompression = true

	override fun compress() = this

	override fun compile(variables: Array<out Any?>?) = component

	override fun asComponent() = component

}

class VariableComponentPattern(
	private val variableIndex: Int
) : ComponentPattern {

	override val isStaticWithoutCompression = false

	override fun compress() = this

	override fun compile(variables: Array<out Any?>?) = valueToComponent(variables!![variableIndex], variables)

}

class JoinedComponentPattern(
	private val joinConfiguration: JoinConfiguration,
	initialElements: Collection<ComponentPattern>
) : ComponentPattern {

	constructor(
		joinConfiguration: JoinConfiguration,
		elements: Array<ComponentPattern>
	) : this(
		joinConfiguration,
		elements.toList()
	)

	val elements = ArrayList(initialElements)

	override val isStaticWithoutCompression = false

	override fun compress(): ComponentPattern {
		if (elements.size == 1)
			return elements[0].compress()
		for (index in elements.indices) {
			elements[index] = elements[index].compress()
		}
		if (elements.all { it.isStaticWithoutCompression }) {
			return SingleComponentPattern(compile(null))
		}
		elements.trimToSize()
		return this
	}

	override fun compile(variables: Array<out Any?>?) =
		join(
			joinConfiguration,
			elements.map { it.compile(variables) }
		)

	// Post-construction addition operator overloads:
	// - is concatenation without a separator
	// + is concatenation with a single space as separator

	operator fun minus(newValue: Any?) =
		if (newValue is JoinedComponentPattern)
			minusJoinedComponentPattern(newValue)
		else
			minusAnyValue(newValue)

	operator fun minus(other: JoinedComponentPattern) =
		minusJoinedComponentPattern(other)

	private fun minusAnyValue(newValue: Any?) =
		if (joinConfiguration == noSeparators()) apply {
			elements.add(valueToComponentPattern(newValue))
		} else JoinedComponentPattern(noSeparators(), listOf(this, valueToComponentPattern(newValue)))

	private fun minusJoinedComponentPattern(other: JoinedComponentPattern) =
		if (joinConfiguration == noSeparators() && other.joinConfiguration == noSeparators()) apply {
			other.elements.forEach(elements::add)
		} else if (joinConfiguration == noSeparators()) apply {
			elements.add(other)
		} else if (other.joinConfiguration == noSeparators())
			JoinedComponentPattern(noSeparators(), listOf(this) + other.elements)
		else JoinedComponentPattern(noSeparators(), listOf(this, other))

	operator fun plus(newValue: Any?) =
		if (newValue is JoinedComponentPattern)
			plusJoinedComponentPattern(newValue)
		else
			plusAnyValue(newValue)

	operator fun plus(other: JoinedComponentPattern) =
		plusJoinedComponentPattern(other)

	private fun plusAnyValue(newValue: Any?) =
		if (joinConfiguration == noSeparators()) apply {
			if (elements.isNotEmpty())
				elements.add(SPACE)
			elements.add(valueToComponentPattern(newValue))
		} else if (joinConfiguration == spaces) apply {
			elements.add(valueToComponentPattern(newValue))
		} else JoinedComponentPattern(spaces, listOf(this, valueToComponentPattern(newValue)))

	private fun plusJoinedComponentPattern(other: JoinedComponentPattern) =
		if (joinConfiguration == noSeparators() && other.joinConfiguration == noSeparators()) apply {
			if (other.elements.isNotEmpty() && elements.isNotEmpty())
				elements.add(SPACE)
			other.elements.forEach(elements::add)
		} else if (joinConfiguration == spaces && other.joinConfiguration == spaces) apply {
			other.elements.forEach(elements::add)
		} else if (joinConfiguration == spaces) apply {
			elements.add(other)
		} else if (other.joinConfiguration == spaces)
			JoinedComponentPattern(spaces, listOf(this) + other.elements)
		else JoinedComponentPattern(spaces, listOf(this, other))

}

class ProcessedComponentPattern(
	private val subPattern: ComponentPattern,
	private val operation: (Component) -> Component
) : ComponentPattern {

	override val isStaticWithoutCompression = subPattern.isStaticWithoutCompression

	override fun compress() =
		ProcessedComponentPattern(subPattern.compress(), operation).run {
			if (isStaticWithoutCompression)
				SingleComponentPattern(compile(null))
			else this
		}

	override fun compile(variables: Array<out Any?>?) =
		operation(subPattern.compile(variables))

}

operator fun TextColor?.times(value: Any?) =
	valueToComponentPattern(value).colorIfAbsent(this)

operator fun TextDecoration.times(value: Any?) =
	valueToComponentPattern(value).decorate(this)

class ComponentDSLContext {

	var nextVariableIndex: Int = 0

	val variable
		get() = VariableComponentPattern(nextVariableIndex)
			.also { nextVariableIndex++ }

	fun n(vararg values: Any?) =
		n(values.toList())

	fun nl(vararg values: Any?) =
		nl(values.toList())

	fun s(vararg values: Any?) =
		s(values.toList())

	fun c(vararg values: Any?) =
		c(values.toList())

	fun cs(vararg values: Any?) =
		cs(values.toList())

	fun n(values: Iterable<Any?>) =
		JoinedComponentPattern(JoinConfiguration.noSeparators(), values.map(::valueToComponentPattern))

	fun nl(values: Iterable<Any?>) =
		JoinedComponentPattern(JoinConfiguration.newlines(), values.map(::valueToComponentPattern))

	fun s(values: Iterable<Any?>) =
		JoinedComponentPattern(spaces, values.map(::valueToComponentPattern))

	fun c(values: Iterable<Any?>) =
		JoinedComponentPattern(JoinConfiguration.commas(false), values.map(::valueToComponentPattern))

	fun cs(values: Iterable<Any?>) =
		JoinedComponentPattern(JoinConfiguration.commas(true), values.map(::valueToComponentPattern))

	operator fun Component.minus(value: Any?) =
		n(this, value)

	operator fun ComponentPattern.minus(value: Any?) =
		n(this, value)

	operator fun Number.minus(value: Any?) =
		n(this, value)

	operator fun Boolean.minus(value: Any?) =
		n(this, value)

	operator fun Char.minus(value: Any?) =
		n(this, value)

	operator fun Component.plus(value: Any?) =
		n(this, " ", value)

	operator fun ComponentPattern.plus(value: Any?) =
		n(this, " ", value)

	operator fun Number.plus(value: Any?) =
		n(this, " ", value)

	operator fun Boolean.plus(value: Any?) =
		n(this, " ", value)

	operator fun Char.plus(value: Any?) =
		n(this, " ", value)

	operator fun String.unaryPlus() = text(this)

	operator fun Byte.not() = text(toInt())

	operator fun Short.not() = text(toInt())

	operator fun Int.not() = text(this)

	operator fun Long.not() = text(this)

	operator fun Float.not() = text(this)

	operator fun Double.not() = text(this)

	operator fun Boolean.unaryPlus() = text(this)

	operator fun Char.not() = text(this)

}

// Creating uncached component patterns and components
// (These should not be used inline, but only in longer-term variable declarations,
// because the patterns or components are not cached)

/**
 * @param color An optional color to set on the whole component pattern.
 * @param build A function that returns the desired value.
 * This is typically a [ComponentPattern],
 * but may also be a [ComponentLike], primitive, [String] or other value.
 * @return An uncached but compressed [ComponentPattern].
 */
fun pattern(
	color: TextColor? = null,
	build: ComponentDSLContext.() -> Any?
) =
	ComponentDSLContext().run(build)
		.let(::valueToComponentPattern)
		.runIfNotNull(color) { colorIfAbsent(color) }
		.compress()

/**
 * @param color An optional color to set on the whole component.
 * @param build A function that returns the desired value.
 * This is typically a [Component],
 * but may also be a [ComponentPattern], [ComponentLike], primitive, [String] or other value.
 * @return An uncached [Component], without any [variables][VariableComponentPattern].
 */
fun component(
	color: TextColor? = null,
	build: ComponentDSLContext.() -> Any?
) =
	ComponentDSLContext().run(build)
		.let(::valueToComponent)
		.runIfNotNull(color) { colorIfAbsent(color) }

/**
 * @param color An optional color to set on the whole component.
 * @param build A function that returns the desired value.
 * This is typically a [Component],
 * but may also be a [ComponentPattern], [ComponentLike], primitive, [String] or other value.
 * @return An uncached [Message], without any [variables][VariableComponentPattern].
 */
fun message(
	color: TextColor? = null,
	build: ComponentDSLContext.() -> Any?
) =
	PaperBrigadier.message(component(color, build))

// Functions with caching to avoid creating local variables for pre-compressed patterns everywhere

private val cachedPatternsById: Long2ObjectMap<ComponentPattern> = Long2ObjectOpenHashMap(512)
private val cachedComponentsById: Long2ObjectMap<Component> = Long2ObjectOpenHashMap(512)
private val cachedMessagesById: Long2ObjectMap<Message> = Long2ObjectOpenHashMap(512)

/**
 * Creates or retrieves a cached and compressed [ComponentPattern].
 *
 * @param key The key to store the component pattern by in the cache.
 * @param color An optional color to set on the whole component pattern.
 * @param build A function that returns the desired value.
 * This is typically a [ComponentPattern],
 * but may also be a [ComponentLike], primitive, [String] or other value.
 * @return The created or retrieved [ComponentPattern].
 */
fun pattern(
	key: Any,
	color: TextColor? = null,
	build: ComponentDSLContext.() -> Any?
): ComponentPattern =
	cachedPatternsById.computeIfAbsent(when (key) {
		is Long -> key
		is String -> key.longHashCode()
		else -> throw IllegalArgumentException(
			"Tried to get cached ComponentPattern with key of invalid type: $key of type ${key::class.java.name}"
		)
	}, LongFunction {
		pattern(color, build)
	})

/**
 * Creates or retrieves a cached [Component], that does not use any [variables][VariableComponentPattern].
 *
 * @param key The key to store the component by in the cache.
 * @param color An optional color to set on the whole component.
 * @param build A function that returns the desired value.
 * This is typically a [Component],
 * but may also be a [ComponentPattern], [ComponentLike], primitive, [String] or other value.
 * @return The created or retrieved cached [Component].
 */
fun component(
	key: Any,
	color: TextColor? = null,
	build: ComponentDSLContext.() -> Any?
): Component =
	cachedComponentsById.computeIfAbsent(when (key) {
		is Long -> key
		is String -> key.longHashCode()
		else -> throw IllegalArgumentException(
			"Tried to get cached Component with key of invalid type: $key of type ${key::class.java.name}"
		)
	}, LongFunction {
		component(color, build)
	})

/**
 * Creates or retrieves a cached [Message], that does not use any [variables][VariableComponentPattern].
 *
 * @param key The key to store the message by in the cache.
 * @param color An optional color to set on the whole component.
 * @param build A function that returns the desired value.
 * This is typically a [Component],
 * but may also be a [ComponentPattern], [ComponentLike], primitive, [String] or other value.
 * @return The created or retrieved cached [Message].
 */
fun message(
	key: Any,
	color: TextColor? = null,
	build: ComponentDSLContext.() -> Any?
): Message =
	cachedMessagesById.computeIfAbsent(when (key) {
		is Long -> key
		is String -> key.longHashCode()
		else -> throw IllegalArgumentException(
			"Tried to get cached Component with key of invalid type: $key of type ${key::class.java.name}"
		)
	}, LongFunction {
		message(color, build)
	})

/**
 * Creates or retrieves a cached and compressed [ComponentPattern],
 * and then compiles it with the given variable values.
 *
 * @param key The key to store the component pattern by in the cache.
 * @param color An optional color to set on the whole component pattern.
 * @param variables The variables to [compile][ComponentPattern.compile] the pattern with.
 * @param build A function that returns the desired component pattern.
 * This is typically a [ComponentPattern],
 * but may also be a [ComponentLike], primitive, [String] or other value.
 * @return The [Component] resulting from the compilation of the created or retrieved cached [ComponentPattern].
 */
fun patternAndCompile(
	key: Any,
	color: TextColor? = null,
	vararg variables: Any?,
	build: ComponentDSLContext.() -> Any?
): Component =
	pattern(key, color, build).compile(variables)

/**
 * Sends a [component] message to this [audience][Audience],
 * which is the created or retrieved [ComponentPattern], compiled with the given variable values.
 *
 * @param key The key to store the component pattern by in the cache.
 * @param color An optional color to set on the whole component pattern.
 * @param variables The variables to [compile][ComponentPattern.compile] the pattern with.
 * @param build A function that returns the desired component pattern.
 * This is typically a [ComponentPattern],
 * but may also be a [ComponentLike], primitive, [String] or other value.
 */
fun Audience.sendMessage(
	key: Any,
	color: TextColor? = null,
	vararg variables: Any?,
	build: ComponentDSLContext.() -> Any?
) =
	sendMessage(pattern(key, color, build), *variables)

/**
 * Sends a [Component] message to this [audience][Audience],
 * which is the created or retrieved [Component], that does not use any [variables][VariableComponentPattern].
 *
 * @param key The key to store the component by in the cache.
 * @param color An optional color to set on the whole component.
 * @param build A function that returns the desired value.
 * This is typically a [Component],
 * but may also be a [ComponentPattern], [ComponentLike], primitive, [String] or other value.
 */
fun Audience.sendMessage(
	key: Any,
	color: TextColor? = null,
	build: ComponentDSLContext.() -> Any?
) =
	sendMessage(component(key, color, build))

/**
 * Sends a [Component] message to this [audience][Audience],
 * which is the given component pattern, compiled with the given variable values.
 *
 * @param pattern The [ComponentPattern] to compile.
 * @param variables The variables to [compile][ComponentPattern.compile] the pattern with.
 */
fun Audience.sendMessage(pattern: ComponentPattern, vararg variables: Any?) =
	sendMessage(pattern.compile(variables))
