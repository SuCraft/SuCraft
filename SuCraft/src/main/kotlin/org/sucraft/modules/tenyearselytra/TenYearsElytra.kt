/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.tenyearselytra

import com.destroystokyo.paper.event.player.PlayerElytraBoostEvent
import net.kyori.adventure.text.format.NamedTextColor.GRAY
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material.ELYTRA
import org.bukkit.inventory.ItemStack
import org.bukkit.permissions.PermissionDefault
import org.sucraft.common.event.on
import org.sucraft.common.itemstack.amountAndTypeAndNBT
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.permission.hasPermission
import org.sucraft.common.text.*
import org.sucraft.modules.harmlessentities.HarmlessEntities
import org.sucraft.modules.tenyearselytra.TenYearsElytraFireworks.scheduleTenYearsFireworks

/**
 * Adds special elytras for SuCraft's 10-year anniversary.
 */
object TenYearsElytra : SuCraftModule<TenYearsElytra>(), TenYearsElytraItemStackExtensions {

	// Dependencies

	override val dependencies = listOf(
		HarmlessEntities
	)

	// Settings

	private const val tenYearsCommandLabel = "10years"
	private const val removeElytraEffectSubArgument = "remove"

	private val youMustBeHoldingATenYearsElytraMessage = component(color = ERROR_NOTHING_HAPPENED) {
		+"You must be holding a 10-years elytra in your main hand to remove the effect." +
				ERROR_NOTHING_HAPPENED_EXTRA_DETAIL * (
				+"If you want to add the effect to an elytra, you can do so with" +
						ERROR_NOTHING_HAPPENED_FOCUS * (+"/" - tenYearsCommandLabel) -
						"."
				)
	}
	private val removedTenYearsEffectMessage = component(color = BORING_SUCCESS) {
		"The effect has been removed from the elytra."
	}
	private val commandNoLongerUsableMessage = component(color = GRAY) {
		"This command existed before to celebrate SuCraft being online for 10 years, but is no longer usable."
	}
	private val youMustBeHoldingAnElytraMessage = component(color = ERROR_NOTHING_HAPPENED) {
		"You must be holding an elytra in your main hand to use this command."
	}
	private val elytraAlreadyHasEffectMessage = component(color = ERROR_NOTHING_HAPPENED) {
		+"This elytra already has the 10-years effect!" +
				ERROR_NOTHING_HAPPENED_EXTRA_DETAIL * (
				+"If you are sure want to remove the effect, you can do so with" +
						ERROR_NOTHING_HAPPENED_FOCUS *
						(+"/" - tenYearsCommandLabel + +removeElytraEffectSubArgument) -
						"."
				)
	}
	private val addedTenYearsEffectMessage = MiniMessage.builder().build()
		.deserialize("<rainbow:2>Enjoy your special festive elytra :)</rainbow>")

	// Components

	override val components = listOf(
		TenYearsElytraFireworks
	)

	// Permissions

	object Permissions {

		val create = permission(
			"create",
			"Create a special 10-years elytra",
			PermissionDefault.TRUE
		)

	}

	// Commands

	object Commands {

		val tenYears = commandTree(
			tenYearsCommandLabel,
			"Create a special 10-years elytra",
			arrayOf("tenyears"),
		) {

			thenLiteralSync(removeElytraEffectSubArgument) {
				// Remove the effect from an elytra
				executesPlayerSync executes@{

					// Make sure the held item is a 10-years elytra
					val itemStack = inventory.itemInMainHand
					if (!itemStack.isTenYearsElytra) {
						sendMessage(youMustBeHoldingATenYearsElytraMessage)
						return@executes
					}

					// Remove the effect
					val newItemStack = itemStack.getWithoutTenYearsEffect()
					// Place the new item stack in the player's main hand
					inventory.setItemInMainHand(newItemStack)
					// Tell the player what happened
					sendMessage(removedTenYearsEffectMessage)

				}
			}

			// Add the effect to an elytra
			executesPlayerSync executes@{

				// Make sure the player has permission to use this command
				if (!hasPermission(Permissions.create)) {
					sendMessage(commandNoLongerUsableMessage)
					return@executes
				}

				// Make sure the held item is an elytra
				val itemStack = inventory.itemInMainHand
				if (itemStack.type != ELYTRA) {
					sendMessage(youMustBeHoldingAnElytraMessage)
					return@executes
				}

				// Make sure the held items is not already a 10-years elytra
				if (itemStack.isTenYearsElytra) {
					sendMessage(elytraAlreadyHasEffectMessage)
					return@executes
				}

				// Add the effect
				val newItemStack: ItemStack = itemStack.getWithTenYearsEffect(this)
				// Place the new item stack in the player's main hand
				inventory.setItemInMainHand(newItemStack)
				// Let the player know what happened
				@Suppress("DEPRECATION")
				sendTitle("Thank you~", "Thank you for being a part of this wonderful community <3", 10, 100, 45)
				sendMessage(addedTenYearsEffectMessage)

			}
		}

	}

	// Events

	init {
		// Listen for elytra rocket boost events to schedule the 10-years fireworks
		on(PlayerElytraBoostEvent::class) {
			val chestplate = player.inventory.chestplate
			// Don't do anything if the player is not wearing a 10-years elytra
			if (chestplate?.isTenYearsElytra != true) return@on
			// Fix old lore if present and add custom model data if absent
			chestplate.getWithFixedLoreAndCustomModelDataIfNecessary()?.let {
				// Log to console
				info(
					"Fixed 10-years elytra item from ${chestplate.amountAndTypeAndNBT} " +
							"to ${it.amountAndTypeAndNBT} for ${player.name}"
				)
				// Place the fixed elytra in the player's chestplate slot instead
				player.inventory.chestplate = it
			}
			// Schedule to spawn fireworks
			player.scheduleTenYearsFireworks()
		}
	}

}