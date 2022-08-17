/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.itemstack.meta

import net.kyori.adventure.text.Component
import org.bukkit.inventory.meta.ItemMeta

/**
 * @return This [ItemMeta].
 */
fun ItemMeta.withLore(operation: MutableList<Component>.() -> Unit): ItemMeta {
	(lore() ?: ArrayList(1))
		.also(operation)
		.also(::lore)
	return this
}

/**
 * @return This [ItemMeta].
 */
fun ItemMeta.addLore(vararg lines: Component) = addLore(lines.toList())

/**
 * @return This [ItemMeta].
 */
fun ItemMeta.addLore(lines: Collection<Component>) = withLore { addAll(lines) }

/**
 * @return This [ItemMeta].
 */
fun ItemMeta.addLore(index: Int, vararg lines: Component) = addLore(index, lines.toList())

/**
 * @return This [ItemMeta].
 */
fun ItemMeta.addLore(index: Int, lines: Collection<Component>) = withLore { addAll(index, lines) }