/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location.distance.nearbyentities

import org.bukkit.entity.Entity
import org.sucraft.common.entity.EntityPredicate

// Get whether there exist entities within 2D chessboard distance

/**
 * @return Whether there exist
 * [entities][Entity] within the given planar (xz) chessboard distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
fun Entity.hasNearbyEntitiesChessboardDistance2D(
	distance: Double?,
	predicate: EntityPredicate? = null
) = getNearbyEntitiesChessboardDistance2D(
	distance,
	predicate
).any()

// Get whether there exist entities within 2D Euclidean distance

/**
 * @return Whether there exist
 * [entities][Entity] within the given planar (xz) Euclidean distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
fun Entity.hasNearbyEntities2D(
	distance: Double?,
	predicate: EntityPredicate? = null
) = getNearbyEntities2D(
	distance,
	predicate
).any()

/**
 * @return Whether there exist
 * [entities][Entity] within the given planar (xz) Euclidean distance from this [Entity].
 * A null distance is assumed to be infinite.
 */
fun Entity.hasNearbyEntities2D(
	distance: Double?,
	predicate: EntityDistancePredicate
) = getNearbyEntities2D(
	distance,
	predicate
).any()

/**
 * @return Whether there exist
 * [entities][Entity] within the given distance per planar (xz) axis from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.hasNearbyEntities2D(
	xDistance: Double?,
	zDistance: Double?,
	predicate: EntityPredicate? = null
) = getNearbyEntities2D(
	xDistance,
	zDistance,
	predicate
).any()

/**
 * @return Whether there exist
 * [entities][Entity] within the given distance per planar (xz) axis from this [Entity].
 * Any null distance is assumed to be infinite.
 */
fun Entity.hasNearbyEntities2D(
	xDistance: Double?,
	zDistance: Double?,
	predicate: EntityDistancePredicate
) = getNearbyEntities2D(
	xDistance,
	zDistance,
	predicate
).any()