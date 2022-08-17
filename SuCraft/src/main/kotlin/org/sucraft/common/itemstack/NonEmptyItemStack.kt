/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.itemstack

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

/**
 * A value class for [ItemStack] for which we know it is not empty
 * (i.e. it is not of the type [Material.AIR] and the amount is strictly positive).
 */
@JvmInline
value class NonEmptyItemStack(val itemStack: ItemStack)