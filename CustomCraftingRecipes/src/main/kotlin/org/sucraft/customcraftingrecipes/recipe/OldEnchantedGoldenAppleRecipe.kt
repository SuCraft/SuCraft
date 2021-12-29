/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.customcraftingrecipes.recipe

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe


object OldEnchantedGoldenAppleRecipe : CustomRecipe() {

	// Settings

	@Suppress("DEPRECATION")
	private val recipeNamespacedKey = NamespacedKey("martijnsrecipes", "enchanted_golden_apple_recipe")

	// Implementation

	override fun getBukkitRecipe() =
		ShapedRecipe(recipeNamespacedKey, ItemStack(Material.ENCHANTED_GOLDEN_APPLE)).also {
			it.shape("$$$", "$%$", "$$$")
			it.setIngredient('$', Material.GOLD_BLOCK)
			it.setIngredient('%', Material.APPLE)
		}

	// Initialization

	init { registerWithBukkit() }

}