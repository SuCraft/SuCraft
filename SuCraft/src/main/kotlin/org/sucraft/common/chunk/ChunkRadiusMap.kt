/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.common.chunk

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap

/**
 * This class stores some value per [chunk][ChunkCoordinates],
 * and has operations for updating a chunk including its surrounding chunks within a certain radius.
 */
open class ChunkRadiusMap<T>(
	private val map: Long2ObjectOpenHashMap<T>
) : MutableIterable<MutableMap.MutableEntry<Long, T>> by map.long2ObjectEntrySet() {

	// Secondary constructor

	constructor(initialCapacity: Int = 3) : this(Long2ObjectOpenHashMap(initialCapacity))

	// Provided functionality

	operator fun set(chunk: ChunkCoordinates, value: T) {
		map[chunk.longKeyWithWorld] = value
	}

	fun setAll(chunkSequence: Sequence<ChunkCoordinates>, value: T) {
		chunkSequence.forEach { set(it, value) }
	}

	fun setWithinChessboardRadius(chunk: ChunkCoordinates, value: T, radius: Int) {
		setAll(
			chunk.getRelativeWithinChessboardRadius(radius),
			value
		)
	}

	fun setWithinChessboardRadius(chunk: ChunkCoordinates, value: T, radius: Double) {
		setAll(
			chunk.getRelativeWithinChessboardRadius(radius),
			value
		)
	}

	fun setWithinRadius(chunk: ChunkCoordinates, value: T, radius: Int) {
		setAll(
			chunk.getRelativeWithinRadius(radius),
			value
		)
	}

	fun setWithinRadius(chunk: ChunkCoordinates, value: T, radius: Double) {
		setAll(
			chunk.getRelativeWithinRadius(radius),
			value
		)
	}

	fun update(chunk: ChunkCoordinates, valueFunction: (T?) -> T) {
		map[chunk.longKeyWithWorld] = valueFunction(map[chunk.longKeyWithWorld])
	}

	fun updateAll(chunkSequence: Sequence<ChunkCoordinates>, valueFunction: (T?, ChunkCoordinates) -> T) {
		chunkSequence.forEach { chunk -> update(chunk) { valueFunction(it, chunk) } }
	}

	fun updateWithinChessboardRadius(chunk: ChunkCoordinates, valueFunction: (T?, ChunkCoordinates) -> T, radius: Int) {
		updateAll(
			chunk.getRelativeWithinChessboardRadius(radius),
			valueFunction
		)
	}

	fun updateWithinChessboardRadius(chunk: ChunkCoordinates, valueFunction: (T?, ChunkCoordinates) -> T, radius: Double) {
		updateAll(
			chunk.getRelativeWithinChessboardRadius(radius),
			valueFunction
		)
	}

	fun updateWithinRadius(chunk: ChunkCoordinates, valueFunction: (T?, ChunkCoordinates) -> T, radius: Int) {
		updateAll(
			chunk.getRelativeWithinRadius(radius),
			valueFunction
		)
	}

	fun updateWithinRadius(chunk: ChunkCoordinates, valueFunction: (T?, ChunkCoordinates) -> T, radius: Double) {
		updateAll(
			chunk.getRelativeWithinRadius(radius),
			valueFunction
		)
	}

	fun remove(chunk: ChunkCoordinates): T? = map.remove(chunk.longKeyWithWorld)

	operator fun get(chunk: ChunkCoordinates): T? = map[chunk.longKeyWithWorld]

	operator fun contains(chunk: ChunkCoordinates) = chunk.longKeyWithWorld in map

	// Implementation

	fun getMutableInternalMap(): MutableMap<Long, T> = map

}