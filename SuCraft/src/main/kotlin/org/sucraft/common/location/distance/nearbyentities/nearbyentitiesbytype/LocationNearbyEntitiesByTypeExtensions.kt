/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location.distance.nearbyentities.nearbyentitiesbytype

import org.bukkit.Location
import org.bukkit.entity.Entity
import org.sucraft.common.entity.EntityByTypePredicate
import org.sucraft.common.entity.y
import org.sucraft.common.location.distance.distance
import org.sucraft.common.location.distance.distance2D
import org.sucraft.common.location.distance.distanceSquared
import kotlin.math.abs

// EntityByTypeDistancePredicate type alias and extension

typealias EntityByTypeDistancePredicate<E> = (entityDistance: Double, entity: E) -> Boolean

/**
 * @return This [EntityByTypeDistancePredicate] as an [EntityByTypePredicate], assuming the distance
 * as the Euclidean distance between the given [fromLocation] and the entity being tested.
 */
fun <E : Entity> EntityByTypeDistancePredicate<E>.asEntityByTypePredicate(fromLocation: Location) =
	{ entity: E -> this(fromLocation.distance(entity), entity) }

// The extension that calls the Bukkit method and serves as the basis for the other extensions

/**
 * @return All [entities][E] within the given distance per axis from this [Location].
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.getNearbyEntitiesByType(
	xDistance: Double?,
	yDistance: Double?,
	zDistance: Double?,
	noinline predicate: EntityByTypePredicate<E>? = null
) = world.getNearbyEntitiesByType(
	E::class.java,
	this,
	xDistance ?: Long.MAX_VALUE.toDouble(),
	yDistance ?: Long.MAX_VALUE.toDouble(),
	zDistance ?: Long.MAX_VALUE.toDouble(),
	predicate
).asSequence()

// Get entities within chessboard distance

/**
 * @return All [entities][E] within the given chessboard distance from this [Location].
 * A null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.getNearbyEntitiesByTypeChessboardDistance(
	distance: Double?,
	noinline predicate: EntityByTypePredicate<E>? = null
) = getNearbyEntitiesByType(
	xDistance = distance,
	yDistance = distance,
	zDistance = distance,
	predicate
)

/**
 * @return All [entities][E] within the given chessboard distances from this [Location].
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.getNearbyEntitiesByTypeChessboardDistance(
	xzDistance: Double?,
	yDistance: Double?,
	noinline predicate: EntityByTypePredicate<E>? = null
) = getNearbyEntitiesByType(
	xDistance = xzDistance,
	yDistance = yDistance,
	zDistance = xzDistance,
	predicate
)

// Get entities within Euclidean distance

/**
 * @return All [entities][E] within the given Euclidean distance from this [Location].
 * A null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.getNearbyEntitiesByType(
	distance: Double?,
	noinline predicate: EntityByTypePredicate<E>? = null
) = getNearbyEntitiesByTypeChessboardDistance(
	distance = distance,
	predicate
).run {
	if (distance != null)
		filter { distance(it) <= distance }
	else this
}

/**
 * @return All [entities][E] within the given Euclidean distance from this [Location].
 * A null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.getNearbyEntitiesByType(
	distance: Double?,
	noinline predicate: EntityByTypeDistancePredicate<E>
) = getNearbyEntitiesByType(
	distance = distance,
	predicate.asEntityByTypePredicate(this)
)

/**
 * @return All [entities][E] within the given Euclidean distances from this [Location].
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.getNearbyEntitiesByType(
	xzDistance: Double?,
	yDistance: Double?,
	noinline predicate: EntityByTypePredicate<E>? = null
) = getNearbyEntitiesByTypeChessboardDistance(
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
 * @return All [entities][E] with the given Euclidean distances from this [Location].
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.getNearbyEntitiesByType(
	xzDistance: Double?,
	yDistance: Double?,
	noinline predicate: EntityByTypeDistancePredicate<E>
) = getNearbyEntitiesByType(
	xzDistance = xzDistance,
	yDistance = yDistance,
	predicate.asEntityByTypePredicate(this)
)

/**
 * @return All [entities][E] within the given distance per axis from this [Location].
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.getNearbyEntitiesByType(
	xDistance: Double?,
	yDistance: Double?,
	zDistance: Double?,
	noinline predicate: EntityByTypeDistancePredicate<E>
) = getNearbyEntitiesByType(
	xDistance = xDistance,
	yDistance = yDistance,
	zDistance = zDistance,
	predicate.asEntityByTypePredicate(this)
)

// Get the closest entity out of a selection of entities

/**
 * @return The closest [entity][E] out of the given [entities],
 * or null if the sequence is empty.
 */
inline fun <reified E : Entity> Location.getClosestEntityByType(entities: Sequence<E>) =
	entities.minByOrNull { distanceSquared(it) }

// Get the closest entity, optionally within a Euclidean distance

/**
 * @return The closest [entity][E] with at most the given Euclidean distance from this [Location],
 * or null if none exists.
 * A null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.getClosestEntityByType(
	maxDistance: Double? = null,
	noinline predicate: EntityByTypePredicate<E>? = null
) = getClosestEntityByType(
	getNearbyEntitiesByType(
		distance = maxDistance,
		predicate
	)
)

/**
 * @return The closest [entity][E] with at most the given Euclidean distance from this [Location],
 * or null if none exists.
 * A null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.getClosestEntityByType(
	maxDistance: Double? = null,
	noinline predicate: EntityByTypeDistancePredicate<E>
) = getClosestEntityByType(
	getNearbyEntitiesByType(
		distance = maxDistance,
		predicate
	)
)

/**
 * @return The closest [entity][E] with at most the given Euclidean distances from this [Location],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.getClosestEntityByType(
	maxXZDistance: Double?,
	maxYDistance: Double?,
	noinline predicate: EntityByTypePredicate<E>? = null
) = getClosestEntityByType(
	getNearbyEntitiesByType(
		xzDistance = maxXZDistance,
		yDistance = maxYDistance,
		predicate
	)
)

/**
 * @return The closest [entity][E] with at most the given Euclidean distances from this [Location],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.getClosestEntityByType(
	maxXZDistance: Double?,
	maxYDistance: Double?,
	noinline predicate: EntityByTypeDistancePredicate<E>
) = getClosestEntityByType(
	getNearbyEntitiesByType(
		xzDistance = maxXZDistance,
		yDistance = maxYDistance,
		predicate
	)
)

/**
 * @return The closest [entity][E] with at most the given distance per axis from this [Location],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.getClosestEntityByType(
	maxXDistance: Double?,
	maxYDistance: Double?,
	maxZDistance: Double?,
	noinline predicate: EntityByTypePredicate<E>? = null
) = getClosestEntityByType(
	getNearbyEntitiesByType(
		xDistance = maxXDistance,
		yDistance = maxYDistance,
		zDistance = maxZDistance,
		predicate
	)
)

/**
 * @return The closest [entity][E] with at most the given distance per axis from this [Location],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.getClosestEntityByType(
	maxXDistance: Double?,
	maxYDistance: Double?,
	maxZDistance: Double?,
	noinline predicate: EntityByTypeDistancePredicate<E>
) = getClosestEntityByType(
	getNearbyEntitiesByType(
		xDistance = maxXDistance,
		yDistance = maxYDistance,
		zDistance = maxZDistance,
		predicate
	)
)