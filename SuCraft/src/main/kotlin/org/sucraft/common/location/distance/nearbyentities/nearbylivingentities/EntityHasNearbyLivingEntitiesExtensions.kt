/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location.distance.nearbyentities.nearbylivingentities

import org.bukkit.entity.Entity
import org.sucraft.common.entity.LivingEntityPredicate

// Get whether there exist living entities within chessboard distance

/**
 * @return Whether there exist
 * [living entities][LivingEntity] within the given chessboard distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
fun Entity.hasNearbyLivingEntitiesChessboardDistance(
	distance: Double?,
	predicate: LivingEntityPredicate? = null
) = getNearbyLivingEntitiesChessboardDistance(
	distance,
	predicate
).any()

/**
 * @return Whether there exist
 * [living entities][LivingEntity] within the given chessboard distances from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.hasNearbyLivingEntitiesChessboardDistance(
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
 * [living entities][LivingEntity] within the given Euclidean distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
fun Entity.hasNearbyLivingEntities(
	distance: Double?,
	predicate: LivingEntityPredicate? = null
) = getNearbyLivingEntities(
	distance,
	predicate
).any()

/**
 * @return Whether there exist
 * [living entities][LivingEntity] within the given Euclidean distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
fun Entity.hasNearbyLivingEntities(
	distance: Double?,
	predicate: LivingEntityDistancePredicate
) = getNearbyLivingEntities(
	distance,
	predicate
).any()

/**
 * @return Whether there exist
 * [living entities][LivingEntity] within the given Euclidean distances from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.hasNearbyLivingEntities(
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
 * [living entities][LivingEntity] with the given Euclidean distances from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.hasNearbyLivingEntities(
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
 * [living entities][LivingEntity] within the given distance per axis from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.hasNearbyLivingEntities(
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
 * [living entities][LivingEntity] within the given distance per axis from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.hasNearbyLivingEntities(
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