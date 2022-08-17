/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.itemstack.meta

import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

/**
 * @return This [ItemStack].
 * @see ItemMeta.withLore
 */
fun ItemStack.withLore(operation: MutableList<Component>.() -> Unit): ItemStack {
	(lore() ?: ArrayList(1))
		.also(operation)
		.also(::lore)
	return this
}

/**
 * @return This [ItemStack].
 * @see ItemMeta.addLore
 */
fun ItemStack.addLore(vararg lines: Component) = addLore(lines.toList())

/**
 * @return This [ItemStack].
 * @see ItemMeta.addLore
 */
fun ItemStack.addLore(lines: Collection<Component>) = withLore { addAll(lines) }

/**
 * @return This [ItemStack].
 * @see ItemMeta.addLore
 */
fun ItemStack.addLore(index: Int, vararg lines: Component) = addLore(index, lines.toList())

/**
 * @return This [ItemStack].
 * @see ItemMeta.addLore
 */
fun ItemStack.addLore(index: Int, lines: Collection<Component>) = withLore { addAll(index, lines) }

/**
 * @see ItemMeta.displayName
 */
fun ItemStack.displayName(displayName: Component) = withMeta { displayName(displayName) }

/**
 * @see ItemMeta.hasCustomModelData
 */
val ItemStack.hasCustomModelData
	get() = withMeta { hasCustomModelData() }

/**
 * @see ItemMeta.getCustomModelData
 * @see ItemMeta.setCustomModelData
 */
var ItemStack.customModelData: Int?
	get() = runMeta { if (hasCustomModelData()) customModelData else null }
	set(value) = runMeta { setCustomModelData(value) }