/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location.distance.nearbyentities

import org.bukkit.Location
import org.bukkit.entity.Entity
import org.sucraft.common.entity.EntityPredicate

// Get whether there exist entities within chessboard distance

/**
 * @return Whether there exist
 * [entities][Entity] within the given chessboard distance from this [Location].
 * A null distance is assumed to be infinite.
 */
fun Location.hasNearbyEntitiesChessboardDistance(
	distance: Double?,
	predicate: EntityPredicate? = null
) = getNearbyEntitiesChessboardDistance(
	distance,
	predicate
).any()

/**
 * @return Whether there exist
 * [entities][Entity] within the given chessboard distances from this [Location].
 * Any null distance is assumed to be infinite.
 */
fun Location.hasNearbyEntitiesChessboardDistance(
	xzDistance: Double?,
	yDistance: Double?,
	predicate: EntityPredicate? = null
) = getNearbyEntitiesChessboardDistance(
	xzDistance,
	yDistance,
	predicate
).any()

// Get whether there exist entities within Euclidean distance

/**
 * @return Whether there exist
 * [entities][Entity] within the given Euclidean distance from this [Location].
 * A null distance is assumed to be infinite.
 */
fun Location.hasNearbyEntities(
	distance: Double?,
	predicate: EntityPredicate? = null
) = getNearbyEntities(
	distance,
	predicate
).any()

/**
 * @return Whether there exist
 * [entities][Entity] within the given Euclidean distance from this [Location].
 * A null distance is assumed to be infinite.
 */
fun Location.hasNearbyEntities(
	distance: Double?,
	predicate: EntityDistancePredicate
) = getNearbyEntities(
	distance,
	predicate
).any()

/**
 * @return Whether there exist
 * [entities][Entity] within the given Euclidean distances from this [Location].
 * Any null distance is assumed to be infinite.
 */
fun Location.hasNearbyEntities(
	xzDistance: Double?,
	yDistance: Double?,
	predicate: EntityPredicate? = null
) = getNearbyEntities(
	xzDistance,
	yDistance,
	predicate
).any()

/**
 * @return Whether there exist
 * [entities][Entity] with the given Euclidean distances from this [Location].
 * Any null distance is assumed to be infinite.
 */
fun Location.hasNearbyEntities(
	xzDistance: Double?,
	yDistance: Double?,
	predicate: EntityDistancePredicate
) = getNearbyEntities(
	xzDistance,
	yDistance,
	predicate
).any()

/**
 * @return Whether there exist
 * [entities][Entity] within the given distance per axis from this [Location].
 * Any null distance is assumed to be infinite.
 */
fun Location.hasNearbyEntities(
	xDistance: Double?,
	yDistance: Double?,
	zDistance: Double?,
	predicate: EntityPredicate? = null
) = getNearbyEntities(
	xDistance,
	yDistance,
	zDistance,
	predicate
).any()

/**
 * @return Whether there exist
 * [entities][Entity] within the given distance per axis from this [Location].
 * Any null distance is assumed to be infinite.
 */
fun Location.hasNearbyEntities(
	xDistance: Double?,
	yDistance: Double?,
	zDistance: Double?,
	predicate: EntityDistancePredicate
) = getNearbyEntities(
	xDistance,
	yDistance,
	zDistance,
	predicate
).any()