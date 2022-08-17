/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location.distance.nearbyentities.nearbylivingentities

import org.bukkit.entity.Entity
import org.sucraft.common.entity.LivingEntityPredicate

// Get whether there exist living entities within 2D chessboard distance

/**
 * @return Whether there exist
 * [living entities][LivingEntity] within the given planar (xz) chessboard distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
fun Entity.hasNearbyLivingEntitiesChessboardDistance2D(
	distance: Double?,
	predicate: LivingEntityPredicate? = null
) = getNearbyLivingEntitiesChessboardDistance2D(
	distance,
	predicate
).any()

// Get whether there exist living entities within 2D Euclidean distance

/**
 * @return Whether there exist
 * [living entities][LivingEntity] within the given planar (xz) Euclidean distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
fun Entity.hasNearbyLivingEntities2D(
	distance: Double?,
	predicate: LivingEntityPredicate? = null
) = getNearbyLivingEntities2D(
	distance,
	predicate
).any()

/**
 * @return Whether there exist
 * [living entities][LivingEntity] within the given planar (xz) Euclidean distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
fun Entity.hasNearbyLivingEntities2D(
	distance: Double?,
	predicate: LivingEntityDistancePredicate
) = getNearbyLivingEntities2D(
	distance,
	predicate
).any()

/**
 * @return Whether there exist
 * [living entities][LivingEntity] within the given distance per planar (xz) axis from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.hasNearbyLivingEntities2D(
	xDistance: Double?,
	zDistance: Double?,
	predicate: LivingEntityPredicate? = null
) = getNearbyLivingEntities2D(
	xDistance,
	zDistance,
	predicate
).any()

/**
 * @return Whether there exist
 * [living entities][LivingEntity] within the given distance per planar (xz) axis from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.hasNearbyLivingEntities2D(
	xDistance: Double?,
	zDistance: Double?,
	predicate: LivingEntityDistancePredicate
) = getNearbyLivingEntities2D(
	xDistance,
	zDistance,
	predicate
).any()