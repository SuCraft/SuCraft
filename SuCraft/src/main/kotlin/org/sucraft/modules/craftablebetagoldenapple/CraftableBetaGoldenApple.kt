/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.craftablebetagoldenapple

import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.permissions.PermissionDefault
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType.REGENERATION
import org.sucraft.common.event.on
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.player.maxPlayerFoodLevel
import org.sucraft.common.time.TimeInSeconds

/**
 * Adds a crafting recipe for Beta golden apples,
 * which are what golden apples worked like in Minecraft version Beta 1.8,
 * and that were thenSync crafted with this recipe.
 */
object CraftableBetaGoldenApple : SuCraftModule<CraftableBetaGoldenApple>() {

	// Permissions

	object Permissions {
		val craftBetaGoldenApple = permission(
			"craft.betagoldenapple",
			"Craft Beta golden apples",
			PermissionDefault.TRUE
		)
	}

	// Recipes

	override val customRecipes by lazy {
		listOf(
			betaGoldenAppleCraftingRecipe
		)
	}

	// Events

	init {

		// Handle players drinking alcoholic beverages to give them an effect
		on(PlayerItemConsumeEvent::class) {
			if (item.isBetaGoldenApple) {
				// 4 hunger will be added because this is also treated as a normal golden apple,
				// so we add additional hunger to make 10, like it was in Minecraft version Beta 1.8
				player.foodLevel = (player.foodLevel + 6).coerceAtMost(maxPlayerFoodLevel)
				// Absorption and Regeneration II are added as normal golden apple effects,
				// we will add the Regeneration effect for 30 seconds, like it was in Minecraft version Beta 1.8
				player.addPotionEffect(
					PotionEffect(
						REGENERATION,
						TimeInSeconds(30).ticksFloored.toInt(),
						0,
						false,
						true,
						true
					)
				)
			}
		}

	}

}