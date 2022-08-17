/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location.distance.nearbyentities.nearbylivingentities

import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.sucraft.common.entity.LivingEntityPredicate

// Get living entities within 2D chessboard distance

/**
 * @return All [living entities][LivingEntity] within the given planar (xz) chessboard distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
fun Entity.getNearbyLivingEntitiesChessboardDistance2D(
	distance: Double?,
	predicate: LivingEntityPredicate? = null
) = location.getNearbyLivingEntitiesChessboardDistance2D(
	distance,
	predicate
)

// Get living entities within 2D Euclidean distance

/**
 * @return All [living entities][LivingEntity] within the given planar (xz) Euclidean distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
fun Entity.getNearbyLivingEntities2D(
	distance: Double?,
	predicate: LivingEntityPredicate? = null
) = location.getNearbyLivingEntities2D(
	distance,
	predicate
)

/**
 * @return All [living entities][LivingEntity] within the given planar (xz) Euclidean distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
fun Entity.getNearbyLivingEntities2D(
	distance: Double?,
	predicate: LivingEntityDistancePredicate
) = location.getNearbyLivingEntities2D(
	distance,
	predicate
)

/**
 * @return All [living entities][LivingEntity] within the given distance per planar (xz) axis from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.getNearbyLivingEntities2D(
	xDistance: Double?,
	zDistance: Double?,
	predicate: LivingEntityPredicate? = null
) = location.getNearbyLivingEntities2D(
	xDistance,
	zDistance,
	predicate
)

/**
 * @return All [living entities][LivingEntity] within the given distance per planar (xz) axis from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.getNearbyLivingEntities2D(
	xDistance: Double?,
	zDistance: Double?,
	predicate: LivingEntityDistancePredicate
) = location.getNearbyLivingEntities2D(
	xDistance,
	zDistance,
	predicate
)

// Get the closest living entity out of a selection of living entities

/**
 * @return The closest (in the xz plane) [living entity][LivingEntity]
 * in planar (xz) distance out of the given [livingEntities],
 * or null if the sequence is empty.
 */
fun Entity.getClosestLivingEntity2D(livingEntities: Sequence<LivingEntity>) =
	location.getClosestLivingEntity2D(livingEntities)

// Get the closest living entity, optionally within a Euclidean distance

/**
 * @return The closest (in the xz plane) [living entity][LivingEntity]
 * with at most the given planar (xz) Euclidean distance from this [Entity],
 * or null if none exists.
 * A null distance is assumed to be infinite.
 */
fun Entity.getClosestLivingEntity2D(
	maxDistance: Double? = null,
	predicate: LivingEntityPredicate? = null
) = location.getClosestLivingEntity2D(
	maxDistance,
	predicate
)

/**
 * @return The closest (in the xz plane) [living entity][LivingEntity]
 * with at most the given planar (xz) Euclidean distance from this [Entity],
 * or null if none exists.
 * A null distance is assumed to be infinite.
 */
fun Entity.getClosestLivingEntity2D(
	maxDistance: Double? = null,
	predicate: LivingEntityDistancePredicate
) = location.getClosestLivingEntity2D(
	maxDistance,
	predicate
)

/**
 * @return The closest (in the xz plane) [living entity][LivingEntity]
 * with at most the given distance per planar (xz) axis from this [Entity],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Entity.getClosestLivingEntity2D(
	maxXDistance: Double?,
	maxZDistance: Double?,
	predicate: LivingEntityPredicate? = null
) = location.getClosestLivingEntity2D(
	maxXDistance,
	maxZDistance,
	predicate
)

/**
 * @return The closest (in the xz plane) [living entity][LivingEntity]
 * with at most the given distance per planar (xz) axis from this [Entity],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Entity.getClosestLivingEntity2D(
	maxXDistance: Double?,
	maxZDistance: Double?,
	predicate: LivingEntityDistancePredicate
) = location.getClosestLivingEntity2D(
	maxXDistance,
	maxZDistance,
	predicate
)