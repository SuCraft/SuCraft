/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location.distance

import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.sucraft.common.block.centerLocation

/**
 * @return The 3D distance from the center of this [Block] to the given [Location].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Block.distanceFromCenter(location: Location) =
	centerLocation.distance(location)

/**
 * @return The squared 3D distance from the center of this [Block] to the given [Location].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Block.distanceFromCenterSquared(location: Location) =
	centerLocation.distanceSquared(location)

/**
 * @return The 3D distance from the center of this [Block] to the given [Entity].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Block.distanceFromCenter(entity: Entity) =
	centerLocation.distance(entity)

/**
 * @return The squared 3D distance from the center of this [Block] to the given [Entity].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Block.distanceFromCenterSquared(entity: Entity) =
	centerLocation.distanceSquared(entity)

/**
 * @return The 3D distance from the center of this [Block] to the origin of the given [Block].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Block.distanceFromCenterToOrigin(block: Block) =
	centerLocation.distanceToOrigin(block)

/**
 * @return The squared 3D distance from the center of this [Block] to the origin of the given [Block].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Block.distanceFromCenterToOriginSquared(block: Entity) =
	centerLocation.distanceToOriginSquared(block)

/**
 * @return The 3D distance from the center of this [Block] to the center of the given [Block].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Block.distanceFromCenterToCenter(block: Block) =
	centerLocation.distanceToCenter(block)

/**
 * @return The squared 3D distance from the center of this [Block] to the center of the given [Block].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Block.distanceFromCenterToCenterSquared(block: Entity) =
	centerLocation.distanceToCenterSquared(block)

/**
 * @return The planar (xz) distance from the center of this [Block] to the given [Location].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Block.distanceFromCenter2D(location: Location) =
	centerLocation.distance2D(location)

/**
 * @return The squared planar (xz) distance from the center of this [Block] to the given [Location].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Block.distanceFromCenterSquared2D(location: Location) =
	centerLocation.distanceSquared2D(location)

/**
 * @return The planar (xz) distance from the center of this [Block] to the given [Entity].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Block.distanceFromCenter2D(entity: Entity) =
	centerLocation.distance2D(entity)

/**
 * @return The squared planar (xz) distance from the center of this [Block] to the given [Entity].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Block.distanceFromCenterSquared2D(entity: Entity) =
	centerLocation.distanceSquared2D(entity)

/**
 * @return The planar (xz) distance from the center of this [Block] to the origin of the given [Block].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Block.distanceFromCenterToOrigin2D(block: Block) =
	centerLocation.distanceToOrigin2D(block)

/**
 * @return The squared planar (xz) distance from the center of this [Block] to the origin of the given [Block].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Block.distanceFromCenterToOriginSquared2D(block: Entity) =
	centerLocation.distanceToOriginSquared2D(block)

/**
 * @return The planar (xz) distance from the center of this [Block] to the center of the given [Block].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Block.distanceFromCenterToCenter2D(block: Block) =
	centerLocation.distanceToCenter2D(block)

/**
 * @return The squared planar (xz) distance from the center of this [Block] to the center of the given [Block].
 * @throws IllegalArgumentException If the worlds are different.
 */
@Throws(java.lang.IllegalArgumentException::class)
fun Block.distanceFromCenterToCenterSquared2D(block: Entity) =
	centerLocation.distanceToCenterSquared2D(block)