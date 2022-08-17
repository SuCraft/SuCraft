/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location.distance.nearbyentities.nearbyentitiesbytype

import org.bukkit.entity.Entity
import org.sucraft.common.entity.EntityByTypePredicate

// Get entities within chessboard distance

/**
 * @return All [entities][E] within the given chessboard distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Entity.getNearbyEntitiesByTypeChessboardDistance(
	distance: Double?,
	noinline predicate: EntityByTypePredicate<E>? = null
) = location.getNearbyEntitiesByTypeChessboardDistance(
	distance,
	predicate
)

/**
 * @return All [entities][E] within the given chessboard distances from this [Entity].
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Entity.getNearbyEntitiesByTypeChessboardDistance(
	xzDistance: Double?,
	yDistance: Double?,
	noinline predicate: EntityByTypePredicate<E>? = null
) = location.getNearbyEntitiesByTypeChessboardDistance(
	xzDistance,
	yDistance,
	predicate
)

// Get entities within Euclidean distance

/**
 * @return All [entities][E] within the given Euclidean distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Entity.getNearbyEntitiesByType(
	distance: Double?,
	noinline predicate: EntityByTypePredicate<E>? = null
) = location.getNearbyEntitiesByType(
	distance,
	predicate
)

/**
 * @return All [entities][E] within the given Euclidean distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Entity.getNearbyEntitiesByType(
	distance: Double?,
	noinline predicate: EntityByTypeDistancePredicate<E>
) = location.getNearbyEntitiesByType(
	distance,
	predicate
)

/**
 * @return All [entities][E] within the given Euclidean distances from this [Entity].
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Entity.getNearbyEntitiesByType(
	xzDistance: Double?,
	yDistance: Double?,
	noinline predicate: EntityByTypePredicate<E>? = null
) = location.getNearbyEntitiesByType(
	xzDistance,
	yDistance,
	predicate
)

/**
 * @return All [entities][E] with the given Euclidean distances from this [Entity].
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Entity.getNearbyEntitiesByType(
	xzDistance: Double?,
	yDistance: Double?,
	noinline predicate: EntityByTypeDistancePredicate<E>
) = location.getNearbyEntitiesByType(
	xzDistance,
	yDistance,
	predicate
)

/**
 * @return All [entities][E] within the given distance per axis from this [Entity].
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Entity.getNearbyEntitiesByType(
	xDistance: Double?,
	yDistance: Double?,
	zDistance: Double?,
	noinline predicate: EntityByTypePredicate<E>? = null
) = location.getNearbyEntitiesByType(
	xDistance,
	yDistance,
	zDistance,
	predicate
)

/**
 * @return All [entities][E] within the given distance per axis from this [Entity].
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Entity.getNearbyEntitiesByType(
	xDistance: Double?,
	yDistance: Double?,
	zDistance: Double?,
	noinline predicate: EntityByTypeDistancePredicate<E>
) = location.getNearbyEntitiesByType(
	xDistance,
	yDistance,
	zDistance,
	predicate
)

// Get the closest entity out of a selection of entities

/**
 * @return The closest [entity][E] out of the given [entities],
 * or null if the sequence is empty.
 */
inline fun <reified E : Entity> Entity.getClosestEntityByType(entities: Sequence<E>) =
	location.getClosestEntityByType(entities)

// Get the closest entity, optionally within a Euclidean distance

/**
 * @return The closest [entity][E] with at most the given Euclidean distance from this [Entity],
 * or null if none exists.
 * A null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Entity.getClosestEntityByType(
	maxDistance: Double? = null,
	noinline predicate: EntityByTypePredicate<E>? = null
) = location.getClosestEntityByType(
	maxDistance,
	predicate
)

/**
 * @return The closest [entity][E] with at most the given Euclidean distance from this [Entity],
 * or null if none exists.
 * A null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Entity.getClosestEntityByType(
	maxDistance: Double? = null,
	noinline predicate: EntityByTypeDistancePredicate<E>
) = location.getClosestEntityByType(
	maxDistance,
	predicate
)

/**
 * @return The closest [entity][E] with at most the given Euclidean distances from this [Entity],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Entity.getClosestEntityByType(
	maxXZDistance: Double?,
	maxYDistance: Double?,
	noinline predicate: EntityByTypePredicate<E>? = null
) = location.getClosestEntityByType(
	maxXZDistance,
	maxYDistance,
	predicate
)

/**
 * @return The closest [entity][E] with at most the given Euclidean distances from this [Entity],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Entity.getClosestEntityByType(
	maxXZDistance: Double?,
	maxYDistance: Double?,
	noinline predicate: EntityByTypeDistancePredicate<E>
) = location.getClosestEntityByType(
	maxXZDistance,
	maxYDistance,
	predicate
)

/**
 * @return The closest [entity][E] with at most the given distance per axis from this [Entity],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Entity.getClosestEntityByType(
	maxXDistance: Double?,
	maxYDistance: Double?,
	maxZDistance: Double?,
	noinline predicate: EntityByTypePredicate<E>? = null
) = location.getClosestEntityByType(
	maxXDistance,
	maxYDistance,
	maxZDistance,
	predicate
)

/**
 * @return The closest [entity][E] with at most the given distance per axis from this [Entity],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Entity.getClosestEntityByType(
	maxXDistance: Double?,
	maxYDistance: Double?,
	maxZDistance: Double?,
	noinline predicate: EntityByTypeDistancePredicate<E>
) = location.getClosestEntityByType(
	maxXDistance,
	maxYDistance,
	maxZDistance,
	predicate
)