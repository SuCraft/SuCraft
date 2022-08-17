/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.mysteryboxes

import org.bukkit.permissions.PermissionDefault
import org.sucraft.common.itemstack.amountAndTypeAndNBT
import org.sucraft.common.itemstack.material.isUncoloredOrColoredShulkerBox
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.permission.hasPermission
import org.sucraft.common.text.*
import org.sucraft.modules.donators.Donators
import org.sucraft.modules.donators.Donators.sendOnlyDonatorsHaveAbility

/**
 * Lets players turn shulker boxes into mystery boxes, of which the contents are hidden until placed.
 */
object MysteryBoxes : SuCraftModule<MysteryBoxes>(), MysteryBoxItemStackExtensions {

	// Dependencies

	override val dependencies = listOf(
		Donators
	)

	// Components

	override val components = listOf(
		MysteryBoxPlaceMessageSender,
		RemoveMysteryBoxNameOnUse
	)

	// Permissions

	object Permissions {

		val create = permission(
			"create", "Use the /mysterybox command", PermissionDefault.OP
		)

	}

	// Commands

	object Commands {

		val mysteryBox = command(
			"mysterybox", "Turn a shulker box into a mystery box"
		) {
			executesPlayerSync executes@{

				// Make sure the player has permission to use this command
				if (!hasPermission(Permissions.create)) {
					sendOnlyDonatorsHaveAbility("create mystery shulker boxes, where the contents are a surprise")
					return@executes
				}

				// Make sure the player is holding a shulker box
				val itemStack = inventory.itemInMainHand
				if (!itemStack.type.isUncoloredOrColoredShulkerBox) {
					sendMessage(448503987867463546L, ERROR_NOTHING_HAPPENED) {
						"You must be holding a shulker box to turn into a mystery box."
					}
					return@executes
				}

				// Make sure the shulker box the player is holding is not already a mystery box
				if (itemStack.isMysteryBox) {
					sendMessage(465776776857649012L, ERROR_NOTHING_HAPPENED) {
						"You are already holding a mystery box."
					}
					return@executes
				}

				// Make the item a mystery box
				val newItemStack = itemStack.makeIntoMysteryBox()

				// Place the mystery box in the same slot in the inventory
				inventory.setItemInMainHand(newItemStack)

				// Log to console
				info(
					"$name created a mystery shulker box: ${itemStack.amountAndTypeAndNBT} " +
							"-> ${newItemStack.amountAndTypeAndNBT}"
				)

				// Tell the player what happened
				sendMessage(
					445765645113546079L, BORING_SUCCESS,
					newItemStack.type.displayNameAsMysteryBox
				) {
					+"You created a" + BORING_SUCCESS_FOCUS * variable - "!"
				}

			}
		}

	}

}