/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.craftablebarriersandlights

import org.bukkit.Material.*
import org.bukkit.inventory.ItemStack
import org.sucraft.common.advancement.diamondsAdvancement
import org.sucraft.common.itemstack.recipe.ShapedCustomRecipe

val barrierCraftingRecipe = ShapedCustomRecipe(
	"barrier_recipe",
	false,
	ItemStack(BARRIER, 10)
) {
	shape("$$$", "$%$", "$$$")
	setIngredient('$', RED_WOOL)
	setIngredient('%', DIAMOND)
}.apply {
	addPermission(CraftableBarriersAndLights.Permissions.craftBarrier)
	addItemMaterialsToDiscover(DIAMOND)
	addAdvancementToDiscover(diamondsAdvancement)
}