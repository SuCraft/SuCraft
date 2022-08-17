/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.craftablebarriersandlights

import org.bukkit.permissions.PermissionDefault
import org.sucraft.common.module.SuCraftModule

/**
 * Adds crafting recipes for barriers and lights.
 */
object CraftableBarriersAndLights : SuCraftModule<CraftableBarriersAndLights>() {

	// Permissions

	object Permissions {

		val craftBarrier = permission(
			"craft.barrier",
			"Craft barriers",
			PermissionDefault.TRUE
		)
		val craftLight = permission(
			"craft.light",
			"Craft lights",
			PermissionDefault.TRUE
		)

	}

	// Recipes

	override val customRecipes by lazy {
		listOf(
			barrierCraftingRecipe,
			lightCraftingRecipe
		)
	}

}