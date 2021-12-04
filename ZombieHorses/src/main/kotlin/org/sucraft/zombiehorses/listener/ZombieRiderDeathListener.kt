/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.zombiehorses.listener

import org.bukkit.entity.Player
import org.bukkit.entity.Zombie
import org.bukkit.entity.ZombieHorse
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.sucraft.core.common.bukkit.player.ClosestPlayerFinder
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.zombiehorses.main.SuCraftZombieHorsesPlugin


object ZombieRiderDeathListener : SuCraftComponent<SuCraftZombieHorsesPlugin>(SuCraftZombieHorsesPlugin.getInstance()) {

	// Settings

	private const val maxDistanceToCountAsPlayerCloseEnough = 35

	// Events

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onEntityDeath(event: EntityDeathEvent) {
		val zombie = event.entity as? Zombie ?: return
		if (!zombie.isInsideVehicle) return
		val horse = zombie.vehicle as? ZombieHorse ?: return
		if (horse.isTamed) return
		val isPlayerCloseEnough =
			zombie.lastDamageCause is EntityDamageByEntityEvent
					|| zombie.killer is Player
					|| ClosestPlayerFinder.getClosestPlayer(zombie) { it.location.distance(zombie.location) <= maxDistanceToCountAsPlayerCloseEnough } != null
		if (isPlayerCloseEnough) return
		// Kill the horse too
		horse.damage(horse.health * 2)
	}

}