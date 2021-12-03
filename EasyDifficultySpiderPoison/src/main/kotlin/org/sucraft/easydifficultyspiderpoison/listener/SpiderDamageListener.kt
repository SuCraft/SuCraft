/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.easydifficultyspiderpoison.listener

import org.bukkit.Difficulty
import org.bukkit.entity.CaveSpider
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.easydifficultyspiderpoison.main.SuCraftEasyDifficultySpiderPoisonPlugin


object SpiderDamageListener : SuCraftComponent<SuCraftEasyDifficultySpiderPoisonPlugin>(SuCraftEasyDifficultySpiderPoisonPlugin.getInstance()) {

	// Settings

	private const val chanceForPoisonOnDamage = 0.2
	private const val poisonDurationInTicks = 20 * 5
	private const val poisonAmplifier = 0

	// Events

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onEntityDamageByEntity(event: EntityDamageByEntityEvent) {
		if (event.damage <= 0) return
		if (event.damager !is CaveSpider) return
		val player = event.entity as? Player ?: return
		if (player.world.difficulty != Difficulty.EASY) return
		if (Math.random() >= chanceForPoisonOnDamage) return
		player.addPotionEffect(PotionEffect(PotionEffectType.POISON, poisonDurationInTicks, poisonAmplifier, false))
	}

}