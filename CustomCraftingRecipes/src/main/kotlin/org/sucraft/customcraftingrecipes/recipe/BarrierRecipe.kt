/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.customcraftingrecipes.recipe

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.sucraft.customcraftingrecipes.main.SuCraftCustomCraftingRecipesPlugin


object BarrierRecipe : CustomRecipe() {

	// Settings

	private val recipeNamespacedKey = SuCraftCustomCraftingRecipesPlugin.getInstance().getNamespacedKey("barrier_recipe");

	// Implementation

	override fun getBukkitRecipe() =
		ShapedRecipe(recipeNamespacedKey, ItemStack(Material.BARRIER, 4)).also {
			it.shape("$$$", "$%$", "$$$")
			it.setIngredient('$', Material.RED_WOOL)
			it.setIngredient('%', Material.DIAMOND)
		}

	// Initialization

	init { registerWithBukkit() }

}