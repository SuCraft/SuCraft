/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.text

import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor.GRAY
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextColor.color

// Text colors

val QUOTE_TEXT: TextColor = GRAY
val QUOTE_AUTHOR = color(0xD8D8D8)
val QUOTE_SEPARATOR = color(0x888888)

// Class

/**
 * Contains a quote as used by [HomeQuotes] and [DeathQuotes].
 */
data class Quote(
	val text: String,
	val author: String? = null
) {

	val component by lazy {
		author?.let {
			component {
				QUOTE_TEXT * text + QUOTE_SEPARATOR * "~" - QUOTE_AUTHOR * author
			}
		} ?: text(text, QUOTE_TEXT)
	}

}

/**
 * Syntactic sugar to define quotes with an author.
 */
infix fun String.by(author: String) = Quote(this, author)

/**
 * @param elements Either instances of [String] or [Quote].
 * @return An array of the given elements,
 * with each [String] turned to a [Quote] with the element as its [Quote.text] property.
 * @throws IllegalArgumentException if any element in [elements] if not a [String] or [Quote].
 */
@Throws(IllegalArgumentException::class)
fun quoteArrayOf(vararg elements: Any) =
	elements.map {
		when (it) {
			is String -> Quote(it)
			is Quote -> it
			else -> throw java.lang.IllegalArgumentException(
				"quoteArrayOf accepts only elements of types String and Quote"
			)
		}
	}.toTypedArray()