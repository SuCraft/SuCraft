/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location.distance.nearbyentities

import org.bukkit.Location
import org.bukkit.entity.Entity
import org.sucraft.common.entity.EntityPredicate
import org.sucraft.common.entity.y
import org.sucraft.common.location.distance.distance
import org.sucraft.common.location.distance.distance2D
import org.sucraft.common.location.distance.distanceSquared
import kotlin.math.abs

// EntityDistancePredicate type alias and extension

typealias EntityDistancePredicate = (entityDistance: Double, entity: Entity) -> Boolean

/**
 * @return This [EntityDistancePredicate] as an [EntityPredicate], assuming the distance
 * as the Euclidean distance between the given [fromLocation] and the entity being tested.
 */
fun EntityDistancePredicate.asEntityPredicate(fromLocation: Location) =
	{ entity: Entity -> this(fromLocation.distance(entity), entity) }

// The extension that calls the Bukkit method and serves as the basis for the other extensions

/**
 * @return All [entities][Entity] within the given distance per axis from this [Location].
 * Any null distance is assumed to be infinite.
 * [xDistance], [yDistance] and [zDistance] must all be null or all be non-null.
 */
fun Location.getNearbyEntities(
	xDistance: Double?,
	yDistance: Double?,
	zDistance: Double?,
	predicate: EntityPredicate? = null
) =
	if (xDistance == null && yDistance == null && zDistance == null)
		world.entities.asSequence().run {
			if (predicate == null) this else filter(predicate)
		}
	else
		world.getNearbyEntities(
			this,
			xDistance!!,
			yDistance!!,
			zDistance!!,
			predicate
		).asSequence()

// Get entities within chessboard distance

/**
 * @return All [entities][Entity] within the given chessboard distance from this [Location].
 * A null distance is assumed to be infinite.
 */
fun Location.getNearbyEntitiesChessboardDistance(
	distance: Double?,
	predicate: EntityPredicate? = null
) = getNearbyEntities(
	xDistance = distance,
	yDistance = distance,
	zDistance = distance,
	predicate
)

/**
 * @return All [entities][Entity] within the given chessboard distances from this [Location].
 * Any null distance is assumed to be infinite.
 */
fun Location.getNearbyEntitiesChessboardDistance(
	xzDistance: Double?,
	yDistance: Double?,
	predicate: EntityPredicate? = null
) = getNearbyEntities(
	xDistance = xzDistance,
	yDistance = yDistance,
	zDistance = xzDistance,
	predicate
)

// Get entities within Euclidean distance

/**
 * @return All [entities][Entity] within the given Euclidean distance from this [Location].
 * A null distance is assumed to be infinite.
 */
fun Location.getNearbyEntities(
	distance: Double?,
	predicate: EntityPredicate? = null
) = getNearbyEntitiesChessboardDistance(
	distance = distance,
	predicate
).run {
	if (distance != null)
		filter { distance(it) <= distance }
	else this
}

/**
 * @return All [entities][Entity] within the given Euclidean distance from this [Location].
 * A null distance is assumed to be infinite.
 */
fun Location.getNearbyEntities(
	distance: Double?,
	predicate: EntityDistancePredicate
) = getNearbyEntities(
	distance = distance,
	predicate.asEntityPredicate(this)
)

/**
 * @return All [entities][Entity] within the given Euclidean distances from this [Location].
 * Any null distance is assumed to be infinite.
 */
fun Location.getNearbyEntities(
	xzDistance: Double?,
	yDistance: Double?,
	predicate: EntityPredicate? = null
) = getNearbyEntitiesChessboardDistance(
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
 * @return All [entities][Entity] with the given Euclidean distances from this [Location].
 * Any null distance is assumed to be infinite.
 */
fun Location.getNearbyEntities(
	xzDistance: Double?,
	yDistance: Double?,
	predicate: EntityDistancePredicate
) = getNearbyEntities(
	xzDistance = xzDistance,
	yDistance = yDistance,
	predicate.asEntityPredicate(this)
)

/**
 * @return All [entities][Entity] within the given distance per axis from this [Location].
 * Any null distance is assumed to be infinite.
 */
fun Location.getNearbyEntities(
	xDistance: Double?,
	yDistance: Double?,
	zDistance: Double?,
	predicate: EntityDistancePredicate
) = getNearbyEntities(
	xDistance = xDistance,
	yDistance = yDistance,
	zDistance = zDistance,
	predicate.asEntityPredicate(this)
)

// Get the closest entity out of a selection of entities

/**
 * @return The closest [entity][Entity] out of the given [entities],
 * or null if the sequence is empty.
 */
fun Location.getClosestEntity(entities: Sequence<Entity>) =
	entities.minByOrNull { distanceSquared(it) }

// Get the closest entity, optionally within a Euclidean distance

/**
 * @return The closest [entity][Entity] with at most the given Euclidean distance from this [Location],
 * or null if none exists.
 * A null distance is assumed to be infinite.
 */
fun Location.getClosestEntity(
	maxDistance: Double? = null,
	predicate: EntityPredicate? = null
) = getClosestEntity(
	getNearbyEntities(
		distance = maxDistance,
		predicate
	)
)

/**
 * @return The closest [entity][Entity] with at most the given Euclidean distance from this [Location],
 * or null if none exists.
 * A null distance is assumed to be infinite.
 */
fun Location.getClosestEntity(
	maxDistance: Double? = null,
	predicate: EntityDistancePredicate
) = getClosestEntity(
	getNearbyEntities(
		distance = maxDistance,
		predicate
	)
)

/**
 * @return The closest [entity][Entity] with at most the given Euclidean distances from this [Location],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Location.getClosestEntity(
	maxXZDistance: Double?,
	maxYDistance: Double?,
	predicate: EntityPredicate? = null
) = getClosestEntity(
	getNearbyEntities(
		xzDistance = maxXZDistance,
		yDistance = maxYDistance,
		predicate
	)
)

/**
 * @return The closest [entity][Entity] with at most the given Euclidean distances from this [Location],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Location.getClosestEntity(
	maxXZDistance: Double?,
	maxYDistance: Double?,
	predicate: EntityDistancePredicate
) = getClosestEntity(
	getNearbyEntities(
		xzDistance = maxXZDistance,
		yDistance = maxYDistance,
		predicate
	)
)

/**
 * @return The closest [entity][Entity] with at most the given distance per axis from this [Location],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Location.getClosestEntity(
	maxXDistance: Double?,
	maxYDistance: Double?,
	maxZDistance: Double?,
	predicate: EntityPredicate? = null
) = getClosestEntity(
	getNearbyEntities(
		xDistance = maxXDistance,
		yDistance = maxYDistance,
		zDistance = maxZDistance,
		predicate
	)
)

/**
 * @return The closest [entity][Entity] with at most the given distance per axis from this [Location],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Location.getClosestEntity(
	maxXDistance: Double?,
	maxYDistance: Double?,
	maxZDistance: Double?,
	predicate: EntityDistancePredicate
) = getClosestEntity(
	getNearbyEntities(
		xDistance = maxXDistance,
		yDistance = maxYDistance,
		zDistance = maxZDistance,
		predicate
	)
)