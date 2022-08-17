/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location.distance.nearbyentities.nearbyplayers

import org.bukkit.entity.Entity
import org.sucraft.common.player.PlayerPredicate

// Get whether there exist players within chessboard distance

/**
 * @return Whether there exist
 * [players][Player] within the given chessboard distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
fun Entity.hasNearbyPlayersChessboardDistance(
	distance: Double?,
	predicate: PlayerPredicate? = null
) = getNearbyPlayersChessboardDistance(
	distance,
	predicate
).any()

/**
 * @return Whether there exist
 * [players][Player] within the given chessboard distances from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.hasNearbyPlayersChessboardDistance(
	xzDistance: Double?,
	yDistance: Double?,
	predicate: PlayerPredicate? = null
) = getNearbyPlayersChessboardDistance(
	xzDistance,
	yDistance,
	predicate
).any()

// Get whether there exist players within Euclidean distance

/**
 * @return Whether there exist
 * [players][Player] within the given Euclidean distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
fun Entity.hasNearbyPlayers(
	distance: Double?,
	predicate: PlayerPredicate? = null
) = getNearbyPlayers(
	distance,
	predicate
).any()

/**
 * @return Whether there exist
 * [players][Player] within the given Euclidean distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
fun Entity.hasNearbyPlayers(
	distance: Double?,
	predicate: PlayerDistancePredicate
) = getNearbyPlayers(
	distance,
	predicate
).any()

/**
 * @return Whether there exist
 * [players][Player] within the given Euclidean distances from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.hasNearbyPlayers(
	xzDistance: Double?,
	yDistance: Double?,
	predicate: PlayerPredicate? = null
) = getNearbyPlayers(
	xzDistance,
	yDistance,
	predicate
).any()

/**
 * @return Whether there exist
 * [players][Player] with the given Euclidean distances from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.hasNearbyPlayers(
	xzDistance: Double?,
	yDistance: Double?,
	predicate: PlayerDistancePredicate
) = getNearbyPlayers(
	xzDistance,
	yDistance,
	predicate
).any()

/**
 * @return Whether there exist
 * [players][Player] within the given distance per axis from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.hasNearbyPlayers(
	xDistance: Double?,
	yDistance: Double?,
	zDistance: Double?,
	predicate: PlayerPredicate? = null
) = getNearbyPlayers(
	xDistance,
	yDistance,
	zDistance,
	predicate
).any()

/**
 * @return Whether there exist
 * [players][Player] within the given distance per axis from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.hasNearbyPlayers(
	xDistance: Double?,
	yDistance: Double?,
	zDistance: Double?,
	predicate: PlayerDistancePredicate
) = getNearbyPlayers(
	xDistance,
	yDistance,
	zDistance,
	predicate
).any()