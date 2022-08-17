/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.zombiehorsejockeys

import org.bukkit.entity.Player
import org.bukkit.entity.ZombieHorse
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.sucraft.common.entity.killByDamage
import org.sucraft.common.event.on
import org.sucraft.common.location.distance.nearbyentities.nearbyplayers.hasNearbyPlayers
import org.sucraft.common.module.SuCraftComponent
import org.sucraft.modules.zombiehorsejockeys.SpawnZombieHorseJockeys.passengerTypes

/**
 * May kill a zombie horse upon its jockey rider dying,
 * to prevent zombie horses being left abandoned all over the place after a few day-night cycles
 */
object JoinZombieHorseAndRiderDeath : SuCraftComponent<ZombieHorseJockeys>() {

	// Settings

	/**
	 * If no player is within this distance from a dying zombie horse's rider
	 * the zombie horse may also not survive.
	 */
	private const val maxDistanceToCountPlayerAsCloseEnoughForHorseToSurvive = 35.0

	// Events

	init {
		on(EntityDeathEvent::class) {
			// Ignore passengers that are not a passenger of a zombie horse relevant to this module
			if (entity.type !in passengerTypes) return@on
			if (!entity.isInsideVehicle) return@on
			val horse = entity.vehicle as? ZombieHorse ?: return@on
			// Do not kill tamed horses
			if (horse.isTamed) return@on
			// Do not kill the horse if a player is nearby or the killed entity was killed by a player or other entity
			// (as opposed to being killer by sunlight)
			if (entity.lastDamageCause is EntityDamageByEntityEvent ||
				entity.killer is Player ||
				entity.hasNearbyPlayers(maxDistanceToCountPlayerAsCloseEnoughForHorseToSurvive) ||
				horse.hasNearbyPlayers(maxDistanceToCountPlayerAsCloseEnoughForHorseToSurvive)
			) return@on
			// Kill the horse in addition to the entity already dying
			horse.killByDamage()
		}
	}

}