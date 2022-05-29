/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.bukkit.item

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object EmptyItemStack {

	@Suppress("NOTHING_TO_INLINE")
	inline fun isEmpty(itemStack: ItemStack?) = itemStack == null || itemStack.type == Material.AIR

	@Suppress("NOTHING_TO_INLINE")
	inline fun isNotEmpty(itemStack: ItemStack?) = itemStack != null && itemStack.type != Material.AIR

}