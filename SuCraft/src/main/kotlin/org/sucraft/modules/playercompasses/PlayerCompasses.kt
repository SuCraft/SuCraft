/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.playercompasses

import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor.*
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.permissions.PermissionDefault
import org.sucraft.common.itemstack.amountAndTypeAndNBT
import org.sucraft.common.itemstack.meta.addLore
import org.sucraft.common.itemstack.meta.runMeta
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.permission.hasPermission
import org.sucraft.common.text.*
import org.sucraft.modules.donators.Donators
import org.sucraft.modules.donators.Donators.sendOnlyDonatorsHaveAbility

/**
 * Adds player-tracking compasses that can be created by donators.
 */
object PlayerCompasses : SuCraftModule<PlayerCompasses>(), PlayerCompassItemStackManipulation {

	// Dependencies

	override val dependencies = listOf(
		Donators
	)

	// Components

	override val components = listOf(
		PlayerCompassUpdater
	)

	// Permissions

	object Permissions {

		val create = permission(
			"create", "Use the /playercompass command", PermissionDefault.OP
		)

	}

	// Commands

	object Commands {

		val playerCompass = command(
			"playercompass", "Create a compass that points to you"
		) {
			executesPlayerSync executes@{

				// Make sure the player has permission to use this command
				if (!hasPermission(Permissions.create)) {
					sendOnlyDonatorsHaveAbility("create compasses that always point to them (and give them to other players)")
					return@executes
				}

				// Make sure the player is holding a compass
				val itemStack = inventory.itemInMainHand
				if (itemStack.type != Material.COMPASS) {
					sendMessage(446758998756774539L, ERROR_NOTHING_HAPPENED) {
						"You must be holding a compass to make a player compass."
					}
					return@executes
				}

				// Make sure the compass the player is holding is not already a player compass
				if (itemStack.isPlayerCompass) {
					sendMessage(980812342673452889L, ERROR_NOTHING_HAPPENED) {
						"You are already holding a player compass."
					}
					return@executes
				}

				// Set the compass to track this player
				itemStack.setPlayerCompassTrackedPlayer(this)

				// Update the compass display name, if it does not already have one
				itemStack.runMeta {
					if (!hasDisplayName()) {
						displayName(
							patternAndCompile(
								483391224536635536L, WHITE,
								name
							) {
								(+"Compass for" + GOLD * variable).nonItalic()
							}
						)
					}
				}

				// Update the compass lore
				itemStack.addLore(
					patternAndCompile(
						748886490979646353L, GRAY,
						name
					) {
						(+"This player compass points to" + TextColor.fromCSSHexString("#d6d6d6") * variable).nonItalic()
					},
					component(438994857114395998L, GRAY) {
						text("when they are in the same world!").nonItalic()
					}
				)

				// Update the compass direction right away
				PlayerCompassUpdater.possiblyUpdateCompass(location, itemStack, this)
				updateInventory()

				// Log to console
				info("$name}created a player compass: ${itemStack.amountAndTypeAndNBT}")

				// Tell the player what happened
				sendMessage(483948112049008958L, BORING_SUCCESS) {
					+"You created a" + BORING_SUCCESS_FOCUS * "player compass" + "that always points to you."
				}

			}
		}

	}

}