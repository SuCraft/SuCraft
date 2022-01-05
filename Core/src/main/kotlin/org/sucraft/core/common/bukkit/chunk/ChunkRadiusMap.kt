/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.bukkit.chunk

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
import org.sucraft.core.common.general.math.RelativeCoordinates


// Class

/**
 * This class stores some value per chunk, and has operations for updating a chunk including its surrounding chunks within a certain radius
 */
@Suppress("MemberVisibilityCanBePrivate")
open class ChunkRadiusMap<T>(
	private val map: Long2ObjectOpenHashMap<T>
	) : MutableIterable<MutableMap.MutableEntry<Long, T>> by map.long2ObjectEntrySet() {

	constructor(initialCapacity: Int = 3) : this(Long2ObjectOpenHashMap(initialCapacity))

	operator fun set(chunk: ChunkCoordinates, value: T) {
		map[chunk.longKeyWithWorld] = value
	}

	fun setAll(chunkSequence: Sequence<ChunkCoordinates>, value: T) {
		chunkSequence.forEach { set(it, value) }
	}

	fun setWithinSquareRadius(chunk: ChunkCoordinates, value: T, radius: Int) {
		setAll(RelativeCoordinates.relativeWithinSquareRadius(radius).map { (dx, dz) -> chunk.getRelative(dx, dz) }, value)
	}

	fun setWithinSquareRadius(chunk: ChunkCoordinates, value: T, radius: Double) {
		setAll(RelativeCoordinates.relativeWithinSquareRadius(radius).map { (dx, dz) -> chunk.getRelative(dx, dz) }, value)
	}

	fun setWithinRadius(chunk: ChunkCoordinates, value: T, radius: Int) {
		setAll(RelativeCoordinates.relativeWithinRadius(radius).map { (dx, dz) -> chunk.getRelative(dx, dz) }, value)
	}

	fun setWithinRadius(chunk: ChunkCoordinates, value: T, radius: Double) {
		setAll(RelativeCoordinates.relativeWithinRadius(radius).map { (dx, dz) -> chunk.getRelative(dx, dz) }, value)
	}

	fun update(chunk: ChunkCoordinates, valueFunction: (T?) -> T) {
		map[chunk.longKeyWithWorld] = valueFunction(map[chunk.longKeyWithWorld])
	}

	fun updateAll(chunkSequence: Sequence<ChunkCoordinates>, valueFunction: (T?, ChunkCoordinates) -> T) {
		chunkSequence.forEach { chunk -> update(chunk) { valueFunction(it, chunk) } }
	}

	fun updateWithinSquareRadius(chunk: ChunkCoordinates, valueFunction: (T?, ChunkCoordinates) -> T, radius: Int) {
		updateAll(RelativeCoordinates.relativeWithinSquareRadius(radius).map { (dx, dz) -> chunk.getRelative(dx, dz) }, valueFunction)
	}

	fun updateWithinSquareRadius(chunk: ChunkCoordinates, valueFunction: (T?, ChunkCoordinates) -> T, radius: Double) {
		updateAll(RelativeCoordinates.relativeWithinSquareRadius(radius).map { (dx, dz) -> chunk.getRelative(dx, dz) }, valueFunction)
	}

	fun updateWithinRadius(chunk: ChunkCoordinates, valueFunction: (T?, ChunkCoordinates) -> T, radius: Int) {
		updateAll(RelativeCoordinates.relativeWithinRadius(radius).map { (dx, dz) -> chunk.getRelative(dx, dz) }, valueFunction)
	}

	fun updateWithinRadius(chunk: ChunkCoordinates, valueFunction: (T?, ChunkCoordinates) -> T, radius: Double) {
		updateAll(RelativeCoordinates.relativeWithinRadius(radius).map { (dx, dz) -> chunk.getRelative(dx, dz) }, valueFunction)
	}

	fun remove(chunk: ChunkCoordinates) = map.remove(chunk.longKeyWithWorld)

	operator fun get(chunk: ChunkCoordinates) = map[chunk.longKeyWithWorld]

	operator fun contains(chunk: ChunkCoordinates) = chunk.longKeyWithWorld in map

	fun getMutableInternalMap(): MutableMap<Long, T> = map

}