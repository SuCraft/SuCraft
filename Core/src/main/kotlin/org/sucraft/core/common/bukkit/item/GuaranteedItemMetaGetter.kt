/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.bukkit.item

import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

object GuaranteedItemMetaGetter {

	fun get(itemStack: ItemStack, setNewItemMetaBeforeReturn: Boolean = true): ItemMeta {
		// Get the existing meta
		val existingMeta: ItemMeta? = if (itemStack.hasItemMeta()) itemStack.itemMeta else null
		if (existingMeta != null) return existingMeta
		// Make a new meta
		val newItemMeta: ItemMeta = Bukkit.getItemFactory().getItemMeta(itemStack.type)
		if (setNewItemMetaBeforeReturn) itemStack.itemMeta = newItemMeta
		return newItemMeta
	}

}