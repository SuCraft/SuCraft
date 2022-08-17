/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */
package org.sucraft.common.chunk

import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.util.Vector
import org.json.JSONObject
import org.sucraft.common.block.BlockCoordinates
import org.sucraft.common.block.BlockCoordinates.Companion.getBlockCoordinates
import org.sucraft.common.math.coordinatesWithinChessboardRadius2D
import org.sucraft.common.math.coordinatesWithinRadius2D
import org.sucraft.common.region.RegionCoordinates
import kotlin.math.min
import kotlin.math.sqrt

/**
 * An immutable class that represents the coordinates of a [Chunk]
 * regardless of whether the world is currently loaded.
 */
data class ChunkCoordinates
@Deprecated("Use new(..) instead", replaceWith = ReplaceWith("ChunkCoordinates.new()"))
constructor(
	val worldName: String,
	val x: Int,
	val z: Int
) : Comparable<ChunkCoordinates> {

	// JSON

	fun toJSON() = JSONObject().apply {
		put(worldNameDatabaseKey, worldName)
		put(xDatabaseKey, x)
		put(zDatabaseKey, z)
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

	val isLoaded
		get() = world?.isChunkLoaded(x, z) ?: false

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

	fun getRelative(d: Pair<Int, Int>) = getRelative(d.first, d.second)

	fun getRelativeWithinChessboardRadius(radius: Int) =
		coordinatesWithinChessboardRadius2D(radius).map(::getRelative)

	fun getRelativeWithinChessboardRadius(radius: Double) =
		coordinatesWithinChessboardRadius2D( radius).map(::getRelative)

	fun getRelativeWithinRadius(radius: Int) =
		coordinatesWithinRadius2D(radius).map(::getRelative)

	fun getRelativeWithinRadius(radius: Double) =
		coordinatesWithinRadius2D(radius).map(::getRelative)

	// Get the region

	val regionX
		get() = x shr 5
	val regionZ
		get() = z shr 5

	val regionCoordinates
		get() = RegionCoordinates.get(worldName, regionX, regionZ)

	// Get blocks

	val minBlockX
		get() = x * 16
	val minBlockZ
		get() = z * 16
	val minMiddleBlockX
		get() = minBlockX + 7
	val minMiddleBlockZ
		get() = minBlockZ + 7

	/**
	 * This is the x-coordinate at the center of the chunk
	 */
	val maxMiddleBlockX
		get() = minBlockX + 8

	/**
	 * This is the z-coordinate at the center of the chunk
	 */
	val maxMiddleBlockZ
		get() = minBlockZ + 8
	val maxBlockX
		get() = minBlockX + 15
	val maxBlockZ
		get() = minBlockZ + 15

	fun getSquaredPlanarDistanceToCenterOfChunk(blockX: Double, blockZ: Double): Double {
		val dx = maxMiddleBlockX.toDouble() - blockX
		val dz = maxMiddleBlockZ.toDouble() - blockZ
		return dx * dx + dz * dz
	}

	fun getPlanarDistanceToCenterOfChunk(blockX: Double, blockZ: Double) =
		sqrt(getSquaredPlanarDistanceToCenterOfChunk(blockX, blockZ))

	fun getSquaredPlanarDistanceToCenterOfChunk(vector: Vector) =
		getSquaredPlanarDistanceToCenterOfChunk(vector.x, vector.z)

	fun getPlanarDistanceToCenterOfChunk(vector: Vector) =
		sqrt(getSquaredPlanarDistanceToCenterOfChunk(vector))

	// To readable string

	override fun toString() = "($worldName: $x, $z)"

	// Comparable

	override fun compareTo(other: ChunkCoordinates) =
		worldName.compareTo(other.worldName).takeIf { it != 0 }
			?: x.compareTo(other.x).takeIf { it != 0 }
			?: z.compareTo(other.z)

	// Companion

	companion object {

		// World size bounds (not tight)

		const val chunkCoordinateUpperBound = (BlockCoordinates.blockCoordinateUpperBound + 15) / 16
		const val chunkCoordinateLowerBound = -chunkCoordinateUpperBound
		const val chunkCoordinateRangeSize = chunkCoordinateUpperBound - chunkCoordinateLowerBound + 1

		// For a world range size of 500000, the chance of a collision with 50 worlds is ~0.2%, and with 15 worlds is ~0.02%
		val longKeyWithWorldWorldRangeSize =
			min(500000, Long.MAX_VALUE / chunkCoordinateRangeSize / chunkCoordinateRangeSize)

		fun getLongKeyWithWorldWorldPart(world: World) =
			getLongKeyWithWorldWorldPart(world.name)

		fun getLongKeyWithWorldWorldPart(worldName: String) =
			Math.floorMod(worldName.hashCode().toLong(), longKeyWithWorldWorldRangeSize)

		// Construction

		fun get(worldName: String, x: Int, z: Int) =
			@Suppress("DEPRECATION")
			ChunkCoordinates(worldName, x, z)

		fun World.getChunkCoordinates(x: Int, z: Int) =
			get(name, x, z)

		val Chunk.coordinates
			get() = get(world.name, x, z)

		val Block.chunkCoordinates
			get() = chunk.coordinates

		fun Location.getChunkCoordinates() =
			getBlockCoordinates().chunkCoordinates

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