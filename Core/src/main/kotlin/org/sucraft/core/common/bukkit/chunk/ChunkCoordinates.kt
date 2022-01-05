/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */
package org.sucraft.core.common.bukkit.chunk

import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.util.Vector
import org.json.JSONObject
import org.sucraft.core.common.bukkit.block.BlockCoordinates
import kotlin.math.min


@Suppress("MemberVisibilityCanBePrivate")
data class ChunkCoordinates(val worldName: String, val x: Int, val z: Int) : Comparable<ChunkCoordinates> {

	// JSON

	fun toJSON() = JSONObject().also {
		it.put(worldNameDatabaseKey, worldName)
		it.put(xDatabaseKey, x)
		it.put(zDatabaseKey, z)
	}

	// Get the world

	val world: World?
		get() =
			try {
				Bukkit.getWorld(worldName)
			} catch (_: Exception) {
				null
			}

	// Get the chunk

	val isLoaded get() = world?.isChunkLoaded(x, z) ?: false

	@Suppress("DeprecatedCallableAddReplaceWith")
	@Deprecated("This loads the chunk, which is usually not desired")
	val chunkLoadIfNotLoaded
		get() = world?.getChunkAt(x, z)

	@Suppress("DEPRECATION")
	val chunkIfIsLoaded
		get() = if (isLoaded) chunkLoadIfNotLoaded else null

	// Get long keys

	/**
	 * This key is not necessarily the same as the one that Paper provides
	 */
	val longKeyWithoutWorld
		get() =
			(x - chunkCoordinateLowerBound) +
					chunkCoordinateRangeSize.toLong() *
					(z - chunkCoordinateLowerBound)
	val longKeyWithWorld
		get() =
			longKeyWithoutWorld +
					chunkCoordinateRangeSize.toLong() *
					chunkCoordinateRangeSize *
					(worldName.hashCode() % longKeyWithWorldWorldRangeSize)

	// Get other chunk coordinates

	fun getRelative(dx: Int, dz: Int) = copy(x = x + dx, z = z + dz)

	// Get the region

	val regionX get() = x shr 5
	val regionZ get() = z shr 5

	val regionCoordinates get() = RegionCoordinates.get(worldName, regionX, regionZ)

	// Get blocks

	val minBlockX get() = x * 16
	val minBlockZ get() = z * 16
	val minMiddleBlockX get() = minBlockX + 7
	val minMiddleBlockZ get() = minBlockZ + 7

	/**
	 * This is the x-coordinate at the center of the chunk
	 */
	val maxMiddleBlockX get() = minBlockX + 8

	/**
	 * This is the z-coordinate at the center of the chunk
	 */
	val maxMiddleBlockZ get() = minBlockZ + 8
	val maxBlockX get() = minBlockX + 15
	val maxBlockZ get() = minBlockZ + 15

	fun getSquaredDistanceToCenterOfChunk(blockX: Double, blockZ: Double): Double {
		val dx = maxMiddleBlockX.toDouble() - blockX
		val dz = maxMiddleBlockZ.toDouble() - blockZ
		return dx * dx + dz * dz
	}

	fun getSquaredPlanarDistanceToCenterOfChunk(vector: Vector) = getSquaredDistanceToCenterOfChunk(vector.x, vector.z)

	// To readable string

	override fun toString() = "($worldName: $x, $z)"

	// Comparable

	override fun compareTo(other: ChunkCoordinates): Int {
		val worldCompare = worldName.compareTo(other.worldName)
		if (worldCompare != 0) return worldCompare
		val xCompare = x.compareTo(other.x)
		if (xCompare != 0) return xCompare
		return z.compareTo(other.z)
	}

	// Companion

	companion object {

		// World size bounds (not tight)

		const val chunkCoordinateUpperBound = (BlockCoordinates.blockCoordinateUpperBound + 15) / 16
		const val chunkCoordinateLowerBound = -chunkCoordinateUpperBound
		const val chunkCoordinateRangeSize = chunkCoordinateUpperBound - chunkCoordinateLowerBound + 1

		// For a world range size of 500000, the chance of a collision with 50 worlds is ~0.2%, and with 15 worlds is ~0.02%
		val longKeyWithWorldWorldRangeSize = min(500000, Long.MAX_VALUE / chunkCoordinateRangeSize / chunkCoordinateRangeSize)

		fun getLongKeyWithWorldWorldPart(world: World) =
			getLongKeyWithWorldWorldPart(world.name)

		fun getLongKeyWithWorldWorldPart(worldName: String) =
			Math.floorMod(worldName.hashCode().toLong(), longKeyWithWorldWorldRangeSize)

		// Construction

		fun get(worldName: String, x: Int, z: Int) = ChunkCoordinates(worldName, x, z)

		fun get(world: World, x: Int, z: Int) = get(world.name, x, z)

		fun get(chunk: Chunk) = get(chunk.world.name, chunk.x, chunk.z)

		fun get(block: Block) = get(block.chunk)

		fun get(location: Location) = BlockCoordinates.get(location).chunkCoordinates

		// From JSON

		private const val worldNameDatabaseKey = "world_name"
		private const val xDatabaseKey = "x"
		private const val zDatabaseKey = "z"

		fun fromJSON(json: JSONObject) = get(
			json.getString(worldNameDatabaseKey),
			json.getInt(xDatabaseKey),
			json.getInt(zDatabaseKey)
		)

	}

}