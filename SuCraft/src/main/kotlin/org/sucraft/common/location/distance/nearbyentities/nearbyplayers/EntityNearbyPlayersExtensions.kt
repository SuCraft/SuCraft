/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location.distance.nearbyentities.nearbyplayers

import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.sucraft.common.player.PlayerPredicate

// Get players within chessboard distance

/**
 * @return All [players][Player] within the given chessboard distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
fun Entity.getNearbyPlayersChessboardDistance(
	distance: Double?,
	predicate: PlayerPredicate? = null
) = location.getNearbyPlayersChessboardDistance(
	distance,
	predicate
)

/**
 * @return All [players][Player] within the given chessboard distances from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.getNearbyPlayersChessboardDistance(
	xzDistance: Double?,
	yDistance: Double?,
	predicate: PlayerPredicate? = null
) = location.getNearbyPlayersChessboardDistance(
	xzDistance,
	yDistance,
	predicate
)

// Get players within Euclidean distance

/**
 * @return All [players][Player] within the given Euclidean distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
fun Entity.getNearbyPlayers(
	distance: Double?,
	predicate: PlayerPredicate? = null
) = location.getNearbyPlayers(
	distance,
	predicate
)

/**
 * @return All [players][Player] within the given Euclidean distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
fun Entity.getNearbyPlayers(
	distance: Double?,
	predicate: PlayerDistancePredicate
) = location.getNearbyPlayers(
	distance,
	predicate
)

/**
 * @return All [players][Player] within the given Euclidean distances from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.getNearbyPlayers(
	xzDistance: Double?,
	yDistance: Double?,
	predicate: PlayerPredicate? = null
) = location.getNearbyPlayers(
	xzDistance,
	yDistance,
	predicate
)

/**
 * @return All [players][Player] with the given Euclidean distances from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.getNearbyPlayers(
	xzDistance: Double?,
	yDistance: Double?,
	predicate: PlayerDistancePredicate
) = location.getNearbyPlayers(
	xzDistance,
	yDistance,
	predicate
)

/**
 * @return All [players][Player] within the given distance per axis from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.getNearbyPlayers(
	xDistance: Double?,
	yDistance: Double?,
	zDistance: Double?,
	predicate: PlayerPredicate? = null
) = location.getNearbyPlayers(
	xDistance,
	yDistance,
	zDistance,
	predicate
)

/**
 * @return All [players][Player] within the given distance per axis from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.getNearbyPlayers(
	xDistance: Double?,
	yDistance: Double?,
	zDistance: Double?,
	predicate: PlayerDistancePredicate
) = location.getNearbyPlayers(
	xDistance,
	yDistance,
	zDistance,
	predicate
)

// Get the closest player out of a selection of players

/**
 * @return The closest [player][Player] out of the given [players],
 * or null if the sequence is empty.
 */
fun Entity.getClosestPlayer(players: Sequence<Player>) =
	location.getClosestPlayer(players)

// Get the closest player, optionally within a Euclidean distance

/**
 * @return The closest [player][Player] with at most the given Euclidean distance from this [Entity],
 * or null if none exists.
 * A null distance is assumed to be infinite.
 */
fun Entity.getClosestPlayer(
	maxDistance: Double? = null,
	predicate: PlayerPredicate? = null
) = location.getClosestPlayer(
	maxDistance,
	predicate
)

/**
 * @return The closest [player][Player] with at most the given Euclidean distance from this [Entity],
 * or null if none exists.
 * A null distance is assumed to be infinite.
 */
fun Entity.getClosestPlayer(
	maxDistance: Double? = null,
	predicate: PlayerDistancePredicate
) = location.getClosestPlayer(
	maxDistance,
	predicate
)

/**
 * @return The closest [player][Player] with at most the given Euclidean distances from this [Entity],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Entity.getClosestPlayer(
	maxXZDistance: Double?,
	maxYDistance: Double?,
	predicate: PlayerPredicate? = null
) = location.getClosestPlayer(
	maxXZDistance,
	maxYDistance,
	predicate
)

/**
 * @return The closest [player][Player] with at most the given Euclidean distances from this [Entity],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Entity.getClosestPlayer(
	maxXZDistance: Double?,
	maxYDistance: Double?,
	predicate: PlayerDistancePredicate
) = location.getClosestPlayer(
	maxXZDistance,
	maxYDistance,
	predicate
)

/**
 * @return The closest [player][Player] with at most the given distance per axis from this [Entity],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Entity.getClosestPlayer(
	maxXDistance: Double?,
	maxYDistance: Double?,
	maxZDistance: Double?,
	predicate: PlayerPredicate? = null
) = location.getClosestPlayer(
	maxXDistance,
	maxYDistance,
	maxZDistance,
	predicate
)

/**
 * @return The closest [player][Player] with at most the given distance per axis from this [Entity],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Entity.getClosestPlayer(
	maxXDistance: Double?,
	maxYDistance: Double?,
	maxZDistance: Double?,
	predicate: PlayerDistancePredicate
) = location.getClosestPlayer(
	maxXDistance,
	maxYDistance,
	maxZDistance,
	predicate
)