/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.block

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.util.Vector
import org.json.JSONObject
import org.sucraft.common.chunk.ChunkCoordinates
import org.sucraft.common.location.LocationCoordinates
import org.sucraft.common.math.coordinatesWithinChessboardRadius3D
import org.sucraft.common.math.coordinatesWithinRadius3D
import kotlin.math.sqrt

/**
 * An immutable class that represents integral world coordinates
 * (typically the coordinates of a [Block])
 * regardless of whether the world is currently loaded.
 */
data class BlockCoordinates
@Deprecated("Use new(..) instead", replaceWith = ReplaceWith("BlockCoordinates.new()"))
constructor(
	val worldName: String,
	val x: Int,
	val y: Int,
	val z: Int
) : Comparable<BlockCoordinates> {

	// To JSON

	fun toJSON() = JSONObject().apply {
		put(worldNameDatabaseKey, worldName)
		put(xDatabaseKey, x)
		put(yDatabaseKey, y)
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

	// Get the block

	@Suppress("DeprecatedCallableAddReplaceWith")
	@Deprecated("This loads the chunk, which is usually not desired")
	val blockLoadChunkIfNotLoaded
		get() = world?.getBlockAt(x, y, z)

	@Suppress("DEPRECATION")
	val blockIfChunkIsLoaded
		get() = if (isChunkLoaded) blockLoadChunkIfNotLoaded else null

	// Get other blocks

	fun getRelative(face: BlockFace) = getRelative(face.modX, face.modY, face.modZ)

	fun getRelative(dx: Int, dy: Int, dz: Int) = copy(x = x + dx, y = y + dy, z = z + dz)

	fun getRelative(d: Triple<Int, Int, Int>) = getRelative(d.first, d.second, d.third)

	fun getRelativeWithinChessboardRadius(radius: Int) =
		coordinatesWithinChessboardRadius3D(radius).map(::getRelative)

	fun getRelativeWithinChessboardRadius(radius: Double) =
		coordinatesWithinChessboardRadius3D(radius).map(::getRelative)

	fun getRelativeWithinRadius(radius: Int) =
		coordinatesWithinRadius3D(radius).map(::getRelative)

	fun getRelativeWithinRadius(radius: Double) =
		coordinatesWithinRadius3D(radius).map(::getRelative)

	// Get the region

	val regionX
		get() = chunkCoordinates.regionX
	val regionZ
		get() = chunkCoordinates.regionZ

	val regionCoordinates
		get() = chunkCoordinates.regionCoordinates

	// Get the chunk

	val chunkX
		get() = x shr 4
	val chunkZ
		get() = z shr 4

	val chunkCoordinates
		get() = ChunkCoordinates.get(worldName, chunkX, chunkZ)

	val isChunkLoaded
		get() = chunkCoordinates.isLoaded

	// Get the location

	val locationCoordinates
		get() = LocationCoordinates.get(worldName, x.toDouble(), y.toDouble(), z.toDouble())

	val centerLocationCoordinates
		get() = LocationCoordinates.get(worldName, x + 0.5, y + 0.5, z + 0.5)

	fun getLocation() =
		world?.let { Location(it, x.toDouble(), y.toDouble(), z.toDouble()) }

	// Get the vector

	fun getVector() =
		Vector(x, y, z)

	fun getCenterVector() =
		Vector(x + 0.5, y + 0.5, z + 0.5)

	// Distance

	fun distanceSquared(other: BlockCoordinates): Double {
		require(other.worldName == worldName) { "Tried to get block distance between blocks in different worlds" }
		val dx = (x - other.x).toDouble()
		val dy = (y - other.y).toDouble()
		val dz = (z - other.z).toDouble()
		return dx * dx * +dy * dy + dz * dz
	}

	fun distance(other: BlockCoordinates) =
		sqrt(distanceSquared(other))

	// To readable string

	override fun toString() = "($worldName: $x, $y, $z)"

	// Comparable

	override fun compareTo(other: BlockCoordinates) =
		worldName.compareTo(other.worldName).takeIf { it != 0 }
			?: x.compareTo(other.x).takeIf { it != 0 }
			?: y.compareTo(other.y).takeIf { it != 0 }
			?: z.compareTo(other.z)

	// Companion

	companion object {

		// World size bounds (not tight)

		const val blockCoordinateUpperBound = 31000000
		const val blockCoordinateLowerBound = -blockCoordinateUpperBound
		const val blockCoordinateRangeSize = blockCoordinateUpperBound - blockCoordinateLowerBound + 1

		// Construction

		fun get(worldName: String, x: Int, y: Int, z: Int) =
			@Suppress("DEPRECATION")
			BlockCoordinates(worldName, x, y, z)

		fun World.getBlockCoordinates(x: Int, y: Int, z: Int) =
			get(name, x, y, z)

		val Block.coordinates
			get() = get(world.name, x, y, z)

		fun Location.getBlockCoordinates() =
			get(world.name, blockX, blockY, blockZ)

		// From JSON

		private const val worldNameDatabaseKey = "world_name"
		private const val xDatabaseKey = "x"
		private const val yDatabaseKey = "y"
		private const val zDatabaseKey = "z"

		fun fromJSON(json: JSONObject) = get(
			json.getString(worldNameDatabaseKey),
			json.getInt(xDatabaseKey),
			json.getInt(yDatabaseKey),
			json.getInt(zDatabaseKey)
		)

	}

}