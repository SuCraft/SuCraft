/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location.distance.nearbyentities

import org.bukkit.entity.Entity
import org.sucraft.common.entity.EntityPredicate

// Get entities within 2D chessboard distance

/**
 * @return All [entities][Entity] within the given planar (xz) chessboard distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
fun Entity.getNearbyEntitiesChessboardDistance2D(
	distance: Double?,
	predicate: EntityPredicate? = null
) = location.getNearbyEntitiesChessboardDistance2D(
	distance,
	predicate
)

// Get entities within 2D Euclidean distance

/**
 * @return All [entities][Entity] within the given planar (xz) Euclidean distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
fun Entity.getNearbyEntities2D(
	distance: Double?,
	predicate: EntityPredicate? = null
) = location.getNearbyEntities2D(
	distance,
	predicate
)

/**
 * @return All [entities][Entity] within the given planar (xz) Euclidean distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
fun Entity.getNearbyEntities2D(
	distance: Double?,
	predicate: EntityDistancePredicate
) = location.getNearbyEntities2D(
	distance,
	predicate
)

/**
 * @return All [entities][Entity] within the given distance per planar (xz) axis from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.getNearbyEntities2D(
	xDistance: Double?,
	zDistance: Double?,
	predicate: EntityPredicate? = null
) = location.getNearbyEntities2D(
	xDistance,
	zDistance,
	predicate
)

/**
 * @return All [entities][Entity] within the given distance per planar (xz) axis from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.getNearbyEntities2D(
	xDistance: Double?,
	zDistance: Double?,
	predicate: EntityDistancePredicate
) = location.getNearbyEntities2D(
	xDistance,
	zDistance,
	predicate
)

// Get the closest entity out of a selection of entities

/**
 * @return The closest (in the xz plane) [entity][Entity] in planar (xz) distance out of the given [entities],
 * or null if the sequence is empty.
 */
fun Entity.getClosestEntity2D(entities: Sequence<Entity>) =
	location.getClosestEntity2D(entities)

// Get the closest entity, optionally within a Euclidean distance

/**
 * @return The closest (in the xz plane) [entity][Entity]
 * with at most the given planar (xz) Euclidean distance from this [Entity],
 * or null if none exists.
 * A null distance is assumed to be infinite.
 */
fun Entity.getClosestEntity2D(
	maxDistance: Double? = null,
	predicate: EntityPredicate? = null
) = location.getClosestEntity2D(
	maxDistance,
	predicate
)

/**
 * @return The closest (in the xz plane) [entity][Entity]
 * with at most the given planar (xz) Euclidean distance from this [Entity],
 * or null if none exists.
 * A null distance is assumed to be infinite.
 */
fun Entity.getClosestEntity2D(
	maxDistance: Double? = null,
	predicate: EntityDistancePredicate
) = location.getClosestEntity2D(
	maxDistance,
	predicate
)

/**
 * @return The closest (in the xz plane) [entity][Entity]
 * with at most the given distance per planar (xz) axis from this [Entity],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Entity.getClosestEntity2D(
	maxXDistance: Double?,
	maxZDistance: Double?,
	predicate: EntityPredicate? = null
) = location.getClosestEntity2D(
	maxXDistance,
	maxZDistance,
	predicate
)

/**
 * @return The closest (in the xz plane) [entity][Entity]
 * with at most the given distance per planar (xz) axis from this [Entity],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Entity.getClosestEntity2D(
	maxXDistance: Double?,
	maxZDistance: Double?,
	predicate: EntityDistancePredicate
) = location.getClosestEntity2D(
	maxXDistance,
	maxZDistance,
	predicate
)