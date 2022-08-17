/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location.distance.nearbyentities

import org.bukkit.entity.Entity
import org.sucraft.common.entity.EntityPredicate

// Get entities within chessboard distance

/**
 * @return All [entities][Entity] within the given chessboard distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
fun Entity.getNearbyEntitiesChessboardDistance(
	distance: Double?,
	predicate: EntityPredicate? = null
) = location.getNearbyEntitiesChessboardDistance(
	distance,
	predicate
)

/**
 * @return All [entities][Entity] within the given chessboard distances from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.getNearbyEntitiesChessboardDistance(
	xzDistance: Double?,
	yDistance: Double?,
	predicate: EntityPredicate? = null
) = location.getNearbyEntitiesChessboardDistance(
	xzDistance,
	yDistance,
	predicate
)

// Get entities within Euclidean distance

/**
 * @return All [entities][Entity] within the given Euclidean distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
fun Entity.getNearbyEntities(
	distance: Double?,
	predicate: EntityPredicate? = null
) = location.getNearbyEntities(
	distance,
	predicate
)

/**
 * @return All [entities][Entity] within the given Euclidean distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
fun Entity.getNearbyEntities(
	distance: Double?,
	predicate: EntityDistancePredicate
) = location.getNearbyEntities(
	distance,
	predicate
)

/**
 * @return All [entities][Entity] within the given Euclidean distances from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.getNearbyEntities(
	xzDistance: Double?,
	yDistance: Double?,
	predicate: EntityPredicate? = null
) = location.getNearbyEntities(
	xzDistance,
	yDistance,
	predicate
)

/**
 * @return All [entities][Entity] with the given Euclidean distances from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.getNearbyEntities(
	xzDistance: Double?,
	yDistance: Double?,
	predicate: EntityDistancePredicate
) = location.getNearbyEntities(
	xzDistance,
	yDistance,
	predicate
)

/**
 * @return All [entities][Entity] within the given distance per axis from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.getNearbyEntities(
	xDistance: Double?,
	yDistance: Double?,
	zDistance: Double?,
	predicate: EntityPredicate? = null
) = location.getNearbyEntities(
	xDistance,
	yDistance,
	zDistance,
	predicate
)

/**
 * @return All [entities][Entity] within the given distance per axis from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.getNearbyEntities(
	xDistance: Double?,
	yDistance: Double?,
	zDistance: Double?,
	predicate: EntityDistancePredicate
) = location.getNearbyEntities(
	xDistance,
	yDistance,
	zDistance,
	predicate
)

// Get the closest entity out of a selection of entities

/**
 * @return The closest [entity][Entity] out of the given [entities],
 * or null if the sequence is empty.
 */
fun Entity.getClosestEntity(entities: Sequence<Entity>) =
	location.getClosestEntity(entities)

// Get the closest entity, optionally within a Euclidean distance

/**
 * @return The closest [entity][Entity] with at most the given Euclidean distance from this [Entity],
 * or null if none exists.
 * A null distance is assumed to be infinite.
 */
fun Entity.getClosestEntity(
	maxDistance: Double? = null,
	predicate: EntityPredicate? = null
) = location.getClosestEntity(
	maxDistance,
	predicate
)

/**
 * @return The closest [entity][Entity] with at most the given Euclidean distance from this [Entity],
 * or null if none exists.
 * A null distance is assumed to be infinite.
 */
fun Entity.getClosestEntity(
	maxDistance: Double? = null,
	predicate: EntityDistancePredicate
) = location.getClosestEntity(
	maxDistance,
	predicate
)

/**
 * @return The closest [entity][Entity] with at most the given Euclidean distances from this [Entity],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Entity.getClosestEntity(
	maxXZDistance: Double?,
	maxYDistance: Double?,
	predicate: EntityPredicate? = null
) = location.getClosestEntity(
	maxXZDistance,
	maxYDistance,
	predicate
)

/**
 * @return The closest [entity][Entity] with at most the given Euclidean distances from this [Entity],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Entity.getClosestEntity(
	maxXZDistance: Double?,
	maxYDistance: Double?,
	predicate: EntityDistancePredicate
) = location.getClosestEntity(
	maxXZDistance,
	maxYDistance,
	predicate
)

/**
 * @return The closest [entity][Entity] with at most the given distance per axis from this [Entity],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Entity.getClosestEntity(
	maxXDistance: Double?,
	maxYDistance: Double?,
	maxZDistance: Double?,
	predicate: EntityPredicate? = null
) = location.getClosestEntity(
	maxXDistance,
	maxYDistance,
	maxZDistance,
	predicate
)

/**
 * @return The closest [entity][Entity] with at most the given distance per axis from this [Entity],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Entity.getClosestEntity(
	maxXDistance: Double?,
	maxYDistance: Double?,
	maxZDistance: Double?,
	predicate: EntityDistancePredicate
) = location.getClosestEntity(
	maxXDistance,
	maxYDistance,
	maxZDistance,
	predicate
)