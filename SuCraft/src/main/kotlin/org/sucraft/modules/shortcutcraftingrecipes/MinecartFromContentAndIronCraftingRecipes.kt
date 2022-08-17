/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.shortcutcraftingrecipes

import org.bukkit.Material
import org.bukkit.Material.*
import org.bukkit.inventory.ItemStack
import org.sucraft.common.itemstack.recipe.ShapedCustomRecipe
import org.sucraft.common.permission.SuCraftPermission

fun createMinecartFromContentAndIronCraftingRecipe(
	name: String,
	content: Material,
	minecart: Material,
	permission: SuCraftPermission,
	vararg materialsToDiscover: Material = arrayOf(IRON_INGOT)
) = ShapedCustomRecipe(
	"${name}_minecart_from_${name}_and_iron_recipe",
	ItemStack(minecart)
) {
	shape("$%$", "$$$")
	setIngredient('%', content)
	setIngredient('$', IRON_INGOT)
}.apply {
	addPermission(permission)
	addItemMaterialsToDiscover(*materialsToDiscover)
}

val chestMinecartFromChestAndIronCraftingRecipe = createMinecartFromContentAndIronCraftingRecipe(
	"chest",
	CHEST,
	CHEST_MINECART,
	ShortcutCraftingRecipes.Permissions.chestMinecartFromChestAndIron
)

val furnaceMinecartFromFurnaceAndIronCraftingRecipe = createMinecartFromContentAndIronCraftingRecipe(
	"furnace",
	FURNACE,
	FURNACE_MINECART,
	ShortcutCraftingRecipes.Permissions.furnaceMinecartFromFurnaceAndIron
)

val hopperMinecartFromHopperAndIronCraftingRecipe = createMinecartFromContentAndIronCraftingRecipe(
	"hopper",
	HOPPER,
	HOPPER_MINECART,
	ShortcutCraftingRecipes.Permissions.hopperMinecartFromHopperAndIron,
	HOPPER
)

val tntMinecartFromTNTAndIronCraftingRecipe = createMinecartFromContentAndIronCraftingRecipe(
	"tnt",
	TNT,
	TNT_MINECART,
	ShortcutCraftingRecipes.Permissions.tntMinecartFromTNTAndIron,
	TNT
)