/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.customcraftingrecipes.recipe.alcohol

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack
import org.sucraft.core.common.bukkit.inventory.setNullableContents
import org.sucraft.core.common.bukkit.scheduler.RunInFuture
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.customcraftingrecipes.main.SuCraftCustomCraftingRecipesPlugin

object CraftAlcoholListener : SuCraftComponent<SuCraftCustomCraftingRecipesPlugin>(SuCraftCustomCraftingRecipesPlugin.getInstance()) {

	// Events

	@EventHandler(priority = EventPriority.MONITOR)
	fun onCraftItem(event: InventoryClickEvent) {

		if (event.slotType != InventoryType.SlotType.RESULT) return
		if (event.inventory.type != InventoryType.CRAFTING && event.inventory.type != InventoryType.WORKBENCH) return
		val player = event.whoClicked as? Player ?: return
		val resultingItem = event.currentItem ?: return
		if (!AlcoholData.isSimilarToAlcoholicItemStackWithoutBrewer(resultingItem)) return

		// Schedule to add lore to the item
		RunInFuture.forPlayerIfOnline(plugin, player, {

			val inventory = it.inventory
			val contents: Array<ItemStack?> = inventory.contents!!
			var changedInventoryContents = false

			for (i in contents.indices) {
				if (contents[i] != null && AlcoholData.isSimilarToAlcoholicItemStackWithoutBrewer(contents[i]!!)) {
					contents[i] = AlcoholData.addBrewedLoreAndPersistentData(it, contents[i]!!)
					changedInventoryContents = true
				}
			}

			if (changedInventoryContents) inventory.setNullableContents(contents)

			val cursor = event.view.cursor
			if (cursor != null && AlcoholData.isSimilarToAlcoholicItemStackWithoutBrewer(cursor)) {
				event.view.cursor = AlcoholData.addBrewedLoreAndPersistentData(it, cursor)
				changedInventoryContents = true
			}

			if (changedInventoryContents) it.updateInventory()

		})

	}

}