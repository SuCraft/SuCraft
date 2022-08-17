/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location.distance

import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import kotlin.math.sqrt

/**
 * @return The 3D distance from this [Location] to the given [Entity].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Location.distance(entity: Entity) =
	distance(entity.location)

/**
 * @return The squared 3D distance from this [Location] to the given [Entity].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Location.distanceSquared(entity: Entity) =
	distanceSquared(entity.location)

/**
 * @return The 3D distance from this [Location] to the origin of the given [Block].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Location.distanceToOrigin(block: Block) =
	distance(block.location)

/**
 * @return The squared 3D distance from this [Location] to the origin of the given [Block].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Location.distanceToOriginSquared(block: Entity) =
	distanceSquared(block.location)

/**
 * @return The 3D distance from this [Location] to the center of the given [Block].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Location.distanceToCenter(block: Block) =
	distance(block.location)

/**
 * @return The squared 3D distance from this [Location] to the center of the given [Block].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Location.distanceToCenterSquared(block: Entity) =
	distanceSquared(block.location)

/**
 * @return The planar (xz) distance from this [Location] to the given [Location].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Location.distance2D(location: Location) =
	sqrt(distanceSquared2D(location))

/**
 * @return The squared planar (xz) distance from this [Location] to the given [Location].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Location.distanceSquared2D(location: Location) =
	if (location.world != world)
		throw IllegalArgumentException(
			"Cannot measure distance between " + world!!.name + " and " + location.world!!.name
		)
	else
		(location.x - x) * (location.x - x) + (location.z - z) * (location.z - z)

/**
 * @return The planar (xz) distance from this [Location] to the given [Entity].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Location.distance2D(entity: Entity) =
	distance2D(entity.location)

/**
 * @return The squared planar (xz) distance from this [Location] to the given [Entity].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Location.distanceSquared2D(entity: Entity) =
	distanceSquared2D(entity.location)

/**
 * @return The planar (xz) distance from this [Location] to the origin of the given [Block].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Location.distanceToOrigin2D(block: Block) =
	distance2D(block.location)

/**
 * @return The squared planar (xz) distance from this [Location] to the origin of the given [Block].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Location.distanceToOriginSquared2D(block: Entity) =
	distanceSquared2D(block.location)

/**
 * @return The planar (xz) distance from this [Location] to the center of the given [Block].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Location.distanceToCenter2D(block: Block) =
	distance2D(block.location)

/**
 * @return The squared planar (xz) distance from this [Location] to the center of the given [Block].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Location.distanceToCenterSquared2D(block: Entity) =
	distanceSquared2D(block.location)