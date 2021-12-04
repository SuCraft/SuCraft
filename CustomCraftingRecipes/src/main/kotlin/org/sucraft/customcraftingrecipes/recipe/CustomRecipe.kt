/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.customcraftingrecipes.recipe

import org.bukkit.Bukkit
import org.bukkit.inventory.Recipe


abstract class CustomRecipe {

	// Initialization (register the recipe)

	init {
		Bukkit.addRecipe(getBukkitRecipe())
	}

	// Interface

	abstract fun getBukkitRecipe(): Recipe

}