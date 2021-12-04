/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.customcraftingrecipes.recipe.alcohol

import org.bukkit.ChatColor.*
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.sucraft.core.common.bukkit.item.ItemStackBuilder
import org.sucraft.core.common.sucraft.player.PlayerUUID


object AlcoholData {

	@Suppress("DEPRECATION")
	private val alcoholPersistentDataNamespacedKey = NamespacedKey("martijnsrecipes", "alcoholicBeverage")
	@Suppress("DEPRECATION")
	private val alcoholBrewerPersistentDataNamespacedKey = NamespacedKey("martijnsrecipes", "alcoholicBeverageBrewer")

	@Suppress("DEPRECATION")
	val beerItemStackWithoutBrewer = ItemStackBuilder
		.create(Material.POTION)
		.setPersistentDataTag(alcoholPersistentDataNamespacedKey)
		.setPotionColor(Color.fromRGB(16760835))
		.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ATTRIBUTES)
		.setDisplayName("${WHITE}Beer").build()
	@Suppress("DEPRECATION")
	val vodkaItemStackWithoutBrewer = ItemStackBuilder
		.create(Material.POTION)
		.setPersistentDataTag(alcoholPersistentDataNamespacedKey)
		.setPotionColor(Color.fromRGB(11184810))
		.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ATTRIBUTES)
		.setDisplayName("${WHITE}Vodka").build()

	@Suppress("DEPRECATION")
	fun addBrewedLoreAndPersistentData(player: Player, itemStack: ItemStack) = ItemStackBuilder
			.create(itemStack)
			.addLore("${GRAY}Brewed by ${player.name}")
			.setPersistentDataPlayerUUID(alcoholBrewerPersistentDataNamespacedKey, PlayerUUID.get(player))
			.build()

	fun isAlcoholicItemStack(itemStack: ItemStack) = ItemStackBuilder
		.create(itemStack)
		.getPersistentDataTag(alcoholPersistentDataNamespacedKey)

	fun isSimilarToAlcoholicItemStackWithoutBrewer(itemStack: ItemStack) =
		itemStack.isSimilar(beerItemStackWithoutBrewer) || itemStack.isSimilar(vodkaItemStackWithoutBrewer)

}