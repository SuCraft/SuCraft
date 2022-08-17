/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.text

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration

fun Component.italic() = decoration(TextDecoration.ITALIC, true)

fun Component.nonItalic() = decoration(TextDecoration.ITALIC, false)

fun Component.bold() = decoration(TextDecoration.BOLD, true)

fun Component.nonBold() = decoration(TextDecoration.BOLD, false)

fun Component.underlined() = decoration(TextDecoration.UNDERLINED, true)

fun Component.nonUnderlined() = decoration(TextDecoration.UNDERLINED, false)

fun Component.strikethrough() = decoration(TextDecoration.STRIKETHROUGH, true)

fun Component.nonStrikethrough() = decoration(TextDecoration.STRIKETHROUGH, false)