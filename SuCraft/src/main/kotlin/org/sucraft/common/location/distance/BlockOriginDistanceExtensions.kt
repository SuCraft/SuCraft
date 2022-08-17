/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location.distance

import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Entity

/**
 * @return The 3D distance from the origin of this [Block] to the given [Location].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Block.distanceFromOrigin(location: Location) =
	location.distance(location)

/**
 * @return The squared 3D distance from the origin of this [Block] to the given [Location].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Block.distanceFromOriginSquared(location: Location) =
	location.distanceSquared(location)

/**
 * @return The 3D distance from the origin of this [Block] to the given [Entity].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Block.distanceFromOrigin(entity: Entity) =
	location.distance(entity)

/**
 * @return The squared 3D distance from the origin of this [Block] to the given [Entity].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Block.distanceFromOriginSquared(entity: Entity) =
	location.distanceSquared(entity)

/**
 * @return The 3D distance from the origin of this [Block] to the origin of the given [Block].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Block.distanceFromOriginToOrigin(block: Block) =
	location.distanceToOrigin(block)

/**
 * @return The squared 3D distance from the origin of this [Block] to the origin of the given [Block].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Block.distanceFromOriginToOriginSquared(block: Entity) =
	location.distanceToOriginSquared(block)

/**
 * @return The 3D distance from the origin of this [Block] to the center of the given [Block].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Block.distanceFromOriginToCenter(block: Block) =
	location.distanceToCenter(block)

/**
 * @return The squared 3D distance from the origin of this [Block] to the center of the given [Block].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Block.distanceFromOriginToCenterSquared(block: Entity) =
	location.distanceToCenterSquared(block)

/**
 * @return The planar (xz) distance from the origin of this [Block] to the given [Location].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Block.distanceFromOrigin2D(location: Location) =
	location.distance2D(location)

/**
 * @return The squared planar (xz) distance from the origin of this [Block] to the given [Location].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Block.distanceFromOriginSquared2D(location: Location) =
	location.distanceSquared2D(location)

/**
 * @return The planar (xz) distance from the origin of this [Block] to the given [Entity].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Block.distanceFromOrigin2D(entity: Entity) =
	location.distance2D(entity)

/**
 * @return The squared planar (xz) distance from the origin of this [Block] to the given [Entity].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Block.distanceFromOriginSquared2D(entity: Entity) =
	location.distanceSquared2D(entity)

/**
 * @return The planar (xz) distance from the origin of this [Block] to the origin of the given [Block].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Block.distanceFromOriginToOrigin2D(block: Block) =
	location.distanceToOrigin2D(block)

/**
 * @return The squared planar (xz) distance from the origin of this [Block] to the origin of the given [Block].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Block.distanceFromOriginToOriginSquared2D(block: Entity) =
	location.distanceToOriginSquared2D(block)

/**
 * @return The planar (xz) distance from the origin of this [Block] to the center of the given [Block].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Block.distanceFromOriginToCenter2D(block: Block) =
	location.distanceToCenter2D(block)

/**
 * @return The squared planar (xz) distance from the origin of this [Block] to the center of the given [Block].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Block.distanceFromOriginToCenterSquared2D(block: Entity) =
	location.distanceToCenterSquared2D(block)