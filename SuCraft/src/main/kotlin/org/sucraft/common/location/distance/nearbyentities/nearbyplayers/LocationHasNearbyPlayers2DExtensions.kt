/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location.distance.nearbyentities.nearbyplayers

import org.bukkit.Location
import org.sucraft.common.player.PlayerPredicate

// Get whether there exist players within 2D chessboard distance

/**
 * @return Whether there exist
 * [players][Player] within the given planar (xz) chessboard distance from this [Location].
 * A null distance is assumed to be infinite.
 */
fun Location.hasNearbyPlayersChessboardDistance2D(
	distance: Double?,
	predicate: PlayerPredicate? = null
) = getNearbyPlayersChessboardDistance2D(
	distance,
	predicate
).any()

// Get whether there exist players within 2D Euclidean distance

/**
 * @return Whether there exist
 * [players][Player] within the given planar (xz) Euclidean distance from this [Location].
 * A null distance is assumed to be infinite.
 */
fun Location.hasNearbyPlayers2D(
	distance: Double?,
	predicate: PlayerPredicate? = null
) = getNearbyPlayers2D(
	distance,
	predicate
).any()

/**
 * @return Whether there exist
 * [players][Player] within the given planar (xz) Euclidean distance from this [Location].
 * A null distance is assumed to be infinite.
 */
fun Location.hasNearbyPlayers2D(
	distance: Double?,
	predicate: PlayerDistancePredicate
) = getNearbyPlayers2D(
	distance,
	predicate
).any()

/**
 * @return Whether there exist
 * [players][Player] within the given distance per planar (xz) axis from this [Location].
 * Any null distance is assumed to be infinite.
 */
fun Location.hasNearbyPlayers2D(
	xDistance: Double?,
	zDistance: Double?,
	predicate: PlayerPredicate? = null
) = getNearbyPlayers2D(
	xDistance,
	zDistance,
	predicate
).any()

/**
 * @return Whether there exist
 * [players][Player] within the given distance per planar (xz) axis from this [Location].
 * Any null distance is assumed to be infinite.
 */
fun Location.hasNearbyPlayers2D(
	xDistance: Double?,
	zDistance: Double?,
	predicate: PlayerDistancePredicate
) = getNearbyPlayers2D(
	xDistance,
	zDistance,
	predicate
).any()