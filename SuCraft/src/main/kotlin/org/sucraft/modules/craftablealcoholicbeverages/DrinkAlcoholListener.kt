/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.craftablealcoholicbeverages

import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType.CONFUSION
import org.sucraft.common.event.on
import org.sucraft.common.itemstack.amountAndTypeAndNBT
import org.sucraft.common.module.SuCraftComponent

/**
 * Gives players drinking alcoholic beverages an effect.
 */
object DrinkAlcoholListener : SuCraftComponent<CraftableAlcoholicBeverages>() {

	// Events

	init {
		on(PlayerItemConsumeEvent::class) {

			// Make sure the item consumed is an alcoholic beverage
			if (!item.isAlcoholicBeverage) return@on

			// Log to console
			info(
				"$name drank an alcoholic beverage: ${item.amountAndTypeAndNBT}"
			)

			// Add the effect
			player.addPotionEffect(
				PotionEffect(
					CONFUSION,
					when {
						item.isVodka -> vodkaNauseaDuration
						item.isBeer -> beerNauseaDuration
						item.isKvass -> kvassNauseaDuration
						else -> beerNauseaDuration
					}.ticksFloored.toInt(),
					0,
					false,
					false,
					false
				)
			)

		}
	}

}