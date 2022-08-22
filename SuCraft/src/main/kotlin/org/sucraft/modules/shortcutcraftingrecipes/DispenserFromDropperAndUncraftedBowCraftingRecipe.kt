/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.shortcutcraftingrecipes

import org.bukkit.Material.*
import org.bukkit.inventory.ItemStack
import org.sucraft.common.itemstack.recipe.ShapedCustomRecipe

val dispenserFromDropperAndUncraftedBowCraftingRecipe = ShapedCustomRecipe(
	"dispenser_from_dropper_and_uncrafted_bow_recipe",
	true,
	ItemStack(DISPENSER)
) {
	shape("$% ", "$@%", "$% ")
	setIngredient('$', STRING)
	setIngredient('%', STICK)
	setIngredient('@', DROPPER)
}.apply {
	addPermission(ShortcutCraftingRecipes.Permissions.dispenserFromDropperAndUncraftedBow)
	addItemMaterialsToDiscover(DROPPER)
}