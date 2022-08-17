/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location.distance.nearbyentities.nearbyentitiesbytype

import org.bukkit.Location
import org.bukkit.entity.Entity
import org.sucraft.common.entity.EntityByTypePredicate
import org.sucraft.common.location.distance.distance2D
import org.sucraft.common.location.distance.distanceSquared2D

// EntityByTypeDistancePredicate extension

/**
 * @return This [EntityByTypeDistancePredicate] as an [EntityByTypePredicate], assuming the distance
 * as the planar (xz) Euclidean distance between the given [fromLocation] and the entity being tested.
 */
fun <E : Entity> EntityByTypeDistancePredicate<E>.asEntityByTypePredicate2D(fromLocation: Location) =
	{ entity: E -> this(fromLocation.distance2D(entity), entity) }

// Get entities within 2D chessboard distance

/**
 * @return All [entities][E] within the given planar (xz) chessboard distance from this [Location].
 * A null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.getNearbyEntitiesByTypeChessboardDistance2D(
	distance: Double?,
	noinline predicate: EntityByTypePredicate<E>? = null
) = getNearbyEntitiesByTypeChessboardDistance(
	xzDistance = distance,
	yDistance = null,
	predicate
)

// Get entities within 2D Euclidean distance

/**
 * @return All [entities][E] within the given planar (xz) Euclidean distance from this [Location].
 * A null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.getNearbyEntitiesByType2D(
	distance: Double?,
	noinline predicate: EntityByTypePredicate<E>? = null
) = getNearbyEntitiesByType(
	xzDistance = distance,
	yDistance = null,
	predicate
)

/**
 * @return All [entities][E] within the given planar (xz) Euclidean distance from this [Location].
 * A null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.getNearbyEntitiesByType2D(
	distance: Double?,
	noinline predicate: EntityByTypeDistancePredicate<E>
) = getNearbyEntitiesByType2D(
	distance = distance,
	predicate.asEntityByTypePredicate2D(this)
)

/**
 * @return All [entities][E] within the given distance per planar (xz) axis from this [Location].
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.getNearbyEntitiesByType2D(
	xDistance: Double?,
	zDistance: Double?,
	noinline predicate: EntityByTypePredicate<E>? = null
) = getNearbyEntitiesByType(
	xDistance = xDistance,
	yDistance = null,
	zDistance = zDistance,
	predicate
)

/**
 * @return All [entities][E] within the given distance per planar (xz) axis from this [Location].
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.getNearbyEntitiesByType2D(
	xDistance: Double?,
	zDistance: Double?,
	noinline predicate: EntityByTypeDistancePredicate<E>
) = getNearbyEntitiesByType2D(
	xDistance = xDistance,
	zDistance = zDistance,
	predicate.asEntityByTypePredicate2D(this)
)

// Get the closest entity out of a selection of entities

/**
 * @return The closest (in the xz plane) [entity][E] in planar (xz) distance out of the given [entities],
 * or null if the sequence is empty.
 */
inline fun <reified E : Entity> Location.getClosestEntityByType2D(entities: Sequence<E>) =
	entities.minByOrNull { distanceSquared2D(it) }

// Get the closest entity, optionally within a Euclidean distance

/**
 * @return The closest (in the xz plane) [entity][E]
 * with at most the given planar (xz) Euclidean distance from this [Location],
 * or null if none exists.
 * A null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.getClosestEntityByType2D(
	maxDistance: Double? = null,
	noinline predicate: EntityByTypePredicate<E>? = null
) = getClosestEntityByType(
	maxXZDistance = maxDistance,
	maxYDistance = null,
	predicate
)

/**
 * @return The closest (in the xz plane) [entity][E]
 * with at most the given planar (xz) Euclidean distance from this [Location],
 * or null if none exists.
 * A null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.getClosestEntityByType2D(
	maxDistance: Double? = null,
	noinline predicate: EntityByTypeDistancePredicate<E>
) = getClosestEntityByType2D(
	maxDistance = maxDistance,
	predicate.asEntityByTypePredicate2D(this)
)

/**
 * @return The closest (in the xz plane) [entity][E]
 * with at most the given distance per planar (xz) axis from this [Location],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.getClosestEntityByType2D(
	maxXDistance: Double?,
	maxZDistance: Double?,
	noinline predicate: EntityByTypePredicate<E>? = null
) = getClosestEntityByType(
	maxXDistance = maxXDistance,
	maxYDistance = null,
	maxZDistance = maxZDistance,
	predicate
)

/**
 * @return The closest (in the xz plane) [entity][E]
 * with at most the given distance per planar (xz) axis from this [Location],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.getClosestEntityByType2D(
	maxXDistance: Double?,
	maxZDistance: Double?,
	noinline predicate: EntityByTypeDistancePredicate<E>
) = getClosestEntityByType2D(
	maxXDistance = maxXDistance,
	maxZDistance = maxZDistance,
	predicate.asEntityByTypePredicate2D(this)
)