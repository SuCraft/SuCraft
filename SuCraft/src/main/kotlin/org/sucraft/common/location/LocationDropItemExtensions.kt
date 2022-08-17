/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location

import org.bukkit.Location
import org.bukkit.entity.Item
import org.bukkit.inventory.ItemStack

fun Location.dropItem(
	item: ItemStack,
	function: ((Item) -> Unit)? = null
) =
	if (function != null)
		world.dropItem(this, item, function)
	else
		world.dropItem(this, item)

fun Location.dropItemNaturally(
	item: ItemStack,
	function: ((Item) -> Unit)? = null
) =
	if (function != null)
		world.dropItemNaturally(this, item, function)
	else
		world.dropItemNaturally(this, item)