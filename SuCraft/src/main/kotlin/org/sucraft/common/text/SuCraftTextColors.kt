/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.text

import net.kyori.adventure.text.format.NamedTextColor.*
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextColor.color

/**
 * Color for errors after an interaction where a user intended a specific custom action
 * (such as a command) where nothing happened as a result (and so it should be immediately clear to the user
 * that the thing they intended was not achieved).
 */
val ERROR_NOTHING_HAPPENED: TextColor = RED
val ERROR_NOTHING_HAPPENED_FOCUS: TextColor = WHITE
val ERROR_NOTHING_HAPPENED_EXTRA_DETAIL: TextColor = GRAY

/**
 * Color for syntax errors in commands or text input.
 */
val SYNTAX_ERROR: TextColor = ERROR_NOTHING_HAPPENED
val SYNTAX_ERROR_FOCUS: TextColor = ERROR_NOTHING_HAPPENED_FOCUS

/**
 * Color for commands or text input where no relevant target was found for the input (such as a player by name),
 * and therefore nothing happened.
 */
val NOT_FOUND_ERROR: TextColor = ERROR_NOTHING_HAPPENED
val NOT_FOUND_ERROR_FOCUS: TextColor = ERROR_NOTHING_HAPPENED_FOCUS

/**
 * Color for after an interaction where a user intended a relatively boring specific custom action
 * (such as a command) and the intended result happened (and so it should be immediately clear to the user
 * that the thing they intended was achieved).
 */
val BORING_SUCCESS: TextColor = color(0xCECECE)
val BORING_SUCCESS_FOCUS: TextColor = WHITE
val BORING_SUCCESS_EXTRA_DETAIL: TextColor = GRAY

/**
 * Color for interesting events.
 */
val INTERESTING_EVENT: TextColor = WHITE

/**
 * Color for messages that are informative but should not catch attention.
 */
val INFORMATIVE_UNIMPORTANT: TextColor = GRAY
val INFORMATIVE_UNIMPORTANT_FOCUS: TextColor = color(0xCECECE)

/**
 * Discord brand color.
 */
val DISCORD: TextColor = color(0x5865F2)