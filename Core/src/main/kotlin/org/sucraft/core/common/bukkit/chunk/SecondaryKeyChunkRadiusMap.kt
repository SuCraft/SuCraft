/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.core.common.bukkit.chunk

import org.sucraft.core.common.general.math.RelativeCoordinates

open class SecondaryKeyChunkRadiusMap<K, V>(initialCapacity: Int = 3) : ChunkRadiusMap<MutableMap<K, V>>(initialCapacity) {

	protected open fun constructSecondaryMap(): MutableMap<K, V> = HashMap(1)

	operator fun set(chunk: ChunkCoordinates, secondaryKey: K, value: V) {
		update(chunk, secondaryKey) { value }
	}

	fun setAll(chunkSequence: Sequence<ChunkCoordinates>, secondaryKey: K, value: V) {
		chunkSequence.forEach { set(it, secondaryKey, value) }
	}

	fun setWithinSquareRadius(chunk: ChunkCoordinates, secondaryKey: K, value: V, radius: Int) {
		setAll(RelativeCoordinates.relativeWithinSquareRadius(radius).map { (dx, dz) -> chunk.getRelative(dx, dz) }, secondaryKey, value)
	}

	fun setWithinSquareRadius(chunk: ChunkCoordinates, secondaryKey: K, value: V, radius: Double) {
		setAll(RelativeCoordinates.relativeWithinSquareRadius(radius).map { (dx, dz) -> chunk.getRelative(dx, dz) }, secondaryKey, value)
	}

	fun setWithinRadius(chunk: ChunkCoordinates, secondaryKey: K, value: V, radius: Int) {
		setAll(RelativeCoordinates.relativeWithinRadius(radius).map { (dx, dz) -> chunk.getRelative(dx, dz) }, secondaryKey, value)
	}

	fun setWithinRadius(chunk: ChunkCoordinates, secondaryKey: K, value: V, radius: Double) {
		setAll(RelativeCoordinates.relativeWithinRadius(radius).map { (dx, dz) -> chunk.getRelative(dx, dz) }, secondaryKey, value)
	}

	fun update(chunk: ChunkCoordinates, secondaryKey: K, valueFunction: (V?) -> V) =
		super.update(chunk) {
			(it ?: constructSecondaryMap()).also { it[secondaryKey] = valueFunction(it[secondaryKey]) }
		}

	fun updateAll(chunkSequence: Sequence<ChunkCoordinates>, secondaryKey: K, valueFunction: (V?, ChunkCoordinates) -> V) {
		chunkSequence.forEach { chunk -> update(chunk, secondaryKey) { valueFunction(it, chunk) } }
	}

	fun updateWithinSquareRadius(chunk: ChunkCoordinates, secondaryKey: K, valueFunction: (V?, ChunkCoordinates) -> V, radius: Int) {
		updateAll(RelativeCoordinates.relativeWithinSquareRadius(radius).map { (dx, dz) -> chunk.getRelative(dx, dz) }, secondaryKey, valueFunction)
	}

	fun updateWithinSquareRadius(chunk: ChunkCoordinates, secondaryKey: K, valueFunction: (V?, ChunkCoordinates) -> V, radius: Double) {
		updateAll(RelativeCoordinates.relativeWithinSquareRadius(radius).map { (dx, dz) -> chunk.getRelative(dx, dz) }, secondaryKey, valueFunction)
	}

	fun updateWithinRadius(chunk: ChunkCoordinates, secondaryKey: K, valueFunction: (V?, ChunkCoordinates) -> V, radius: Int) {
		updateAll(RelativeCoordinates.relativeWithinRadius(radius).map { (dx, dz) -> chunk.getRelative(dx, dz) }, secondaryKey, valueFunction)
	}

	fun updateWithinRadius(chunk: ChunkCoordinates, secondaryKey: K, valueFunction: (V?, ChunkCoordinates) -> V, radius: Double) {
		updateAll(RelativeCoordinates.relativeWithinRadius(radius).map { (dx, dz) -> chunk.getRelative(dx, dz) }, secondaryKey, valueFunction)
	}

	fun remove(chunk: ChunkCoordinates, secondaryKey: K) {
		val mapForChunk = super.get(chunk) ?: return
		mapForChunk.remove(secondaryKey) ?: return
		if (mapForChunk.isEmpty()) super.remove(chunk)
	}

	operator fun get(chunk: ChunkCoordinates, secondaryKey: K) = super.get(chunk)?.let { it[secondaryKey] }

	fun contains(chunk: ChunkCoordinates, secondaryKey: K) = super.get(chunk)?.let { secondaryKey in it } ?: false

}