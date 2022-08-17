/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.itemstack.meta.potion

import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionEffect
import org.sucraft.common.itemstack.meta.runMeta
import org.sucraft.common.itemstack.meta.withMeta

var ItemStack.potionColor
	/**
	 * @throws IllegalArgumentException If the meta is not an instance of [PotionMeta].
	 */
	@Throws(IllegalArgumentException::class)
	get() = runMeta(PotionMeta::class) { color }
	/**
	 * @throws IllegalArgumentException If the meta is not an instance of [PotionMeta].
	 */
	@Throws(IllegalArgumentException::class)
	set(value) {
		withMeta(PotionMeta::class) { color = value }
	}

var ItemStack.basePotionDate
	/**
	 * @throws IllegalArgumentException If the meta is not an instance of [PotionMeta].
	 */
	@Throws(IllegalArgumentException::class)
	get() = runMeta(PotionMeta::class) { basePotionData }
	/**
	 * @throws IllegalArgumentException If the meta is not an instance of [PotionMeta].
	 */
	@Throws(IllegalArgumentException::class)
	set(value) {
		withMeta(PotionMeta::class) { basePotionData = value }
	}

/**
 * @throws IllegalArgumentException If the meta is not an instance of [PotionMeta].
 */
@Throws(IllegalArgumentException::class)
fun ItemStack.addCustomPotionEffect(potionEffect: PotionEffect, overwrite: Boolean) =
	withMeta(PotionMeta::class) { addCustomEffect(potionEffect, overwrite) }

val ItemStack.customPotionEffects
	/**
	 * @throws IllegalArgumentException If the meta is not an instance of [PotionMeta].
	 */
	@Throws(IllegalArgumentException::class)
	get(): List<PotionEffect> = runMeta(PotionMeta::class) { customEffects }