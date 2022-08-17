/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.mysteryboxes

import org.bukkit.block.ShulkerBox
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.InventoryType.SHULKER_BOX
import org.sucraft.common.event.on
import org.sucraft.common.module.SuCraftComponent

/**
 * Removes the custom display name of a mystery box when it is used
 * (and thus when it turns back into a regular shulker box).
 */
object RemoveMysteryBoxNameOnUse : SuCraftComponent<MysteryBoxes>(), MysteryBoxBlockExtensions {

	// Events

	init {

		// Remove the mystery box title when a mystery box is placed
		on(BlockPlaceEvent::class) {
			blockPlaced.removeMysteryBoxTitleIfNecessary()
		}

		// Remove the mystery box title when a mystery box is broken
		on(BlockBreakEvent::class) {
			block.removeMysteryBoxTitleIfNecessary()
		}

		// Remove the mystery box title when a mystery box is opened
		on(InventoryOpenEvent::class) {
			if (inventory.type != SHULKER_BOX) return@on
			(inventory.holder as? ShulkerBox)?.block?.removeMysteryBoxTitleIfNecessary()
		}

	}

}