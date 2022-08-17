/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.craftablealcoholicbeverages

import org.bukkit.permissions.PermissionDefault
import org.sucraft.common.module.SuCraftModule

/**
 * Adds crafting recipes for a selection of alcoholic beverages,
 * that give an effect when drunk.
 */
object CraftableAlcoholicBeverages : SuCraftModule<CraftableAlcoholicBeverages>() {

	// Permissions

	object Permissions {
		val craftBeer = permission(
			"craft.beer",
			"Craft beer",
			PermissionDefault.TRUE
		)
		val craftVodka = permission(
			"craft.vodka",
			"Craft vodka",
			PermissionDefault.TRUE
		)
		val craftKvass = permission(
			"craft.kvass",
			"Craft kvass",
			PermissionDefault.TRUE
		)
	}

	// Recipes

	override val customRecipes by lazy {
		listOf(
			beerCraftingRecipe,
			vodkaCraftingRecipe,
			kvassCraftingRecipe
		)
	}

	// Components

	override val components = listOf(
		CraftAlcoholicBeverageListener,
		DrinkAlcoholListener
	)

}