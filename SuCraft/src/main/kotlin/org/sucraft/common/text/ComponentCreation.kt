/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.text

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor.BLACK
import net.kyori.adventure.text.format.NamedTextColor.WHITE
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.inventory.ItemStack
import org.sucraft.common.function.runIfNotNull

/**
 * @return A [Component] with the given [content],
 * specifically styled to not be [italic][TextDecoration.ITALIC].
 *
 * This is useful in [display names][ItemStack.displayName] and [lore][ItemStack.lore],
 * because they are by default displayed as italic if the component is not specifically made non-italic.
 */
fun unstyledText(content: String, color: TextColor? = null, vararg decorations: TextDecoration) =
	text(content)
		.decoration(TextDecoration.ITALIC, false)
		.runIfNotNull(color) { color(color) }
		.decorate(*decorations)

/**
 * @return A [Component] with the given [content],
 * specifically styled to not be [italic][TextDecoration.ITALIC], and be [white][WHITE].
 *
 * This is useful in [display names][ItemStack.displayName] and [lore][ItemStack.lore],
 * because they are by default displayed as italic if the component is not specifically made non-italic.
 */
fun unstyledWhiteText(content: String, vararg decorations: TextDecoration) =
	unstyledText(content)
		.color(WHITE)
		.decorate(*decorations)

/**
 * @return A [Component] with the given [content],
 * specifically styled to not be [italic][TextDecoration.ITALIC], and be [black][BLACK].
 *
 * This is useful in [display names][ItemStack.displayName] and [lore][ItemStack.lore],
 * because they are by default displayed as italic if the component is not specifically made non-italic.
 */
fun unstyledBlackText(content: String, vararg decorations: TextDecoration) =
	unstyledText(content)
		.color(BLACK)
		.decorate(*decorations)