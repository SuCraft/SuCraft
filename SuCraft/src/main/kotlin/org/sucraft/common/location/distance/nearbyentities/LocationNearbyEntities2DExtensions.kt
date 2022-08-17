/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location.distance.nearbyentities

import org.bukkit.Location
import org.bukkit.entity.Entity
import org.sucraft.common.entity.EntityPredicate
import org.sucraft.common.location.distance.distance2D
import org.sucraft.common.location.distance.distanceSquared2D

// EntityDistancePredicate extension

/**
 * @return This [EntityDistancePredicate] as an [EntityPredicate], assuming the distance
 * as the planar (xz) Euclidean distance between the given [fromLocation] and the entity being tested.
 */
fun EntityDistancePredicate.asEntityPredicate2D(fromLocation: Location) =
	{ entity: Entity -> this(fromLocation.distance2D(entity), entity) }

// Get entities within 2D chessboard distance

/**
 * @return All [entities][Entity] within the given planar (xz) chessboard distance from this [Location].
 * A null distance is assumed to be infinite.
 */
fun Location.getNearbyEntitiesChessboardDistance2D(
	distance: Double?,
	predicate: EntityPredicate? = null
) = getNearbyEntitiesChessboardDistance(
	xzDistance = distance,
	yDistance = null,
	predicate
)

// Get entities within 2D Euclidean distance

/**
 * @return All [entities][Entity] within the given planar (xz) Euclidean distance from this [Location].
 * A null distance is assumed to be infinite.
 */
fun Location.getNearbyEntities2D(
	distance: Double?,
	predicate: EntityPredicate? = null
) = getNearbyEntities(
	xzDistance = distance,
	yDistance = null,
	predicate
)

/**
 * @return All [entities][Entity] within the given planar (xz) Euclidean distance from this [Location].
 * A null distance is assumed to be infinite.
 */
fun Location.getNearbyEntities2D(
	distance: Double?,
	predicate: EntityDistancePredicate
) = getNearbyEntities2D(
	distance = distance,
	predicate.asEntityPredicate2D(this)
)

/**
 * @return All [entities][Entity] within the given distance per planar (xz) axis from this [Location].
 * Any null distance is assumed to be infinite.
 */
fun Location.getNearbyEntities2D(
	xDistance: Double?,
	zDistance: Double?,
	predicate: EntityPredicate? = null
) = getNearbyEntities(
	xDistance = xDistance,
	yDistance = null,
	zDistance = zDistance,
	predicate
)

/**
 * @return All [entities][Entity] within the given distance per planar (xz) axis from this [Location].
 * Any null distance is assumed to be infinite.
 */
fun Location.getNearbyEntities2D(
	xDistance: Double?,
	zDistance: Double?,
	predicate: EntityDistancePredicate
) = getNearbyEntities2D(
	xDistance = xDistance,
	zDistance = zDistance,
	predicate.asEntityPredicate2D(this)
)

// Get the closest entity out of a selection of entities

/**
 * @return The closest (in the xz plane) [entity][Entity] in planar (xz) distance out of the given [entities],
 * or null if the sequence is empty.
 */
fun Location.getClosestEntity2D(entities: Sequence<Entity>) =
	entities.minByOrNull { distanceSquared2D(it) }

// Get the closest entity, optionally within a Euclidean distance

/**
 * @return The closest (in the xz plane) [entity][Entity]
 * with at most the given planar (xz) Euclidean distance from this [Location],
 * or null if none exists.
 * A null distance is assumed to be infinite.
 */
fun Location.getClosestEntity2D(
	maxDistance: Double? = null,
	predicate: EntityPredicate? = null
) = getClosestEntity(
	maxXZDistance = maxDistance,
	maxYDistance = null,
	predicate
)

/**
 * @return The closest (in the xz plane) [entity][Entity]
 * with at most the given planar (xz) Euclidean distance from this [Location],
 * or null if none exists.
 * A null distance is assumed to be infinite.
 */
fun Location.getClosestEntity2D(
	maxDistance: Double? = null,
	predicate: EntityDistancePredicate
) = getClosestEntity2D(
	maxDistance = maxDistance,
	predicate.asEntityPredicate2D(this)
)

/**
 * @return The closest (in the xz plane) [entity][Entity]
 * with at most the given distance per planar (xz) axis from this [Location],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Location.getClosestEntity2D(
	maxXDistance: Double?,
	maxZDistance: Double?,
	predicate: EntityPredicate? = null
) = getClosestEntity(
	maxXDistance = maxXDistance,
	maxYDistance = null,
	maxZDistance = maxZDistance,
	predicate
)

/**
 * @return The closest (in the xz plane) [entity][Entity]
 * with at most the given distance per planar (xz) axis from this [Location],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Location.getClosestEntity2D(
	maxXDistance: Double?,
	maxZDistance: Double?,
	predicate: EntityDistancePredicate
) = getClosestEntity2D(
	maxXDistance = maxXDistance,
	maxZDistance = maxZDistance,
	predicate.asEntityPredicate2D(this)
)