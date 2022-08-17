/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.math

import kotlin.math.ceil

// 2D

/**
 * @param radius A chessboard distance radius.
 * @return A sequence of all the coordinates that are within the given [radius] from a center point at (0, 0).
 */
fun coordinatesWithinChessboardRadius2D(radius: Int) = sequence {
	for (dx in -radius..radius)
		for (dz in -radius..radius)
			yield(dx to dz)
}

/**
 * @param x The x-coordinate of the center point.
 * @param z The z-coordinate of the center point.
 * @param radius A chessboard distance radius.
 * @return A sequence of all the coordinates that are within the given [radius] from the given point ([x], [z]).
 */
fun coordinatesWithinChessboardRadius2D(x: Int, z: Int, radius: Int) =
	coordinatesWithinChessboardRadius2D(radius).map { (dx, dz) -> x + dx to z + dz }

/**
 * Similar to [coordinatesWithinChessboardRadius2D], but uses the [ceil] of the given [radius] (to include
 * all coordinate cells partially within the given floating-point radius).
 *
 * @see coordinatesWithinChessboardRadius2D
 */
fun coordinatesWithinChessboardRadius2D(radius: Double) =
	coordinatesWithinChessboardRadius2D(ceil(radius).toInt())

/**
 * Similar to [coordinatesWithinChessboardRadius2D], but uses the [ceil] of the given [radius] (to include
 * all coordinate cells partially within the given floating-point radius).
 *
 * @see coordinatesWithinChessboardRadius2D
 */
fun coordinatesWithinChessboardRadius2D(x: Int, z: Int, radius: Double) =
	coordinatesWithinChessboardRadius2D(radius).map { (dx, dz) -> x + dx to z + dz }

/**
 * @param radius An Euclidean distance radius.
 * @return A sequence of all the coordinates that are within the given [radius] from a center point at (0, 0).
 */
fun coordinatesWithinRadius2D(radius: Int): Sequence<Pair<Int, Int>> {
	val radiusSquared = radius * radius
	return coordinatesWithinChessboardRadius2D(radius)
		.filter { (dx, dz) -> dx * dx + dz * dz <= radiusSquared }
}

/**
 * @param x The x-coordinate of the center point.
 * @param z The z-coordinate of the center point.
 * @param radius An Euclidean distance radius.
 * @return A sequence of all the coordinates that are within the given [radius] from the given point ([x], [z]).
 */
fun coordinatesWithinRadius2D(x: Int, z: Int, radius: Int) =
	coordinatesWithinRadius2D(radius).map { (dx, dz) -> x + dx to z + dz }


/**
 * @see coordinatesWithinRadius2D
 */
fun coordinatesWithinRadius2D(radius: Double): Sequence<Pair<Int, Int>> {
	val radiusSquared = radius * radius
	return coordinatesWithinChessboardRadius2D(radius)
		.filter { (dx, dz) -> dx * dx + dz * dz <= radiusSquared }
}

/**
 * @see coordinatesWithinRadius2D
 */
fun coordinatesWithinRadius2D(x: Int, z: Int, radius: Double) =
	coordinatesWithinRadius2D(radius).map { (dx, dz) -> x + dx to z + dz }

// 3D

/**
 * @param radius A chessboard distance radius.
 * @return A sequence of all the coordinates that are within the given [radius] from a center point at (0, 0, 0).
 */
fun coordinatesWithinChessboardRadius3D(radius: Int) = sequence {
	for (dx in -radius..radius)
		for (dy in -radius..radius)
			for (dz in -radius..radius)
				yield(Triple(dx, dy , dz))
}

/**
 * @param x The x-coordinate of the center point.
 * @param y The y-coordinate of the center point.
 * @param z The z-coordinate of the center point.
 * @param radius A chessboard distance radius.
 * @return A sequence of all the coordinates that are within the given [radius] from the given point ([x], [y], [z]).
 */
fun coordinatesWithinChessboardRadius3D(x: Int, y: Int, z: Int, radius: Int) =
	coordinatesWithinChessboardRadius3D(radius).map { (dx, dy, dz) -> Triple(x + dx, y + dy, z + dz) }

/**
 * Similar to [coordinatesWithinChessboardRadius3D], but uses the [ceil] of the given [radius] (to include
 * all coordinate cells partially within the given floating-point radius).
 *
 * @see coordinatesWithinChessboardRadius3D
 */
fun coordinatesWithinChessboardRadius3D(radius: Double) =
	coordinatesWithinChessboardRadius3D(ceil(radius).toInt())

/**
 * Similar to [coordinatesWithinChessboardRadius3D], but uses the [ceil] of the given [radius] (to include
 * all coordinate cells partially within the given floating-point radius).
 *
 * @see coordinatesWithinChessboardRadius3D
 */
fun coordinatesWithinChessboardRadius3D(x: Int, y: Int, z: Int, radius: Double) =
	coordinatesWithinChessboardRadius3D(radius).map { (dx, dy, dz) -> Triple(x + dx, y + dy, z + dz) }

/**
 * @param radius An Euclidean distance radius.
 * @return A sequence of all the coordinates that are within the given [radius] from a center point at (0, 0, 0).
 */
fun coordinatesWithinRadius3D(radius: Int): Sequence<Triple<Int, Int, Int>> {
	val radiusSquared = radius * radius
	return coordinatesWithinChessboardRadius3D(radius)
		.filter { (dx, dy, dz) -> dx * dx + dy * dy + dz * dz <= radiusSquared }
}

/**
 * @param x The x-coordinate of the center point.
 * @param y The x-coordinate of the center point.
 * @param z The z-coordinate of the center point.
 * @param radius An Euclidean distance radius.
 * @return A sequence of all the coordinates that are within the given [radius] from the given point ([x], [z], [y]).
 */
fun coordinatesWithinRadius3D(x: Int, y: Int, z: Int, radius: Int) =
	coordinatesWithinRadius3D(radius).map { (dx, dy, dz) -> Triple(x + dx, y + dy, z + dz) }


/**
 * @see coordinatesWithinRadius3D
 */
fun coordinatesWithinRadius3D(radius: Double): Sequence<Triple<Int, Int, Int>> {
	val radiusSquared = radius * radius
	return coordinatesWithinChessboardRadius3D(radius)
		.filter { (dx, dy, dz) -> dx * dx + dy * dy + dz * dz <= radiusSquared }
}

/**
 * @see coordinatesWithinRadius3D
 */
fun coordinatesWithinRadius3D(x: Int, y: Int, z: Int, radius: Double) =
	coordinatesWithinRadius3D(radius).map { (dx, dy, dz) -> Triple(x + dx, y + dy, z + dz) }