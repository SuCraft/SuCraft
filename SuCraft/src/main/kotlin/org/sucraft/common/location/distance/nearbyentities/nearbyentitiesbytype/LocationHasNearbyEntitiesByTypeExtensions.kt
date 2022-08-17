/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location.distance.nearbyentities.nearbyentitiesbytype

import org.bukkit.Location
import org.bukkit.entity.Entity
import org.sucraft.common.entity.EntityByTypePredicate

// Get whether there exist entities within chessboard distance

/**
 * @return Whether there exist
 * [entities][E] within the given chessboard distance from this [Location].
 * A null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.hasNearbyEntitiesByTypeChessboardDistance(
	distance: Double?,
	noinline predicate: EntityByTypePredicate<E>? = null
) = getNearbyEntitiesByTypeChessboardDistance(
	distance,
	predicate
).any()

/**
 * @return Whether there exist
 * [entities][E] within the given chessboard distances from this [Location].
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.hasNearbyEntitiesByTypeChessboardDistance(
	xzDistance: Double?,
	yDistance: Double?,
	noinline predicate: EntityByTypePredicate<E>? = null
) = getNearbyEntitiesByTypeChessboardDistance(
	xzDistance,
	yDistance,
	predicate
).any()

// Get whether there exist entities within Euclidean distance

/**
 * @return Whether there exist
 * [entities][E] within the given Euclidean distance from this [Location].
 * A null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.hasNearbyEntitiesByType(
	distance: Double?,
	noinline predicate: EntityByTypePredicate<E>? = null
) = getNearbyEntitiesByType(
	distance,
	predicate
).any()

/**
 * @return Whether there exist
 * [entities][E] within the given Euclidean distance from this [Location].
 * A null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.hasNearbyEntitiesByType(
	distance: Double?,
	noinline predicate: EntityByTypeDistancePredicate<E>
) = getNearbyEntitiesByType(
	distance,
	predicate
).any()

/**
 * @return Whether there exist
 * [entities][E] within the given Euclidean distances from this [Location].
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.hasNearbyEntitiesByType(
	xzDistance: Double?,
	yDistance: Double?,
	noinline predicate: EntityByTypePredicate<E>? = null
) = getNearbyEntitiesByType(
	xzDistance,
	yDistance,
	predicate
).any()

/**
 * @return Whether there exist
 * [entities][E] with the given Euclidean distances from this [Location].
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.hasNearbyEntitiesByType(
	xzDistance: Double?,
	yDistance: Double?,
	noinline predicate: EntityByTypeDistancePredicate<E>
) = getNearbyEntitiesByType(
	xzDistance,
	yDistance,
	predicate
).any()

/**
 * @return Whether there exist
 * [entities][E] within the given distance per axis from this [Location].
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.hasNearbyEntitiesByType(
	xDistance: Double?,
	yDistance: Double?,
	zDistance: Double?,
	noinline predicate: EntityByTypePredicate<E>? = null
) = getNearbyEntitiesByType(
	xDistance,
	yDistance,
	zDistance,
	predicate
).any()

/**
 * @return Whether there exist
 * [entities][E] within the given distance per axis from this [Location].
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.hasNearbyEntitiesByType(
	xDistance: Double?,
	yDistance: Double?,
	zDistance: Double?,
	noinline predicate: EntityByTypeDistancePredicate<E>
) = getNearbyEntitiesByType(
	xDistance,
	yDistance,
	zDistance,
	predicate
).any()