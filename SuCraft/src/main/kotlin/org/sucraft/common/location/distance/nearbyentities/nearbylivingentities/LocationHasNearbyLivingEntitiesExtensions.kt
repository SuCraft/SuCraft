/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location.distance.nearbyentities.nearbylivingentities

import org.bukkit.Location
import org.sucraft.common.entity.LivingEntityPredicate

// Get whether there exist living entities within chessboard distance

/**
 * @return Whether there exist
 * [living entities][LivingEntity] within the given chessboard distance from this [Location].
 * A null distance is assumed to be infinite.
 */
fun Location.hasNearbyLivingEntitiesChessboardDistance(
	distance: Double?,
	predicate: LivingEntityPredicate? = null
) = getNearbyLivingEntitiesChessboardDistance(
	distance,
	predicate
).any()

/**
 * @return Whether there exist
 * [living entities][LivingEntity] within the given chessboard distances from this [Location].
 * Any null distance is assumed to be infinite.
 */
fun Location.hasNearbyLivingEntitiesChessboardDistance(
	xzDistance: Double?,
	yDistance: Double?,
	predicate: LivingEntityPredicate? = null
) = getNearbyLivingEntitiesChessboardDistance(
	xzDistance,
	yDistance,
	predicate
).any()

// Get whether there exist living entities within Euclidean distance

/**
 * @return Whether there exist
 * [living entities][LivingEntity] within the given Euclidean distance from this [Location].
 * A null distance is assumed to be infinite.
 */
fun Location.hasNearbyLivingEntities(
	distance: Double?,
	predicate: LivingEntityPredicate? = null
) = getNearbyLivingEntities(
	distance,
	predicate
).any()

/**
 * @return Whether there exist
 * [living entities][LivingEntity] within the given Euclidean distance from this [Location].
 * A null distance is assumed to be infinite.
 */
fun Location.hasNearbyLivingEntities(
	distance: Double?,
	predicate: LivingEntityDistancePredicate
) = getNearbyLivingEntities(
	distance,
	predicate
).any()

/**
 * @return Whether there exist
 * [living entities][LivingEntity] within the given Euclidean distances from this [Location].
 * Any null distance is assumed to be infinite.
 */
fun Location.hasNearbyLivingEntities(
	xzDistance: Double?,
	yDistance: Double?,
	predicate: LivingEntityPredicate? = null
) = getNearbyLivingEntities(
	xzDistance,
	yDistance,
	predicate
).any()

/**
 * @return Whether there exist
 * [living entities][LivingEntity] with the given Euclidean distances from this [Location].
 * Any null distance is assumed to be infinite.
 */
fun Location.hasNearbyLivingEntities(
	xzDistance: Double?,
	yDistance: Double?,
	predicate: LivingEntityDistancePredicate
) = getNearbyLivingEntities(
	xzDistance,
	yDistance,
	predicate
).any()

/**
 * @return Whether there exist
 * [living entities][LivingEntity] within the given distance per axis from this [Location].
 * Any null distance is assumed to be infinite.
 */
fun Location.hasNearbyLivingEntities(
	xDistance: Double?,
	yDistance: Double?,
	zDistance: Double?,
	predicate: LivingEntityPredicate? = null
) = getNearbyLivingEntities(
	xDistance,
	yDistance,
	zDistance,
	predicate
).any()

/**
 * @return Whether there exist
 * [living entities][LivingEntity] within the given distance per axis from this [Location].
 * Any null distance is assumed to be infinite.
 */
fun Location.hasNearbyLivingEntities(
	xDistance: Double?,
	yDistance: Double?,
	zDistance: Double?,
	predicate: LivingEntityDistancePredicate
) = getNearbyLivingEntities(
	xDistance,
	yDistance,
	zDistance,
	predicate
).any()