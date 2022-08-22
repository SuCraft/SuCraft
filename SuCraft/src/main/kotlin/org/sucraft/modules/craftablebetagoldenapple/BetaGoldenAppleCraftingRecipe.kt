/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.craftablebetagoldenapple

import org.bukkit.Material.*
import org.bukkit.inventory.ItemStack
import org.sucraft.common.itemstack.meta.customModelData
import org.sucraft.common.itemstack.meta.displayName
import org.sucraft.common.itemstack.recipe.ShapedCustomRecipe
import org.sucraft.common.namespacedkey.toSuCraftNamespacedKey
import org.sucraft.common.persistentdata.getPersistentFlag
import org.sucraft.common.persistentdata.setPersistentFlag
import org.sucraft.common.text.unstyledText

private val betaGoldenAppleKey by lazy {
	"beta_golden_apple".toSuCraftNamespacedKey()
}
private const val betaGoldenAppleCustomModelData = 1

val betaGoldenAppleCraftingRecipe = ShapedCustomRecipe(
	"beta_golden_apple_recipe",
	false,
	ItemStack(GOLDEN_APPLE).apply {
		setPersistentFlag(betaGoldenAppleKey)
		customModelData = betaGoldenAppleCustomModelData
		displayName(unstyledText("Beta Golden Apple"))
	}
) {
	shape("$$$", "$%$", "$$$")
	setIngredient('$', GOLD_BLOCK)
	setIngredient('%', APPLE)
}.apply {
	addPermission(CraftableBetaGoldenApple.Permissions.craftBetaGoldenApple)
	addItemMaterialsToDiscover(GOLD_BLOCK)
}

val ItemStack.isBetaGoldenApple
	get() = type == betaGoldenAppleCraftingRecipe.result.type && getPersistentFlag(betaGoldenAppleKey)