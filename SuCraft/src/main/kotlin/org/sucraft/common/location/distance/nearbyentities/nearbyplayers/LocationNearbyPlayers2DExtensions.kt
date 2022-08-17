/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location.distance.nearbyentities.nearbyplayers

import org.bukkit.Location
import org.bukkit.entity.Player
import org.sucraft.common.location.distance.distance2D
import org.sucraft.common.location.distance.distanceSquared2D
import org.sucraft.common.player.PlayerPredicate

// PlayerDistancePredicate extension

/**
 * @return This [PlayerDistancePredicate] as a [PlayerPredicate], assuming the distance
 * as the planar (xz) Euclidean distance between the given [fromLocation] and the player being tested.
 */
fun PlayerDistancePredicate.asPlayerPredicate2D(fromLocation: Location) =
	{ player: Player -> this(fromLocation.distance2D(player), player) }

// Get players within 2D chessboard distance

/**
 * @return All [players][Player] within the given planar (xz) chessboard distance from this [Location].
 * A null distance is assumed to be infinite.
 */
fun Location.getNearbyPlayersChessboardDistance2D(
	distance: Double?,
	predicate: PlayerPredicate? = null
) = getNearbyPlayersChessboardDistance(
	xzDistance = distance,
	yDistance = null,
	predicate
)

// Get players within 2D Euclidean distance

/**
 * @return All [players][Player] within the given planar (xz) Euclidean distance from this [Location].
 * A null distance is assumed to be infinite.
 */
fun Location.getNearbyPlayers2D(
	distance: Double?,
	predicate: PlayerPredicate? = null
) = getNearbyPlayers(
	xzDistance = distance,
	yDistance = null,
	predicate
)

/**
 * @return All [players][Player] within the given planar (xz) Euclidean distance from this [Location].
 * A null distance is assumed to be infinite.
 */
fun Location.getNearbyPlayers2D(
	distance: Double?,
	predicate: PlayerDistancePredicate
) = getNearbyPlayers2D(
	distance = distance,
	predicate.asPlayerPredicate2D(this)
)

/**
 * @return All [players][Player] within the given distance per planar (xz) axis from this [Location].
 * Any null distance is assumed to be infinite.
 */
fun Location.getNearbyPlayers2D(
	xDistance: Double?,
	zDistance: Double?,
	predicate: PlayerPredicate? = null
) = getNearbyPlayers(
	xDistance = xDistance,
	yDistance = null,
	zDistance = zDistance,
	predicate
)

/**
 * @return All [players][Player] within the given distance per planar (xz) axis from this [Location].
 * Any null distance is assumed to be infinite.
 */
fun Location.getNearbyPlayers2D(
	xDistance: Double?,
	zDistance: Double?,
	predicate: PlayerDistancePredicate
) = getNearbyPlayers2D(
	xDistance = xDistance,
	zDistance = zDistance,
	predicate.asPlayerPredicate2D(this)
)

// Get the closest player out of a selection of players

/**
 * @return The closest (in the xz plane) [player][Player] in planar (xz) distance out of the given [players],
 * or null if the sequence is empty.
 */
fun Location.getClosestPlayer2D(players: Sequence<Player>) =
	players.minByOrNull { distanceSquared2D(it) }

// Get the closest player, optionally within a Euclidean distance

/**
 * @return The closest (in the xz plane) [player][Player]
 * with at most the given planar (xz) Euclidean distance from this [Location],
 * or null if none exists.
 * A null distance is assumed to be infinite.
 */
fun Location.getClosestPlayer2D(
	maxDistance: Double? = null,
	predicate: PlayerPredicate? = null
) = getClosestPlayer(
	maxXZDistance = maxDistance,
	maxYDistance = null,
	predicate
)

/**
 * @return The closest (in the xz plane) [player][Player]
 * with at most the given planar (xz) Euclidean distance from this [Location],
 * or null if none exists.
 * A null distance is assumed to be infinite.
 */
fun Location.getClosestPlayer2D(
	maxDistance: Double? = null,
	predicate: PlayerDistancePredicate
) = getClosestPlayer2D(
	maxDistance = maxDistance,
	predicate.asPlayerPredicate2D(this)
)

/**
 * @return The closest (in the xz plane) [player][Player]
 * with at most the given distance per planar (xz) axis from this [Location],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Location.getClosestPlayer2D(
	maxXDistance: Double?,
	maxZDistance: Double?,
	predicate: PlayerPredicate? = null
) = getClosestPlayer(
	maxXDistance = maxXDistance,
	maxYDistance = null,
	maxZDistance = maxZDistance,
	predicate
)

/**
 * @return The closest (in the xz plane) [player][Player]
 * with at most the given distance per planar (xz) axis from this [Location],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Location.getClosestPlayer2D(
	maxXDistance: Double?,
	maxZDistance: Double?,
	predicate: PlayerDistancePredicate
) = getClosestPlayer2D(
	maxXDistance = maxXDistance,
	maxZDistance = maxZDistance,
	predicate.asPlayerPredicate2D(this)
)