/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.shortcutcraftingrecipes

import org.bukkit.Material.*
import org.bukkit.inventory.ItemStack
import org.sucraft.common.itemstack.recipe.ShapelessCustomRecipe

val dispenserFromDropperAndBowCraftingRecipe = ShapelessCustomRecipe(
	"dispenser_from_dropper_and_bow_recipe",
	true,
	ItemStack(DISPENSER)
) {
	addIngredient(DROPPER)
	// TODO this may allow for dispensers to be crafted with damaged bows,
	// and perhaps that is not possible in vanilla - the easiest solution seems to be to check
	// for a crafting event and cancel any event where the result is a dispenser and one of the ingredients
	// is a damaged bow
	addIngredient(BOW)
}.apply {
	addPermission(ShortcutCraftingRecipes.Permissions.dispenserFromDropperAndBow)
	addItemMaterialsToDiscover(DROPPER)
}