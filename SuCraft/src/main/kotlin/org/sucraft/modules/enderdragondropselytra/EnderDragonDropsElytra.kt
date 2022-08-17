/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.enderdragondropselytra

import org.bukkit.GameMode.SURVIVAL
import org.bukkit.Material.ELYTRA
import org.bukkit.Statistic.KILL_ENTITY
import org.bukkit.World.Environment.THE_END
import org.bukkit.entity.EnderDragon
import org.bukkit.entity.EntityType.ENDER_DRAGON
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.inventory.ItemStack
import org.sucraft.common.event.on
import org.sucraft.common.location.distance.nearbyentities.nearbyplayers.getClosestPlayer
import org.sucraft.common.math.testProbability
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.text.INTERESTING_EVENT
import org.sucraft.common.text.component
import org.sucraft.common.text.times

/**
 * Adds elytra drops from the ender dragon.
 */
object EnderDragonDropsElytra : SuCraftModule<EnderDragonDropsElytra>() {

	// Settings

	/**
	 * Get the chance for an elytra to drop, based on the number of dragon kills the killer already had.
	 */
	private fun getChanceForElytraDrop(numberOfDragonKills: Int?): Double = when (numberOfDragonKills) {
		0 -> 1.0
		in 1..2 -> 0.3
		in 3..5 -> 0.2
		in 6..10 -> 0.1
		else -> 0.05
	}

	/**
	 * The player that killed the dragon will be notified if an elytra is dropped if they are within this distance.
	 */
	private const val maxDistanceToDragonToGetNotifiedOfElytra = 200

	/**
	 * The message that is sent to the dragon killer if an elytra is dropped.
	 */
	private val elytraDroppedMessage = component(color = INTERESTING_EVENT) {
		+"An" + ELYTRA.itemRarity.color * "Elytra" + "dropped from the Ender Dragon!"
	}

	// Events

	init {
		// Listen to entity deaths to potentially drop an elytra if an ender dragon dies
		on(EntityDeathEvent::class) {

			// We don't do anything if the entity that died is not an ender dragon
			val dragon = (entity as? EnderDragon) ?: return@on

			// Check that this is the regular dragon and not a glitched one
			if (dragon.world.environment != THE_END) return@on
			if (dragon.world.enderDragonBattle == null) return@on

			// Possibly drop an elytra
			val mostLikelyKiller = dragon.killer ?: dragon.getClosestPlayer()
			// If we can't find the most likely killer, or the most likely killer is not in survival mode, do nothing
			if (mostLikelyKiller == null || mostLikelyKiller.gameMode != SURVIVAL) return@on
			val dragonKills = mostLikelyKiller.getStatistic(KILL_ENTITY, ENDER_DRAGON)
			val chanceForElytra = getChanceForElytraDrop(dragonKills)
			if (testProbability(chanceForElytra)) {
				// Log to console
				info(
					"Dropping an elytra for ${mostLikelyKiller.name}, " +
							"beating the odds of $chanceForElytra based on $dragonKills existing dragon kills"
				)
				// Add the elytra to the dragon drops
				drops.add(ItemStack(ELYTRA))
				//
				mostLikelyKiller
					.takeIf { it.location.distance(dragon.location) <= maxDistanceToDragonToGetNotifiedOfElytra }
					?.sendMessage(
						elytraDroppedMessage
					)
			}

		}
	}


}