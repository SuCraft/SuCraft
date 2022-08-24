/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.craftablealcoholicbeverages

import org.bukkit.Color
import org.bukkit.Material.POTION
import org.bukkit.Material.WHEAT
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemFlag.HIDE_ATTRIBUTES
import org.bukkit.inventory.ItemFlag.HIDE_POTION_EFFECTS
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionType
import org.sucraft.common.itemstack.createWaterBottle
import org.sucraft.common.itemstack.meta.customModelData
import org.sucraft.common.itemstack.meta.displayName
import org.sucraft.common.itemstack.meta.potion.basePotionDate
import org.sucraft.common.itemstack.meta.potion.potionColor
import org.sucraft.common.itemstack.recipe.ShapelessCustomRecipe
import org.sucraft.common.namespacedkey.toSuCraftNamespacedKey
import org.sucraft.common.persistentdata.getPersistentFlag
import org.sucraft.common.persistentdata.setPersistentFlag
import org.sucraft.common.text.unstyledText
import org.sucraft.common.time.TimeInSeconds

private val beerKey by lazy {
	"beer".toSuCraftNamespacedKey()
}
private const val beerCustomModelData = 1

val beerNauseaDuration = TimeInSeconds(20)

val beerItemStackWithoutBrewer =
	ItemStack(POTION).apply {
		setAlcoholicBeveragePersistentFlag()
		setPersistentFlag(beerKey)
		customModelData = beerCustomModelData
		potionColor = Color.fromRGB(16760835)
		addItemFlags(HIDE_POTION_EFFECTS, HIDE_ATTRIBUTES)
		displayName(unstyledText("Beer"))
	}

val beerCraftingRecipe = ShapelessCustomRecipe(
	{ @Suppress("DEPRECATION") NamespacedKey("martijnsrecipes", "beer_recipe") },
	false,
	beerItemStackWithoutBrewer
) {
	addIngredient(createWaterBottle())
	addIngredient(WHEAT)
}.apply {
	addPermission(CraftableAlcoholicBeverages.Permissions.craftBeer)
	addItemStackToDiscover { type == POTION && basePotionDate.type == PotionType.WATER }
	doNotAddResultMaterialAsMaterialToDiscover()
}

val ItemStack.isBeer
	get() = type == beerItemStackWithoutBrewer.type && getPersistentFlag(beerKey)