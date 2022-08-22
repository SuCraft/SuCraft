/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.craftablealcoholicbeverages

import org.bukkit.Color
import org.bukkit.Material.BREAD
import org.bukkit.Material.POTION
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

private val kvassKey by lazy {
	"kvass".toSuCraftNamespacedKey()
}
private const val kvassCustomModelData = 3

val kvassNauseaDuration = TimeInSeconds(30)

val kvassItemStackWithoutBrewer =
	ItemStack(POTION).apply {
		setAlcoholicBeveragePersistentFlag()
		setPersistentFlag(kvassKey)
		customModelData = kvassCustomModelData
		potionColor = Color.fromRGB(15100234)
		addItemFlags(HIDE_POTION_EFFECTS, HIDE_ATTRIBUTES)
		displayName(unstyledText("Kvass"))
	}

val kvassCraftingRecipe = ShapelessCustomRecipe(
	{ @Suppress("DEPRECATION") NamespacedKey("martijnsrecipes", "kvass_recipe") },
	false,
	kvassItemStackWithoutBrewer
) {
	addIngredient(createWaterBottle())
	addIngredient(BREAD)
}.apply {
	addPermission(CraftableAlcoholicBeverages.Permissions.craftKvass)
	addItemStackToDiscover { type == POTION && basePotionDate.type == PotionType.WATER }
}

val ItemStack.isKvass
	get() = type == kvassItemStackWithoutBrewer.type && getPersistentFlag(kvassKey)