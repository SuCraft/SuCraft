/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.craftablebarriersandlights

import org.bukkit.Material.*
import org.bukkit.inventory.ItemStack
import org.sucraft.common.advancement.diamondsAdvancement
import org.sucraft.common.itemstack.recipe.ShapedCustomRecipe

val lightCraftingRecipe = ShapedCustomRecipe(
	"light_recipe",
	false,
	ItemStack(LIGHT, 10)
) {
	shape("$$$", "$%$", "$$$")
	setIngredient('$', GLASS)
	setIngredient('%', DIAMOND)
}.apply {
	addPermission(CraftableBarriersAndLights.Permissions.craftLight)
	addItemMaterialsToDiscover(DIAMOND)
	addAdvancementToDiscover(diamondsAdvancement)
}