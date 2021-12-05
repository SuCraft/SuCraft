/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.bukkit.chunk


/**
 * This class stores chunks, and has operations for adding a chunk including its surrounding chunks within a certain radius
 */
@Suppress("MemberVisibilityCanBePrivate")
class ChunkRadiusSet(initialCapacity: Int = 3) {

	// TODO replace by set using Long chunk keys as element
	private val set: MutableSet<ChunkCoordinates> = HashSet(initialCapacity)

	fun add(chunk: ChunkCoordinates) {
		set.add(chunk)
	}

	fun addAll(chunkSequence: Sequence<ChunkCoordinates>) {
		chunkSequence.forEach(::add)
	}

	fun addWithinSquareRadius(chunk: ChunkCoordinates, radius: Int) {
		addAll(relativeWithinSquareRadius(radius).map { (dx, dz) -> chunk.getRelative(dx, dz) })
	}

	fun addWithinSquareRadius(chunk: ChunkCoordinates, radius: Double) {
		addAll(relativeWithinSquareRadius(radius).map { (dx, dz) -> chunk.getRelative(dx, dz) })
	}

	fun addWithinRadius(chunk: ChunkCoordinates, radius: Int) {
		addAll(relativeWithinRadius(radius).map { (dx, dz) -> chunk.getRelative(dx, dz) })
	}

	fun addWithinRadius(chunk: ChunkCoordinates, radius: Double) {
		addAll(relativeWithinRadius(radius).map { (dx, dz) -> chunk.getRelative(dx, dz) })
	}

	fun remove(chunk: ChunkCoordinates) = set.remove(chunk)

	fun contains(chunk: ChunkCoordinates) = chunk in set

}