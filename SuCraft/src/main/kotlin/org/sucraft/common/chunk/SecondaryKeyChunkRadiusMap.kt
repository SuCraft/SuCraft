/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.chunk

/**
 * This class stores some value per pair of a [chunk][ChunkCoordinates] and some value of a [secondary key type][K],
 * and has operations for updating the value for such a pair including its surrounding chunks within a certain radius.
 */
open class SecondaryKeyChunkRadiusMap<K, V>(initialCapacity: Int = 3) :
	ChunkRadiusMap<MutableMap<K, V>>(initialCapacity) {

	// Overridable implementation

	/**
	 * This method can be overridden to provide a more efficient implementation of the secondary map used per chunk.
	 */
	protected open fun constructSecondaryMap(): MutableMap<K, V> = HashMap(1)

	// Provided functionality

	operator fun set(chunk: ChunkCoordinates, secondaryKey: K, value: V) {
		update(chunk, secondaryKey) { value }
	}

	fun setAll(chunkSequence: Sequence<ChunkCoordinates>, secondaryKey: K, value: V) {
		chunkSequence.forEach { set(it, secondaryKey, value) }
	}

	fun setWithinChessboardRadius(chunk: ChunkCoordinates, secondaryKey: K, value: V, radius: Int) {
		setAll(
			chunk.getRelativeWithinChessboardRadius(radius),
			secondaryKey,
			value
		)
	}

	fun setWithinChessboardRadius(chunk: ChunkCoordinates, secondaryKey: K, value: V, radius: Double) {
		setAll(
			chunk.getRelativeWithinChessboardRadius(radius),
			secondaryKey,
			value
		)
	}

	fun setWithinRadius(chunk: ChunkCoordinates, secondaryKey: K, value: V, radius: Int) {
		setAll(
			chunk.getRelativeWithinRadius(radius),
			secondaryKey,
			value
		)
	}

	fun setWithinRadius(chunk: ChunkCoordinates, secondaryKey: K, value: V, radius: Double) {
		setAll(
			chunk.getRelativeWithinRadius(radius),
			secondaryKey,
			value
		)
	}

	fun update(chunk: ChunkCoordinates, secondaryKey: K, valueFunction: (V?) -> V) =
		super.update(chunk) {
			(it ?: constructSecondaryMap()).also { it[secondaryKey] = valueFunction(it[secondaryKey]) }
		}

	fun updateAll(
		chunkSequence: Sequence<ChunkCoordinates>,
		secondaryKey: K,
		valueFunction: (V?, ChunkCoordinates) -> V
	) {
		chunkSequence.forEach { chunk -> update(chunk, secondaryKey) { valueFunction(it, chunk) } }
	}

	fun updateWithinChessboardRadius(
		chunk: ChunkCoordinates,
		secondaryKey: K,
		valueFunction: (V?, ChunkCoordinates) -> V,
		radius: Int
	) {
		updateAll(
			chunk.getRelativeWithinChessboardRadius(radius),
			secondaryKey,
			valueFunction
		)
	}

	fun updateWithinChessboardRadius(
		chunk: ChunkCoordinates,
		secondaryKey: K,
		valueFunction: (V?, ChunkCoordinates) -> V,
		radius: Double
	) {
		updateAll(
			chunk.getRelativeWithinChessboardRadius(radius),
			secondaryKey,
			valueFunction
		)
	}

	fun updateWithinRadius(
		chunk: ChunkCoordinates,
		secondaryKey: K,
		valueFunction: (V?, ChunkCoordinates) -> V,
		radius: Int
	) {
		updateAll(
			chunk.getRelativeWithinRadius(radius),
			secondaryKey,
			valueFunction
		)
	}

	fun updateWithinRadius(
		chunk: ChunkCoordinates,
		secondaryKey: K,
		valueFunction: (V?, ChunkCoordinates) -> V,
		radius: Double
	) {
		updateAll(
			chunk.getRelativeWithinRadius(radius),
			secondaryKey,
			valueFunction
		)
	}

	fun remove(chunk: ChunkCoordinates, secondaryKey: K) {
		val mapForChunk = super.get(chunk) ?: return
		mapForChunk.remove(secondaryKey) ?: return
		if (mapForChunk.isEmpty()) super.remove(chunk)
	}

	operator fun get(chunk: ChunkCoordinates, secondaryKey: K) = super.get(chunk)?.let { it[secondaryKey] }

	fun contains(chunk: ChunkCoordinates, secondaryKey: K) = super.get(chunk)?.let { secondaryKey in it } ?: false

}