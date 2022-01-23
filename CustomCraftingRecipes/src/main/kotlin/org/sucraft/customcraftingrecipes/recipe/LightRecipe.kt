/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.customcraftingrecipes.recipe

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.sucraft.customcraftingrecipes.main.SuCraftCustomCraftingRecipesPlugin


object LightRecipe : CustomRecipe() {

	// Settings

	private val recipeNamespacedKey = SuCraftCustomCraftingRecipesPlugin.getInstance().getNamespacedKey("light_recipe");

	// Implementation

	override fun getBukkitRecipe() =
		ShapedRecipe(recipeNamespacedKey, ItemStack(Material.LIGHT, 4)).also {
			it.shape("$$$", "$%$", "$$$")
			it.setIngredient('$', Material.GLASS)
			it.setIngredient('%', Material.DIAMOND)
		}

	// Initialization

	init { registerWithBukkit() }

}