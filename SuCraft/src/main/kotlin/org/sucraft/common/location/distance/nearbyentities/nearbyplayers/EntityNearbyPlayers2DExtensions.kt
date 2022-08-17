/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location.distance.nearbyentities.nearbyplayers

import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.sucraft.common.player.PlayerPredicate

// Get players within 2D chessboard distance

/**
 * @return All [players][Player] within the given planar (xz) chessboard distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
fun Entity.getNearbyPlayersChessboardDistance2D(
	distance: Double?,
	predicate: PlayerPredicate? = null
) = location.getNearbyPlayersChessboardDistance2D(
	distance,
	predicate
)

// Get players within 2D Euclidean distance

/**
 * @return All [players][Player] within the given planar (xz) Euclidean distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
fun Entity.getNearbyPlayers2D(
	distance: Double?,
	predicate: PlayerPredicate? = null
) = location.getNearbyPlayers2D(
	distance,
	predicate
)

/**
 * @return All [players][Player] within the given planar (xz) Euclidean distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
fun Entity.getNearbyPlayers2D(
	distance: Double?,
	predicate: PlayerDistancePredicate
) = location.getNearbyPlayers2D(
	distance,
	predicate
)

/**
 * @return All [players][Player] within the given distance per planar (xz) axis from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.getNearbyPlayers2D(
	xDistance: Double?,
	zDistance: Double?,
	predicate: PlayerPredicate? = null
) = location.getNearbyPlayers2D(
	xDistance,
	zDistance,
	predicate
)

/**
 * @return All [players][Player] within the given distance per planar (xz) axis from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.getNearbyPlayers2D(
	xDistance: Double?,
	zDistance: Double?,
	predicate: PlayerDistancePredicate
) = location.getNearbyPlayers2D(
	xDistance,
	zDistance,
	predicate
)

// Get the closest player out of a selection of players

/**
 * @return The closest (in the xz plane) [player][Player] in planar (xz) distance out of the given [players],
 * or null if the sequence is empty.
 */
fun Entity.getClosestPlayer2D(players: Sequence<Player>) =
	location.getClosestPlayer2D(players)

// Get the closest player, optionally within a Euclidean distance

/**
 * @return The closest (in the xz plane) [player][Player]
 * with at most the given planar (xz) Euclidean distance from this [Entity],
 * or null if none exists.
 * A null distance is assumed to be infinite.
 */
fun Entity.getClosestPlayer2D(
	maxDistance: Double? = null,
	predicate: PlayerPredicate? = null
) = location.getClosestPlayer2D(
	maxDistance,
	predicate
)

/**
 * @return The closest (in the xz plane) [player][Player]
 * with at most the given planar (xz) Euclidean distance from this [Entity],
 * or null if none exists.
 * A null distance is assumed to be infinite.
 */
fun Entity.getClosestPlayer2D(
	maxDistance: Double? = null,
	predicate: PlayerDistancePredicate
) = location.getClosestPlayer2D(
	maxDistance,
	predicate
)

/**
 * @return The closest (in the xz plane) [player][Player]
 * with at most the given distance per planar (xz) axis from this [Entity],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Entity.getClosestPlayer2D(
	maxXDistance: Double?,
	maxZDistance: Double?,
	predicate: PlayerPredicate? = null
) = location.getClosestPlayer2D(
	maxXDistance,
	maxZDistance,
	predicate
)

/**
 * @return The closest (in the xz plane) [player][Player]
 * with at most the given distance per planar (xz) axis from this [Entity],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Entity.getClosestPlayer2D(
	maxXDistance: Double?,
	maxZDistance: Double?,
	predicate: PlayerDistancePredicate
) = location.getClosestPlayer2D(
	maxXDistance,
	maxZDistance,
	predicate
)