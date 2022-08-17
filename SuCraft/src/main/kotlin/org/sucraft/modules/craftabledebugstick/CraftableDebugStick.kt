/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.craftabledebugstick

import org.bukkit.permissions.PermissionDefault
import org.sucraft.common.module.SuCraftModule

/**
 * Adds a crafting recipe for a debug stick.
 */
object CraftableDebugStick : SuCraftModule<CraftableDebugStick>() {

	// Permissions

	object Permissions {

		val craftDebugStick = permission(
			"craft.debugstick",
			"Craft a debug stick",
			PermissionDefault.OP
		)

	}

	// Recipes

	override val customRecipes by lazy {
		listOf(
			debugStickCraftingRecipe
		)
	}

}