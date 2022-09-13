/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location.distance.nearbyentities.nearbylivingentities

import org.bukkit.Location
import org.bukkit.entity.LivingEntity
import org.sucraft.common.entity.LivingEntityPredicate
import org.sucraft.common.entity.y
import org.sucraft.common.location.distance.distance
import org.sucraft.common.location.distance.distance2D
import org.sucraft.common.location.distance.distanceSquared
import kotlin.math.abs

// LivingEntityDistancePredicate type alias and extension

typealias LivingEntityDistancePredicate = (livingEntityDistance: Double, livingEntity: LivingEntity) -> Boolean

/**
 * @return This [LivingEntityDistancePredicate] as a [LivingEntityPredicate], assuming the distance
 * as the Euclidean distance between the given [fromLocation] and the living entity being tested.
 */
fun LivingEntityDistancePredicate.asLivingEntityPredicate(fromLocation: Location) =
	{ livingEntity: LivingEntity -> this(fromLocation.distance(livingEntity), livingEntity) }

// The extension that calls the Bukkit method and serves as the basis for the other extensions

/**
 * @return All [living entities][LivingEntity] within the given distance per axis from this [Location].
 * Any null distance is assumed to be infinite.
 */
fun Location.getNearbyLivingEntities(
	xDistance: Double?,
	yDistance: Double?,
	zDistance: Double?,
	predicate: LivingEntityPredicate? = null
) =

	if (xDistance == null && yDistance == null && zDistance == null)
		world.livingEntities.asSequence().run {
			if (predicate == null) this else filter(predicate)
		}
	else
		world.getNearbyLivingEntities(
			this,
			xDistance!!,
			yDistance!!,
			zDistance!!,
			predicate
		).asSequence()

// Get living entities within chessboard distance

/**
 * @return All [living entities][LivingEntity] within the given chessboard distance from this [Location].
 * A null distance is assumed to be infinite.
 */
fun Location.getNearbyLivingEntitiesChessboardDistance(
	distance: Double?,
	predicate: LivingEntityPredicate? = null
) = getNearbyLivingEntities(
	xDistance = distance,
	yDistance = distance,
	zDistance = distance,
	predicate
)

/**
 * @return All [living entities][LivingEntity] within the given chessboard distances from this [Location].
 * Any null distance is assumed to be infinite.
 */
fun Location.getNearbyLivingEntitiesChessboardDistance(
	xzDistance: Double?,
	yDistance: Double?,
	predicate: LivingEntityPredicate? = null
) = getNearbyLivingEntities(
	xDistance = xzDistance,
	yDistance = yDistance,
	zDistance = xzDistance,
	predicate
)

// Get living entities within Euclidean distance

/**
 * @return All [living entities][LivingEntity] within the given Euclidean distance from this [Location].
 * A null distance is assumed to be infinite.
 */
fun Location.getNearbyLivingEntities(
	distance: Double?,
	predicate: LivingEntityPredicate? = null
) = getNearbyLivingEntitiesChessboardDistance(
	distance = distance,
	predicate
).run {
	if (distance != null)
		filter { distance(it) <= distance }
	else this
}

/**
 * @return All [living entities][LivingEntity] within the given Euclidean distance from this [Location].
 * A null distance is assumed to be infinite.
 */
fun Location.getNearbyLivingEntities(
	distance: Double?,
	predicate: LivingEntityDistancePredicate
) = getNearbyLivingEntities(
	distance = distance,
	predicate.asLivingEntityPredicate(this)
)

/**
 * @return All [living entities][LivingEntity] within the given Euclidean distances from this [Location].
 * Any null distance is assumed to be infinite.
 */
fun Location.getNearbyLivingEntities(
	xzDistance: Double?,
	yDistance: Double?,
	predicate: LivingEntityPredicate? = null
) = getNearbyLivingEntitiesChessboardDistance(
	xzDistance = xzDistance,
	yDistance = yDistance,
	predicate
).run {
	if (xzDistance != null)
		filter { distance2D(it) <= xzDistance }
	else this
}.run {
	if (yDistance != null)
		filter { abs(y - it.y) <= yDistance }
	else this
}

/**
 * @return All [living entities][LivingEntity] with the given Euclidean distances from this [Location].
 * Any null distance is assumed to be infinite.
 */
fun Location.getNearbyLivingEntities(
	xzDistance: Double?,
	yDistance: Double?,
	predicate: LivingEntityDistancePredicate
) = getNearbyLivingEntities(
	xzDistance = xzDistance,
	yDistance = yDistance,
	predicate.asLivingEntityPredicate(this)
)

/**
 * @return All [living entities][LivingEntity] within the given distance per axis from this [Location].
 * Any null distance is assumed to be infinite.
 */
fun Location.getNearbyLivingEntities(
	xDistance: Double?,
	yDistance: Double?,
	zDistance: Double?,
	predicate: LivingEntityDistancePredicate
) = getNearbyLivingEntities(
	xDistance = xDistance,
	yDistance = yDistance,
	zDistance = zDistance,
	predicate.asLivingEntityPredicate(this)
)

// Get the closest living entity out of a selection of living entities

/**
 * @return The closest [living entity][LivingEntity] out of the given [livingEntities],
 * or null if the sequence is empty.
 */
fun Location.getClosestLivingEntity(livingEntities: Sequence<LivingEntity>) =
	livingEntities.minByOrNull { distanceSquared(it) }

// Get the closest living entity, optionally within a Euclidean distance

/**
 * @return The closest [living entity][LivingEntity] with at most the given Euclidean distance from this [Location],
 * or null if none exists.
 * A null distance is assumed to be infinite.
 */
fun Location.getClosestLivingEntity(
	maxDistance: Double? = null,
	predicate: LivingEntityPredicate? = null
) = getClosestLivingEntity(
	getNearbyLivingEntities(
		distance = maxDistance,
		predicate
	)
)

/**
 * @return The closest [living entity][LivingEntity] with at most the given Euclidean distance from this [Location],
 * or null if none exists.
 * A null distance is assumed to be infinite.
 */
fun Location.getClosestLivingEntity(
	maxDistance: Double? = null,
	predicate: LivingEntityDistancePredicate
) = getClosestLivingEntity(
	getNearbyLivingEntities(
		distance = maxDistance,
		predicate
	)
)

/**
 * @return The closest [living entity][LivingEntity] with at most the given Euclidean distances from this [Location],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Location.getClosestLivingEntity(
	maxXZDistance: Double?,
	maxYDistance: Double?,
	predicate: LivingEntityPredicate? = null
) = getClosestLivingEntity(
	getNearbyLivingEntities(
		xzDistance = maxXZDistance,
		yDistance = maxYDistance,
		predicate
	)
)

/**
 * @return The closest [living entity][LivingEntity] with at most the given Euclidean distances from this [Location],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Location.getClosestLivingEntity(
	maxXZDistance: Double?,
	maxYDistance: Double?,
	predicate: LivingEntityDistancePredicate
) = getClosestLivingEntity(
	getNearbyLivingEntities(
		xzDistance = maxXZDistance,
		yDistance = maxYDistance,
		predicate
	)
)

/**
 * @return The closest [living entity][LivingEntity] with at most the given distance per axis from this [Location],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Location.getClosestLivingEntity(
	maxXDistance: Double?,
	maxYDistance: Double?,
	maxZDistance: Double?,
	predicate: LivingEntityPredicate? = null
) = getClosestLivingEntity(
	getNearbyLivingEntities(
		xDistance = maxXDistance,
		yDistance = maxYDistance,
		zDistance = maxZDistance,
		predicate
	)
)

/**
 * @return The closest [living entity][LivingEntity] with at most the given distance per axis from this [Location],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Location.getClosestLivingEntity(
	maxXDistance: Double?,
	maxYDistance: Double?,
	maxZDistance: Double?,
	predicate: LivingEntityDistancePredicate
) = getClosestLivingEntity(
	getNearbyLivingEntities(
		xDistance = maxXDistance,
		yDistance = maxYDistance,
		zDistance = maxZDistance,
		predicate
	)
)