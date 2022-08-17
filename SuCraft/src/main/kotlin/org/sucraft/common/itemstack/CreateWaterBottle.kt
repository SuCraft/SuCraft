/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.itemstack

import org.bukkit.Material.POTION
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionData
import org.bukkit.potion.PotionType.WATER
import org.sucraft.common.itemstack.meta.potion.basePotionDate

fun createWaterBottle(amount: Int = 1) =
	ItemStack(POTION, amount).apply {
		basePotionDate = PotionData(WATER)
	}