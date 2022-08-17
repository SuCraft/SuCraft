/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location.distance.nearbyentities.nearbyentitiesbytype

import org.bukkit.Location
import org.bukkit.entity.Entity
import org.sucraft.common.entity.EntityByTypePredicate

// Get whether there exist entities within 2D chessboard distance

/**
 * @return Whether there exist
 * [entities][E] within the given planar (xz) chessboard distance from this [Location].
 * A null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.hasNearbyEntitiesByTypeChessboardDistance2D(
	distance: Double?,
	noinline predicate: EntityByTypePredicate<E>? = null
) = getNearbyEntitiesByTypeChessboardDistance2D(
	distance,
	predicate
).any()

// Get whether there exist entities within 2D Euclidean distance

/**
 * @return Whether there exist
 * [entities][E] within the given planar (xz) Euclidean distance from this [Location].
 * A null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.hasNearbyEntitiesByType2D(
	distance: Double?,
	noinline predicate: EntityByTypePredicate<E>? = null
) = getNearbyEntitiesByType2D(
	distance,
	predicate
).any()

/**
 * @return Whether there exist
 * [entities][E] within the given planar (xz) Euclidean distance from this [Location].
 * A null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.hasNearbyEntitiesByType2D(
	distance: Double?,
	noinline predicate: EntityByTypeDistancePredicate<E>
) = getNearbyEntitiesByType2D(
	distance,
	predicate
).any()

/**
 * @return Whether there exist
 * [entities][E] within the given distance per planar (xz) axis from this [Location].
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.hasNearbyEntitiesByType2D(
	xDistance: Double?,
	zDistance: Double?,
	noinline predicate: EntityByTypePredicate<E>? = null
) = getNearbyEntitiesByType2D(
	xDistance,
	zDistance,
	predicate
).any()

/**
 * @return Whether there exist
 * [entities][E] within the given distance per planar (xz) axis from this [Location].
 * Any null distance is assumed to be infinite.
 */
inline fun <reified E : Entity> Location.hasNearbyEntitiesByType2D(
	xDistance: Double?,
	zDistance: Double?,
	noinline predicate: EntityByTypeDistancePredicate<E>
) = getNearbyEntitiesByType2D(
	xDistance,
	zDistance,
	predicate
).any()