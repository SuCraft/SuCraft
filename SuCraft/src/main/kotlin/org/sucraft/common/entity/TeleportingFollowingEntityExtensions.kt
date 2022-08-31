/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.entity

import org.bukkit.World
import org.bukkit.entity.*
import org.sucraft.common.player.getOnlinePlayer
import kotlin.reflect.KClass

/**
 * The player this entity is actively [teleporting following][canTeleportingFollowPlayer].
 *
 * Will be null if any of the following if:
 * - this entity cannot teleporting follow players,
 * - the player this entity would teleporting follow is not online,
 * - this entity cannot teleporting follow at the moment (because it is sitting, leashed, etc.).
 *
 * If this entity is loaded but very far away from the player (even in a different world) this may return
 * the non-null player, as this entity could teleport at any moment.
 */
val Entity.playerBeingTeleportingFollowed
	get() = playerUUIDWouldTeleportingFollow?.getOnlinePlayer()

/**
 * The UUID of the player (regardless if the player is online) that this entity would
 * [teleporting follow][canTeleportingFollowPlayer] (regardless of whether this entity is currently
 * following the player, the player may for example be offline, out of range, or this entity may be stopped from
 * being able to teleporting follow the player in some way, such as being in the sitting state or being leashed).
 */
val Entity.playerUUIDWouldTeleportingFollow
	get() = when (this) {
		is Wolf, is Cat, is Parrot -> (this as? Tameable)?.owner?.uniqueId
		else -> null
	}

/**
 * Whether this entity is capable of teleporting following players. This happens when an entity follows the player
 * around, and is able to teleport to the player if they go out of a certain range.
 */
val Entity.canTeleportingFollowPlayer
	get() = when (this) {
		is Wolf, is Cat, is Parrot -> true
		else -> false
	}

fun World.getEntitiesTeleportingFollowingPlayer(player: Player) =
	getEntitiesThatCanTeleportingFollowPlayer().filter { it.playerUUIDWouldTeleportingFollow == player.uniqueId }

fun World.getEntitiesThatCanTeleportingFollowPlayer() =
	sequenceOf<KClass<out Entity>>(Wolf::class, Cat::class, Parrot::class)
		.flatMap { getEntitiesByClass(it.java).asSequence() }