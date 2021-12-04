/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.bukkit.chunk

import kotlin.math.ceil


// Utility

fun relativeWithinSquareRadius(radius: Int): Sequence<Pair<Int, Int>> = sequence {
	for (dx in -radius..radius) {
		for (dz in -radius..radius) {
			yield(Pair(dz, dz))
		}
	}
}

fun relativeWithinSquareRadius(radius: Double) = relativeWithinSquareRadius(ceil(radius).toInt())

fun relativeWithinRadius(radius: Int) = relativeWithinSquareRadius(radius).filter { (dx, dz) -> dx * dx + dz * dz <= radius * radius }

fun relativeWithinRadius(radius: Double) = relativeWithinSquareRadius(radius).filter { (dx, dz) -> dx * dx + dz * dz <= radius * radius }

// Class

/**
 * This class stores some value per chunk, and has operations for updating a chunk including its surrounding chunks within a certain radius
 */
@Suppress("MemberVisibilityCanBePrivate")
class ChunkRadiusMap<T> {

	private val map: MutableMap<ChunkCoordinates, T> = HashMap()

	fun set(chunk: ChunkCoordinates, value: T) {
		map[chunk] = value
	}

	fun setAll(chunkSequence: Sequence<ChunkCoordinates>, value: T) {
		chunkSequence.forEach { set(it, value) }
	}

	fun setWithinSquareRadius(chunk: ChunkCoordinates, value: T, radius: Int) {
		setAll(relativeWithinSquareRadius(radius).map { (dx, dz) -> chunk.getRelative(dx, dz) }, value)
	}

	fun setWithinSquareRadius(chunk: ChunkCoordinates, value: T, radius: Double) {
		setAll(relativeWithinSquareRadius(radius).map { (dx, dz) -> chunk.getRelative(dx, dz) }, value)
	}

	fun setWithinRadius(chunk: ChunkCoordinates, value: T, radius: Int) {
		setAll(relativeWithinRadius(radius).map { (dx, dz) -> chunk.getRelative(dx, dz) }, value)
	}

	fun setWithinRadius(chunk: ChunkCoordinates, value: T, radius: Double) {
		setAll(relativeWithinRadius(radius).map { (dx, dz) -> chunk.getRelative(dx, dz) }, value)
	}

	fun remove(chunk: ChunkCoordinates) = map.remove(chunk)

	fun get(chunk: ChunkCoordinates) = map[chunk]

	fun contains(chunk: ChunkCoordinates) = chunk in map

}