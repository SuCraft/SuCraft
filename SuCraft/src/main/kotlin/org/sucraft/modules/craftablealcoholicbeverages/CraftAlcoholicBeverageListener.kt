/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.craftablealcoholicbeverages

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType.CRAFTING
import org.bukkit.event.inventory.InventoryType.SlotType.RESULT
import org.bukkit.event.inventory.InventoryType.WORKBENCH
import org.sucraft.common.event.on
import org.sucraft.common.function.runIf
import org.sucraft.common.inventory.updateInventoryAndCursor
import org.sucraft.common.itemstack.amountAndTypeAndNBT
import org.sucraft.common.itemstack.takeIfNotEmpty
import org.sucraft.common.module.SuCraftComponent
import org.sucraft.common.scheduler.runNextTick

/**
 * Adds the brewer (crafter) of alcoholic beverages to the item data.
 */
object CraftAlcoholicBeverageListener : SuCraftComponent<CraftableAlcoholicBeverages>() {

	// Events

	init {
		on(InventoryClickEvent::class) {

			// Make sure an alcoholic beverage is being crafted
			if (slotType != RESULT) return@on
			if (!(inventory.type == CRAFTING || inventory.type == WORKBENCH)) return@on
			val player = whoClicked as? Player ?: return@on
			val resultingItem = currentItem
				?.takeIfNotEmpty()
				?.takeIf { it.isSimilarToAlcoholicBeverageWithoutBrewer }
				?: return@on

			// Add lore to the item now (this should be safe because we already know it is in the result slot)
			currentItem = resultingItem.setBrewerLoreAndPersistentDataAfterBrewing(player)

			// Log to console
			info(
				"${player.name} crafted an alcoholic beverage: ${currentItem!!.amountAndTypeAndNBT}"
			)

			// Schedule to add lore to all items in the player's inventory and on their cursor, just in case
			player.runNextTick {
				updateInventoryAndCursor {
					it?.runIf(it.isSimilarToAlcoholicBeverageWithoutBrewer) {
						setBrewerLoreAndPersistentDataAfterBrewing(player)
					}
				}
			}

		}
	}

}