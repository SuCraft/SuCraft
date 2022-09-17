/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.teleportfollowingmobsbeforeunload

import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.World.Environment.NORMAL
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.entity.Sittable
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.world.EntitiesUnloadEvent
import org.sucraft.common.entity.getEntitiesTeleportingFollowingPlayer
import org.sucraft.common.entity.playerBeingTeleportingFollowed
import org.sucraft.common.event.on
import org.sucraft.common.module.SuCraftModule

/**
 * Teleports mob that belong to and follow a player (at least including tamed cats, parrots, wolves)
 * but are not tethered in some way (leashed, sitting, in a vehicle)
 * and are alive, to the player they are following before they are unloaded if their chunk is unloaded.
 *
 * Because the chunks loaded when the player joins again after leaving may be different, also teleports these mobs
 * to the player when they leave the server.
 */
object TeleportFollowingMobsBeforeUnload : SuCraftModule<TeleportFollowingMobsBeforeUnload>() {

	// Settings

	private const val minimumDistanceToTeleportFollowingMobToPlayerOnQuit = 16.0

	// Data

	/**
	 * Live setting, can be changed at any time to temporarily not check mobs when chunks are unloaded.
	 */
	var checkOnChunkUnload = true

	// Implementation

	/**
	 * @return A crude estimation of whether it is okay to teleport a following mob to a player,
	 * depending on the location it comes from and that it will teleport to.
	 *
	 * Specifically, we don't teleport the mob to non-overworld worlds if they are coming from a
	 * different environment.
	 */
	private fun isSafeToTeleport(from: Location, to: Location): Boolean {
		try {
			if (to.world.environment != NORMAL && from.world.environment != to.world.environment) {
				return false
			}
			return true
		} catch (_: Exception) {
			// If something goes wrong here, a world may not be loaded, and we judge the teleport unsafe just in case
			return false
		}
	}

	/**
	 * @return A crude estimation of whether the entity is tethered in some way that does not lend itself
	 * to being teleported suddenly.
	 */
	private fun isTethered(entity: Entity): Boolean {
		if (entity.isInsideVehicle) return true
		if (entity is LivingEntity && entity.isLeashed) return true
		if (entity is Sittable && entity.isSitting) return true
		return false
	}

	private fun teleportFollowingMobsInChunkToPlayers(chunk: Chunk, entities: List<Entity>?) {
		try {
			// We don't want to call for loading the chunk again
			// if it is unloaded (should not happen here but asynchronous chunk loading is unpredictable)
			if (entities == null && !chunk.isEntitiesLoaded) return
			(entities?.asSequence() ?: chunk.entities.asSequence())
				.filter { !it.isDead && it.isValid }
				.mapNotNull { entity -> entity.playerBeingTeleportingFollowed?.let { player -> entity to player } }
				.toList().forEach { (entity, player) ->
					teleportFollowingMobToPlayerIfPossible(entity, player)
				}
		} catch (_: Exception) {
			// Many things could go wrong, such as chunk not being loaded properly
			// In all of those expected cases, there is nothing we can do about it,
			// we can simply not perform our desired actions, so we ignore the exception
		}
	}

	fun teleportFollowingMobToPlayerIfPossible(entity: Entity, player: Player) {
		try {
			if (entity.isDead || !entity.isValid) return
			if (isTethered(entity)) return
			if (!isSafeToTeleport(entity.location, player.location)) return
			entity.teleport(player.location)
		} catch (_: Exception) {
			// Many things could go wrong, such as chunk not being loaded properly
			// In all of those expected cases, there is nothing we can do about it,
			// we can simply not perform our desired actions, so we ignore the exception
		}
	}

	// Events

	init {

		// Listen for chunk unloads to teleport mobs in the chunk to the player
		// Temporarily disabled because this seems unnecessary (and computationally costly)
		// if we are also listening to EntitiesUnloadEvent
//		on(ChunkUnloadEvent::class) {
//			teleportFollowingMobsInChunkToPlayers(chunk, null)
//		}
		// Listen for chunk entities unloads to teleport mobs in the chunk to the player
		on(EntitiesUnloadEvent::class) {
			if (!checkOnChunkUnload) return@on
			teleportFollowingMobsInChunkToPlayers(chunk, entities)
		}

		// Teleport any following mobs to a player when they leave, but only those that are nearby
		// (we simply check all entities in that world now)
		on(PlayerQuitEvent::class) {
			player.world.getEntitiesTeleportingFollowingPlayer(player).forEach {
				// Don't do anything if the entity is right next to the player
				try {
					if (it.location.distance(player.location) <= minimumDistanceToTeleportFollowingMobToPlayerOnQuit)
						return@forEach
				} catch (_: Exception) {
					return@forEach
				}
				teleportFollowingMobToPlayerIfPossible(it, player)
			}
		}

	}

}