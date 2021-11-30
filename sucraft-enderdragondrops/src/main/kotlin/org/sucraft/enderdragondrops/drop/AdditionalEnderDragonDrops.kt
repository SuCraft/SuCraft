/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.enderdragondrops.drop

import org.bukkit.Material
import org.bukkit.inventory.ItemStack


object AdditionalEnderDragonDrops {

	class DropWithProbability(val item: ItemStack, val probability: Double) {

		fun evaluateProbability() = Math.random() < probability

	}

	val drops = arrayOf(
		DropWithProbability(ItemStack(Material.ELYTRA), 1.0 / 20)
	)

}