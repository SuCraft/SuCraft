/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.customcraftingrecipes.main

import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.customcraftingrecipes.recipe.BarrierRecipe
import org.sucraft.customcraftingrecipes.recipe.DebugStickRecipe
import org.sucraft.customcraftingrecipes.recipe.LightRecipe
import org.sucraft.customcraftingrecipes.recipe.OldEnchantedGoldenAppleRecipe
import org.sucraft.customcraftingrecipes.recipe.alcohol.BeerRecipe
import org.sucraft.customcraftingrecipes.recipe.alcohol.CraftAlcoholListener
import org.sucraft.customcraftingrecipes.recipe.alcohol.DrinkAlcoholListener
import org.sucraft.customcraftingrecipes.recipe.alcohol.VodkaRecipe

class SuCraftCustomCraftingRecipesPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftCustomCraftingRecipesPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		OldEnchantedGoldenAppleRecipe
		DebugStickRecipe
		BarrierRecipe
		LightRecipe
		DrinkAlcoholListener
		CraftAlcoholListener
		BeerRecipe
		VodkaRecipe
	}

}