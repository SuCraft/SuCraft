/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location.distance.nearbyentities.nearbylivingentities

import org.bukkit.Location
import org.bukkit.entity.LivingEntity
import org.sucraft.common.entity.LivingEntityPredicate
import org.sucraft.common.location.distance.distance2D
import org.sucraft.common.location.distance.distanceSquared2D

// LivingEntityDistancePredicate extension

/**
 * @return This [LivingEntityDistancePredicate] as a [LivingEntityPredicate], assuming the distance
 * as the planar (xz) Euclidean distance between the given [fromLocation] and the living entity being tested.
 */
fun LivingEntityDistancePredicate.asLivingEntityPredicate2D(fromLocation: Location) =
	{ livingEntity: LivingEntity -> this(fromLocation.distance2D(livingEntity), livingEntity) }

// Get living entities within 2D chessboard distance

/**
 * @return All [living entities][LivingEntity] within the given planar (xz) chessboard distance from this [Location].
 * A null distance is assumed to be infinite.
 */
fun Location.getNearbyLivingEntitiesChessboardDistance2D(
	distance: Double?,
	predicate: LivingEntityPredicate? = null
) = getNearbyLivingEntitiesChessboardDistance(
	xzDistance = distance,
	yDistance = null,
	predicate
)

// Get living entities within 2D Euclidean distance

/**
 * @return All [living entities][LivingEntity] within the given planar (xz) Euclidean distance from this [Location].
 * A null distance is assumed to be infinite.
 */
fun Location.getNearbyLivingEntities2D(
	distance: Double?,
	predicate: LivingEntityPredicate? = null
) = getNearbyLivingEntities(
	xzDistance = distance,
	yDistance = null,
	predicate
)

/**
 * @return All [living entities][LivingEntity] within the given planar (xz) Euclidean distance from this [Location].
 * A null distance is assumed to be infinite.
 */
fun Location.getNearbyLivingEntities2D(
	distance: Double?,
	predicate: LivingEntityDistancePredicate
) = getNearbyLivingEntities2D(
	distance = distance,
	predicate.asLivingEntityPredicate2D(this)
)

/**
 * @return All [living entities][LivingEntity] within the given distance per planar (xz) axis from this [Location].
 * Any null distance is assumed to be infinite.
 */
fun Location.getNearbyLivingEntities2D(
	xDistance: Double?,
	zDistance: Double?,
	predicate: LivingEntityPredicate? = null
) = getNearbyLivingEntities(
	xDistance = xDistance,
	yDistance = null,
	zDistance = zDistance,
	predicate
)

/**
 * @return All [living entities][LivingEntity] within the given distance per planar (xz) axis from this [Location].
 * Any null distance is assumed to be infinite.
 */
fun Location.getNearbyLivingEntities2D(
	xDistance: Double?,
	zDistance: Double?,
	predicate: LivingEntityDistancePredicate
) = getNearbyLivingEntities2D(
	xDistance = xDistance,
	zDistance = zDistance,
	predicate.asLivingEntityPredicate2D(this)
)

// Get the closest living entity out of a selection of living entities

/**
 * @return The closest (in the xz plane) [living entity][LivingEntity]
 * in planar (xz) distance out of the given [livingEntities],
 * or null if the sequence is empty.
 */
fun Location.getClosestLivingEntity2D(livingEntities: Sequence<LivingEntity>) =
	livingEntities.minByOrNull { distanceSquared2D(it) }

// Get the closest living entity, optionally within a Euclidean distance

/**
 * @return The closest (in the xz plane) [living entity][LivingEntity]
 * with at most the given planar (xz) Euclidean distance from this [Location],
 * or null if none exists.
 * A null distance is assumed to be infinite.
 */
fun Location.getClosestLivingEntity2D(
	maxDistance: Double? = null,
	predicate: LivingEntityPredicate? = null
) = getClosestLivingEntity(
	maxXZDistance = maxDistance,
	maxYDistance = null,
	predicate
)

/**
 * @return The closest (in the xz plane) [living entity][LivingEntity]
 * with at most the given planar (xz) Euclidean distance from this [Location],
 * or null if none exists.
 * A null distance is assumed to be infinite.
 */
fun Location.getClosestLivingEntity2D(
	maxDistance: Double? = null,
	predicate: LivingEntityDistancePredicate
) = getClosestLivingEntity2D(
	maxDistance = maxDistance,
	predicate.asLivingEntityPredicate2D(this)
)

/**
 * @return The closest (in the xz plane) [living entity][LivingEntity]
 * with at most the given distance per planar (xz) axis from this [Location],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Location.getClosestLivingEntity2D(
	maxXDistance: Double?,
	maxZDistance: Double?,
	predicate: LivingEntityPredicate? = null
) = getClosestLivingEntity(
	maxXDistance = maxXDistance,
	maxYDistance = null,
	maxZDistance = maxZDistance,
	predicate
)

/**
 * @return The closest (in the xz plane) [living entity][LivingEntity]
 * with at most the given distance per planar (xz) axis from this [Location],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Location.getClosestLivingEntity2D(
	maxXDistance: Double?,
	maxZDistance: Double?,
	predicate: LivingEntityDistancePredicate
) = getClosestLivingEntity2D(
	maxXDistance = maxXDistance,
	maxZDistance = maxZDistance,
	predicate.asLivingEntityPredicate2D(this)
)