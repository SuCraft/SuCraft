/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.bukkit.chunk

import it.unimi.dsi.fastutil.longs.LongOpenHashSet
import org.sucraft.core.common.general.math.RelativeCoordinates

/**
 * This class stores chunks, and has operations for adding a chunk including its surrounding chunks within a certain radius
 */
@Suppress("MemberVisibilityCanBePrivate")
class ChunkRadiusSet(private val set: LongOpenHashSet) : MutableIterable<Long> by set {

	constructor(initialCapacity: Int = 3) : this(LongOpenHashSet(initialCapacity))

	fun add(chunk: ChunkCoordinates) {
		set.add(chunk.longKeyWithWorld)
	}

	fun addAll(chunkSequence: Sequence<ChunkCoordinates>) {
		chunkSequence.forEach(::add)
	}

	fun addWithinSquareRadius(chunk: ChunkCoordinates, radius: Int) {
		addAll(RelativeCoordinates.relativeWithinSquareRadius(radius).map { (dx, dz) -> chunk.getRelative(dx, dz) })
	}

	fun addWithinSquareRadius(chunk: ChunkCoordinates, radius: Double) {
		addAll(RelativeCoordinates.relativeWithinSquareRadius(radius).map { (dx, dz) -> chunk.getRelative(dx, dz) })
	}

	fun addWithinRadius(chunk: ChunkCoordinates, radius: Int) {
		addAll(RelativeCoordinates.relativeWithinRadius(radius).map { (dx, dz) -> chunk.getRelative(dx, dz) })
	}

	fun addWithinRadius(chunk: ChunkCoordinates, radius: Double) {
		addAll(RelativeCoordinates.relativeWithinRadius(radius).map { (dx, dz) -> chunk.getRelative(dx, dz) })
	}

	fun remove(chunk: ChunkCoordinates) = set.remove(chunk.longKeyWithWorld)

	operator fun contains(chunk: ChunkCoordinates) = chunk.longKeyWithWorld in set

}