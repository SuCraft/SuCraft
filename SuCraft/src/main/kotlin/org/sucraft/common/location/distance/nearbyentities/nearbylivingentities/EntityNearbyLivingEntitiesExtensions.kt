/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location.distance.nearbyentities.nearbylivingentities

import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.sucraft.common.entity.LivingEntityPredicate

// Get living entities within chessboard distance

/**
 * @return All [living entities][LivingEntity] within the given chessboard distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
fun Entity.getNearbyLivingEntitiesChessboardDistance(
	distance: Double?,
	predicate: LivingEntityPredicate? = null
) = location.getNearbyLivingEntitiesChessboardDistance(
	distance,
	predicate
)

/**
 * @return All [living entities][LivingEntity] within the given chessboard distances from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.getNearbyLivingEntitiesChessboardDistance(
	xzDistance: Double?,
	yDistance: Double?,
	predicate: LivingEntityPredicate? = null
) = location.getNearbyLivingEntitiesChessboardDistance(
	xzDistance,
	yDistance,
	predicate
)

// Get living entities within Euclidean distance

/**
 * @return All [living entities][LivingEntity] within the given Euclidean distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
fun Entity.getNearbyLivingEntities(
	distance: Double?,
	predicate: LivingEntityPredicate? = null
) = location.getNearbyLivingEntities(
	distance,
	predicate
)

/**
 * @return All [living entities][LivingEntity] within the given Euclidean distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
fun Entity.getNearbyLivingEntities(
	distance: Double?,
	predicate: LivingEntityDistancePredicate
) = location.getNearbyLivingEntities(
	distance,
	predicate
)

/**
 * @return All [living entities][LivingEntity] within the given Euclidean distances from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.getNearbyLivingEntities(
	xzDistance: Double?,
	yDistance: Double?,
	predicate: LivingEntityPredicate? = null
) = location.getNearbyLivingEntities(
	xzDistance,
	yDistance,
	predicate
)

/**
 * @return All [living entities][LivingEntity] with the given Euclidean distances from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.getNearbyLivingEntities(
	xzDistance: Double?,
	yDistance: Double?,
	predicate: LivingEntityDistancePredicate
) = location.getNearbyLivingEntities(
	xzDistance,
	yDistance,
	predicate
)

/**
 * @return All [living entities][LivingEntity] within the given distance per axis from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.getNearbyLivingEntities(
	xDistance: Double?,
	yDistance: Double?,
	zDistance: Double?,
	predicate: LivingEntityPredicate? = null
) = location.getNearbyLivingEntities(
	xDistance,
	yDistance,
	zDistance,
	predicate
)

/**
 * @return All [living entities][LivingEntity] within the given distance per axis from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.getNearbyLivingEntities(
	xDistance: Double?,
	yDistance: Double?,
	zDistance: Double?,
	predicate: LivingEntityDistancePredicate
) = location.getNearbyLivingEntities(
	xDistance,
	yDistance,
	zDistance,
	predicate
)

// Get the closest living entity out of a selection of living entities

/**
 * @return The closest [living entity][LivingEntity] out of the given [livingEntities],
 * or null if the sequence is empty.
 */
fun Entity.getClosestLivingEntity(livingEntities: Sequence<LivingEntity>) =
	location.getClosestLivingEntity(livingEntities)

// Get the closest living entity, optionally within a Euclidean distance

/**
 * @return The closest [living entity][LivingEntity] with at most the given Euclidean distance from this [Entity],
 * or null if none exists.
 * A null distance is assumed to be infinite.
 */
fun Entity.getClosestLivingEntity(
	maxDistance: Double? = null,
	predicate: LivingEntityPredicate? = null
) = location.getClosestLivingEntity(
	maxDistance,
	predicate
)

/**
 * @return The closest [living entity][LivingEntity] with at most the given Euclidean distance from this [Entity],
 * or null if none exists.
 * A null distance is assumed to be infinite.
 */
fun Entity.getClosestLivingEntity(
	maxDistance: Double? = null,
	predicate: LivingEntityDistancePredicate
) = location.getClosestLivingEntity(
	maxDistance,
	predicate
)

/**
 * @return The closest [living entity][LivingEntity] with at most the given Euclidean distances from this [Entity],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Entity.getClosestLivingEntity(
	maxXZDistance: Double?,
	maxYDistance: Double?,
	predicate: LivingEntityPredicate? = null
) = location.getClosestLivingEntity(
	maxXZDistance,
	maxYDistance,
	predicate
)

/**
 * @return The closest [living entity][LivingEntity] with at most the given Euclidean distances from this [Entity],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Entity.getClosestLivingEntity(
	maxXZDistance: Double?,
	maxYDistance: Double?,
	predicate: LivingEntityDistancePredicate
) = location.getClosestLivingEntity(
	maxXZDistance,
	maxYDistance,
	predicate
)

/**
 * @return The closest [living entity][LivingEntity] with at most the given distance per axis from this [Entity],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Entity.getClosestLivingEntity(
	maxXDistance: Double?,
	maxYDistance: Double?,
	maxZDistance: Double?,
	predicate: LivingEntityPredicate? = null
) = location.getClosestLivingEntity(
	maxXDistance,
	maxYDistance,
	maxZDistance,
	predicate
)

/**
 * @return The closest [living entity][LivingEntity] with at most the given distance per axis from this [Entity],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Entity.getClosestLivingEntity(
	maxXDistance: Double?,
	maxYDistance: Double?,
	maxZDistance: Double?,
	predicate: LivingEntityDistancePredicate
) = location.getClosestLivingEntity(
	maxXDistance,
	maxYDistance,
	maxZDistance,
	predicate
)