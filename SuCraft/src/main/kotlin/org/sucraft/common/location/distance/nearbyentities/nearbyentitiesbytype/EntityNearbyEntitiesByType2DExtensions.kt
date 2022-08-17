/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location.distance.nearbyentities.nearbyentitiesbytype

import org.bukkit.entity.Entity
import org.sucraft.common.entity.EntityByTypePredicate

// Get entities within 2D chessboard distance

/**
 * @return All [entities][E] within the given planar (xz) chessboard distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Entity.getNearbyEntitiesByTypeChessboardDistance2D(
	distance: Double?,
	noinline predicate: EntityByTypePredicate<E>? = null
) = location.getNearbyEntitiesByTypeChessboardDistance2D(
	distance,
	predicate
)

// Get entities within 2D Euclidean distance

/**
 * @return All [entities][E] within the given planar (xz) Euclidean distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Entity.getNearbyEntitiesByType2D(
	distance: Double?,
	noinline predicate: EntityByTypePredicate<E>? = null
) = location.getNearbyEntitiesByType2D(
	distance,
	predicate
)

/**
 * @return All [entities][E] within the given planar (xz) Euclidean distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Entity.getNearbyEntitiesByType2D(
	distance: Double?,
	noinline predicate: EntityByTypeDistancePredicate<E>
) = location.getNearbyEntitiesByType2D(
	distance,
	predicate
)

/**
 * @return All [entities][E] within the given distance per planar (xz) axis from this [Entity].
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Entity.getNearbyEntitiesByType2D(
	xDistance: Double?,
	zDistance: Double?,
	noinline predicate: EntityByTypePredicate<E>? = null
) = location.getNearbyEntitiesByType2D(
	xDistance,
	zDistance,
	predicate
)

/**
 * @return All [entities][E] within the given distance per planar (xz) axis from this [Entity].
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Entity.getNearbyEntitiesByType2D(
	xDistance: Double?,
	zDistance: Double?,
	noinline predicate: EntityByTypeDistancePredicate<E>
) = location.getNearbyEntitiesByType2D(
	xDistance,
	zDistance,
	predicate
)

// Get the closest entity out of a selection of entities

/**
 * @return The closest (in the xz plane) [entity][E] in planar (xz) distance out of the given [entities],
 * or null if the sequence is empty.
 */
inline fun <reified E : Entity> Entity.getClosestEntityByType2D(entities: Sequence<E>) =
	location.getClosestEntityByType2D(entities)

// Get the closest entity, optionally within a Euclidean distance

/**
 * @return The closest (in the xz plane) [entity][E]
 * with at most the given planar (xz) Euclidean distance from this [Entity],
 * or null if none exists.
 * A null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Entity.getClosestEntityByType2D(
	maxDistance: Double? = null,
	noinline predicate: EntityByTypePredicate<E>? = null
) = location.getClosestEntityByType2D(
	maxDistance,
	predicate
)

/**
 * @return The closest (in the xz plane) [entity][E]
 * with at most the given planar (xz) Euclidean distance from this [Entity],
 * or null if none exists.
 * A null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Entity.getClosestEntityByType2D(
	maxDistance: Double? = null,
	noinline predicate: EntityByTypeDistancePredicate<E>
) = location.getClosestEntityByType2D(
	maxDistance,
	predicate
)

/**
 * @return The closest (in the xz plane) [entity][E]
 * with at most the given distance per planar (xz) axis from this [Entity],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Entity.getClosestEntityByType2D(
	maxXDistance: Double?,
	maxZDistance: Double?,
	noinline predicate: EntityByTypePredicate<E>? = null
) = location.getClosestEntityByType2D(
	maxXDistance,
	maxZDistance,
	predicate
)

/**
 * @return The closest (in the xz plane) [entity][E]
 * with at most the given distance per planar (xz) axis from this [Entity],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Entity.getClosestEntityByType2D(
	maxXDistance: Double?,
	maxZDistance: Double?,
	noinline predicate: EntityByTypeDistancePredicate<E>
) = location.getClosestEntityByType2D(
	maxXDistance,
	maxZDistance,
	predicate
)