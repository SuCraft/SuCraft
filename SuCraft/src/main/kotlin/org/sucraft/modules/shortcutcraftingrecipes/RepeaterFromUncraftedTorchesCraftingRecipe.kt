/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.shortcutcraftingrecipes

import org.bukkit.Material.*
import org.bukkit.inventory.ItemStack
import org.sucraft.common.itemstack.recipe.ShapedCustomRecipe

val repeaterFromUncraftedTorchesCraftingRecipe = ShapedCustomRecipe(
	"repeater_from_uncrafted_torches_recipe",
	ItemStack(REPEATER)
) {
	shape("% %", "@%@", "$$$")
	setIngredient('%', REDSTONE)
	setIngredient('@', STICK)
	setIngredient('$', STONE)
}.apply {
	addPermission(ShortcutCraftingRecipes.Permissions.repeaterFromUncraftedTorches)
	addItemMaterialsToDiscover(REDSTONE)
}