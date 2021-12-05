/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.customcraftingrecipes.recipe.alcohol

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ShapelessRecipe
import org.sucraft.core.common.bukkit.item.ItemStackBuilder
import org.sucraft.customcraftingrecipes.recipe.CustomRecipe


object VodkaRecipe : CustomRecipe() {

	// Settings

	@Suppress("DEPRECATION")
	private val recipeNamespacedKey = NamespacedKey("martijnsrecipes", "vodka_recipe")

	// Implementation

	override fun getBukkitRecipe() =
		ShapelessRecipe(recipeNamespacedKey, AlcoholData.vodkaItemStackWithoutBrewer).also {
			it.addIngredient(ItemStackBuilder.createWaterBottle().build());
			it.addIngredient(Material.POTATO);
		}

	// Initialization

	init { registerWithBukkit() }

}