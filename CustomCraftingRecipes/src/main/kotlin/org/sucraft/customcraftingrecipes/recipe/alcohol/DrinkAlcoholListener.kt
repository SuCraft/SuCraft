/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.customcraftingrecipes.recipe.alcohol

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.customcraftingrecipes.main.SuCraftCustomCraftingRecipesPlugin

object DrinkAlcoholListener : SuCraftComponent<SuCraftCustomCraftingRecipesPlugin>(SuCraftCustomCraftingRecipesPlugin.getInstance()) {

	// Events

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onPlayerItemConsume(event: PlayerItemConsumeEvent) {
		if (AlcoholData.isAlcoholicItemStack(event.item))
			event.player.addPotionEffect(PotionEffect(PotionEffectType.CONFUSION, 600, 0, false, false, false))
	}

}