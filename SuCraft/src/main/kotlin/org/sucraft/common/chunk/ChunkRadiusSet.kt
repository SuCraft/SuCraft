/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.common.chunk

import it.unimi.dsi.fastutil.longs.LongOpenHashSet

/**
 * This class stores [chunks][ChunkCoordinates],
 * and has operations for adding a chunk including its surrounding chunks within a certain radius.
 */
class ChunkRadiusSet(private val set: LongOpenHashSet) : MutableIterable<Long> by set {

	// Secondary constructor

	constructor(initialCapacity: Int = 3) : this(LongOpenHashSet(initialCapacity))

	// Provided functionality

	fun add(chunk: ChunkCoordinates) {
		set.add(chunk.longKeyWithWorld)
	}

	fun addAll(chunkSequence: Sequence<ChunkCoordinates>) {
		chunkSequence.forEach(::add)
	}

	fun addWithinChessboardRadius(chunk: ChunkCoordinates, radius: Int) {
		addAll(chunk.getRelativeWithinChessboardRadius(radius))
	}

	fun addWithinChessboardRadius(chunk: ChunkCoordinates, radius: Double) {
		addAll(chunk.getRelativeWithinChessboardRadius(radius))
	}

	fun addWithinRadius(chunk: ChunkCoordinates, radius: Int) {
		addAll(chunk.getRelativeWithinRadius(radius))
	}

	fun addWithinRadius(chunk: ChunkCoordinates, radius: Double) {
		addAll(chunk.getRelativeWithinRadius(radius))
	}

	fun remove(chunk: ChunkCoordinates) = set.remove(chunk.longKeyWithWorld)

	operator fun contains(chunk: ChunkCoordinates) = chunk.longKeyWithWorld in set

}