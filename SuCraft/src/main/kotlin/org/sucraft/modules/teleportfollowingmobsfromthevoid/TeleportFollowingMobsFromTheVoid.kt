/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.teleportfollowingmobsfromthevoid

import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.sucraft.common.event.on
import org.sucraft.common.module.SuCraftModule
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDamageEvent.DamageCause.VOID
import org.sucraft.common.entity.playerBeingTeleportingFollowed
import org.sucraft.modules.teleportfollowingmobsbeforeunload.TeleportFollowingMobsBeforeUnload

/**
 * Teleports mob that belong to a player and can follow them (at least including tamed cats, parrots, wolves)
 * and are alive, to the player when they take void damage.
 *
 * This may eject them from a vehicle they are in.
 */
object TeleportFollowingMobsFromTheVoid : SuCraftModule<TeleportFollowingMobsFromTheVoid>() {

	// Implementation

	/**
	 * This implementation notably differs from
	 * [TeleportFollowingMobsBeforeUnload.teleportFollowingMobToPlayerIfPossible]
	 * in that it does not check for the entity being tethered,
	 * or the teleport being considered safe,
	 * and that it may remove the entity from a vehicle, or remove its leash.
	 *
	 * Also, passengers of the entity itself will not be ejected.
	 */
	private fun teleportFollowingMobToPlayerIfPossible(entity: Entity, player: Player) {
		try {
			if (entity.isDead || !entity.isValid) return
			// Check if the entity is in a vehicle, and if so, cancel if the entity could not leave the vehicle
			if (entity.isInsideVehicle && !entity.leaveVehicle()) return
			// Check if the entity is leashed, and if so, cancel if the entity could not be unleashed
			if (entity is LivingEntity && entity.isLeashed && !entity.setLeashHolder(null)) return
			entity.teleport(player.location)
		} catch (_: Exception) {
			// Many things could go wrong, such as chunk not being loaded properly
			// In all of those expected cases, there is nothing we can do about it,
			// we can simply not perform our desired actions, so we ignore the exception
		}
	}

	// Events

	init {
		// Listen for entity damage to teleport following mobs to the player
		on(EntityDamageEvent::class) {
			if (cause != VOID) return@on
			val player = entity.playerBeingTeleportingFollowed ?: return@on
			teleportFollowingMobToPlayerIfPossible(entity, player)
		}
	}

}