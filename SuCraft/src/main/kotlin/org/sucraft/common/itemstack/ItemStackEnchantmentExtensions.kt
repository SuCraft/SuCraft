/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.itemstack

import org.bukkit.enchantments.Enchantment.SILK_TOUCH
import org.bukkit.inventory.ItemStack

val ItemStack.hasSilkTouch
	get() = SILK_TOUCH in enchantments.keys