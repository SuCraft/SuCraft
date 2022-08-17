/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location.distance

import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Entity

/**
 * @return The 3D distance from this [Entity] to the given [Location].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Entity.distance(location: Location) =
	location.distance(location)

/**
 * @return The squared 3D distance from this [Entity] to the given [Location].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Entity.distanceSquared(location: Location) =
	location.distanceSquared(location)

/**
 * @return The 3D distance from this [Entity] to the given [Entity].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Entity.distance(entity: Entity) =
	location.distance(entity)

/**
 * @return The squared 3D distance from this [Entity] to the given [Entity].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Entity.distanceSquared(entity: Entity) =
	location.distanceSquared(entity)

/**
 * @return The 3D distance from this [Entity] to the origin of the given [Block].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Entity.distanceToOrigin(block: Block) =
	location.distanceToOrigin(block)

/**
 * @return The squared 3D distance from this [Entity] to the origin of the given [Block].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Entity.distanceToOriginSquared(block: Entity) =
	location.distanceToOriginSquared(block)

/**
 * @return The 3D distance from this [Entity] to the center of the given [Block].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Entity.distanceToCenter(block: Block) =
	location.distanceToCenter(block)

/**
 * @return The squared 3D distance from this [Entity] to the center of the given [Block].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Entity.distanceToCenterSquared(block: Entity) =
	location.distanceToCenterSquared(block)

/**
 * @return The planar (xz) distance from this [Entity] to the given [Location].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Entity.distance2D(location: Location) =
	location.distance2D(location)

/**
 * @return The squared planar (xz) distance from this [Entity] to the given [Location].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Entity.distanceSquared2D(location: Location) =
	location.distanceSquared2D(location)

/**
 * @return The planar (xz) distance from this [Entity] to the given [Entity].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Entity.distance2D(entity: Entity) =
	location.distance2D(entity)

/**
 * @return The squared planar (xz) distance from this [Entity] to the given [Entity].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Entity.distanceSquared2D(entity: Entity) =
	location.distanceSquared2D(entity)

/**
 * @return The planar (xz) distance from this [Entity] to the origin of the given [Block].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Entity.distanceToOrigin2D(block: Block) =
	location.distanceToOrigin2D(block)

/**
 * @return The squared planar (xz) distance from this [Entity] to the origin of the given [Block].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Entity.distanceToOriginSquared2D(block: Entity) =
	location.distanceToOriginSquared2D(block)

/**
 * @return The planar (xz) distance from this [Entity] to the center of the given [Block].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Entity.distanceToCenter2D(block: Block) =
	location.distanceToCenter2D(block)

/**
 * @return The squared planar (xz) distance from this [Entity] to the center of the given [Block].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Entity.distanceToCenterSquared2D(block: Entity) =
	location.distanceToCenterSquared2D(block)