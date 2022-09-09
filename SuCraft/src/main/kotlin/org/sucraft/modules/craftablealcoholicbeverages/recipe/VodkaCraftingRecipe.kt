/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.craftablealcoholicbeverages.recipe

import org.bukkit.Color
import org.bukkit.Material.POTATO
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
import org.sucraft.modules.craftablealcoholicbeverages.CraftableAlcoholicBeverages
import org.sucraft.modules.craftablealcoholicbeverages.setAlcoholicBeveragePersistentFlag

private val vodkaKey by lazy {
	"vodka".toSuCraftNamespacedKey()
}
private const val vodkaCustomModelData = 2

val vodkaNauseaDuration = TimeInSeconds(40)

val vodkaItemStackWithoutBrewer =
	ItemStack(POTION).apply {
		setAlcoholicBeveragePersistentFlag()
		setPersistentFlag(vodkaKey)
		customModelData = vodkaCustomModelData
		potionColor = Color.fromRGB(11184810)
		addItemFlags(HIDE_POTION_EFFECTS, HIDE_ATTRIBUTES)
		displayName(unstyledText("Vodka"))
	}

val vodkaCraftingRecipe = ShapelessCustomRecipe(
	{ @Suppress("DEPRECATION") NamespacedKey("martijnsrecipes", "vodka_recipe") },
	false,
	vodkaItemStackWithoutBrewer
) {
	addIngredient(createWaterBottle())
	addIngredient(POTATO)
}.apply {
	addPermission(CraftableAlcoholicBeverages.Permissions.craftVodka)
	addItemStackToDiscover { type == POTION && basePotionDate.type == PotionType.WATER }
	doNotAddResultMaterialAsMaterialToDiscover()
}

val ItemStack.isVodka
	get() = type == vodkaItemStackWithoutBrewer.type && getPersistentFlag(vodkaKey)