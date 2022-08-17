/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.craftabledebugstick

import org.bukkit.Material
import org.bukkit.Material.LAPIS_LAZULI
import org.bukkit.Material.STICK
import org.bukkit.inventory.ItemStack
import org.sucraft.common.itemstack.recipe.ShapelessCustomRecipe
import org.sucraft.common.permission.survivallikeDebugStickPermission

val debugStickCraftingRecipe = ShapelessCustomRecipe(
	"debug_stick_recipe",
	ItemStack(Material.DEBUG_STICK, 1)
) {
	addIngredient(STICK)
	addIngredient(LAPIS_LAZULI)
}.apply {
	addPermission(CraftableDebugStick.Permissions.craftDebugStick)
	addPermission(survivallikeDebugStickPermission)
	addItemMaterialsToDiscover(LAPIS_LAZULI)
}