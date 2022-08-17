/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.mysteryboxes

import org.bukkit.event.block.BlockPlaceEvent
import org.sucraft.common.block.BlockCoordinates.Companion.coordinates
import org.sucraft.common.event.on
import org.sucraft.common.itemstack.amountAndTypeAndNBT
import org.sucraft.common.module.SuCraftComponent
import org.sucraft.common.text.BORING_SUCCESS
import org.sucraft.common.text.BORING_SUCCESS_FOCUS
import org.sucraft.common.text.sendMessage
import org.sucraft.common.text.times

/**
 * Sends a message to players when they place a mystery box.
 */
object MysteryBoxPlaceMessageSender : SuCraftComponent<MysteryBoxes>(), MysteryBoxItemStackExtensions {

	// Events

	init {
		// Send a message when a mystery box is placed
		on(BlockPlaceEvent::class) {

			// Ignore the event if the item in hand is not a mystery box
			if (!itemInHand.isMysteryBox) return@on

			// Log to console
			info(
				"${player.name} placed a mystery shulker box: ${itemInHand.amountAndTypeAndNBT} at " +
						blockPlaced.coordinates
			)

			// Send the message to the player
			player.sendMessage(
				938009878453785600L, BORING_SUCCESS,
				itemInHand.type.displayNameComponentAsMysteryBox
			) {
				+"The contents of your" + BORING_SUCCESS_FOCUS * variable + "have been revealed!"
			}

		}
	}

}