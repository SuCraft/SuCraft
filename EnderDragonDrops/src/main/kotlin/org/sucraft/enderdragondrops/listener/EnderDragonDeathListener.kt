/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.enderdragondrops.listener

import org.bukkit.World
import org.bukkit.entity.EnderDragon
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityDeathEvent
import org.sucraft.core.common.bukkit.player.ClosestPlayerFinder
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.enderdragondrops.drop.AdditionalEnderDragonDrops
import org.sucraft.enderdragondrops.main.SuCraftEnderDragonDropsPlugin


object EnderDragonDeathListener : SuCraftComponent<SuCraftEnderDragonDropsPlugin>(SuCraftEnderDragonDropsPlugin.getInstance()) {

	// Events

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onEntityDeath(event: EntityDeathEvent) {
		val dragon = (event.entity as? EnderDragon) ?: return
		// Log to console
		logger.info("An ender dragon died at ${dragon.location} (${dragon.killer?.let {"killed by ${it.name}"} ?: ClosestPlayerFinder.getClosestPlayer(dragon)?.let {"with closest by player ${it.name}"} ?: "killer unknown and no player close by"}")
		// Attempt to drop items
		if (dragon.world.environment != World.Environment.THE_END) return
		dragon.world.enderDragonBattle ?: return
		for (drop in AdditionalEnderDragonDrops.drops) {
			if (drop.evaluateProbability()) event.drops.add(drop.item)
		}
	}

}