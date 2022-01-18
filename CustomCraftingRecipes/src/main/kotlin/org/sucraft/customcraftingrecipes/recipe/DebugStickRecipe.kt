/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.customcraftingrecipes.recipe

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapelessRecipe
import org.sucraft.customcraftingrecipes.main.SuCraftCustomCraftingRecipesPlugin


object DebugStickRecipe : CustomRecipe() {

	// Settings

	private val recipeNamespacedKey = SuCraftCustomCraftingRecipesPlugin.getInstance().getNamespacedKey("debug_stick_recipe");

	// Implementation

	override fun getBukkitRecipe() =
		ShapelessRecipe(recipeNamespacedKey, ItemStack(Material.DEBUG_STICK)).also {
			it.addIngredient(Material.STICK)
			it.addIngredient(Material.LAPIS_LAZULI)
		}

	// Initialization

	init { registerWithBukkit() }

}