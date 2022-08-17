/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.itemstack

import org.bukkit.Material.AIR
import org.bukkit.inventory.ItemStack

val ItemStack?.isEmpty
	get() = this == null || this.type == AIR || this.amount == 0

val ItemStack?.isNotEmpty
	get() = this != null && this.type != AIR && this.amount != 0

fun ItemStack.takeAsNotEmpty() = takeIfNotEmpty()?.let(::NonEmptyItemStack)

fun ItemStack.takeIfNotEmpty() = takeIf { isNotEmpty }