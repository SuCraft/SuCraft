/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.bukkit.inventory

import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack


fun Inventory.setNullableContents(contents: Array<ItemStack?>) {
	setContents(contents.map { it ?: ItemStack(Material.AIR, 0) }.toTypedArray())
}