/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.enderdragondrops.listener

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.JoinConfiguration
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.Statistic
import org.bukkit.World
import org.bukkit.entity.EnderDragon
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.inventory.ItemStack
import org.sucraft.core.common.bukkit.player.ClosestPlayerFinder
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.enderdragondrops.main.SuCraftEnderDragonDropsPlugin


object EnderDragonDeathListener : SuCraftComponent<SuCraftEnderDragonDropsPlugin>(SuCraftEnderDragonDropsPlugin.getInstance()) {

	// Settings

	private fun getChanceForElytraDrop(numberOfDragonKills: Int?): Double = when(numberOfDragonKills) {
		0 -> 1.0
		in 1..2 -> 0.3
		in 3..5 -> 0.2
		in 6..10 -> 0.1
		else -> 0.05
	}

	private const val maxDistanceToDragonToGetNotifiedOfElytra = 200

	// Events

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onEntityDeath(event: EntityDeathEvent) {

		val dragon = (event.entity as? EnderDragon) ?: return

		// Log to console
		logger.info("An ender dragon died at ${dragon.location} (${dragon.killer?.let {"killed by ${it.name}"} ?: ClosestPlayerFinder.getClosestPlayer(dragon)?.let {"with closest by player ${it.name}"} ?: "killer unknown and no player close by"})")

		// Check that this is the regular dragon and not a glitched one
		if (dragon.world.environment != World.Environment.THE_END) return
		dragon.world.enderDragonBattle ?: return

		// Possibly drop the elytra
		val mostLikelyKiller = dragon.killer ?: ClosestPlayerFinder.getClosestPlayer(dragon)
		if (mostLikelyKiller != null && mostLikelyKiller.gameMode != GameMode.SURVIVAL) return
		val dragonKills = mostLikelyKiller?.getStatistic(Statistic.KILL_ENTITY, EntityType.ENDER_DRAGON)
		val chanceForElytra = getChanceForElytraDrop(dragonKills)
		if (Math.random() < chanceForElytra) {
			logger.info("Dropping an elytra for ${mostLikelyKiller?.name}, beating the odds of $chanceForElytra based on $dragonKills existing dragon kills")
			event.drops.add(ItemStack(Material.ELYTRA))
			mostLikelyKiller?.takeIf { it.location.distance(dragon.location) <= maxDistanceToDragonToGetNotifiedOfElytra }?.sendMessage(
				Component.join(
					JoinConfiguration.noSeparators(),
					Component.text("An "),
					Component.text("Elytra").color(NamedTextColor.YELLOW),
					Component.text(" dropped from the Ender Dragon!")
				).color(NamedTextColor.WHITE)
			)
		}
	}

}