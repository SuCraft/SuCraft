/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.shortcutcraftingrecipes

import org.bukkit.Material.CHEST
import org.bukkit.Tag.LOGS
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.RecipeChoice
import org.sucraft.common.itemstack.recipe.ShapedCustomRecipe

val chestsFromLogsCraftingRecipe = ShapedCustomRecipe(
	"chests_from_logs_recipe",
	true,
	ItemStack(CHEST, 4)
) {
	shape("$$$", "$ $", "$$$")
	setIngredient('$', RecipeChoice.MaterialChoice(LOGS))
}.apply {
	addPermission(ShortcutCraftingRecipes.Permissions.chestsFromLogs)
	addItemMaterialsToDiscover(LOGS)
}