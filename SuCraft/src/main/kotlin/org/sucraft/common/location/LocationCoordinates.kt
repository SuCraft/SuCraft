/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.util.Vector
import org.json.JSONException
import org.json.JSONObject
import org.sucraft.common.block.BlockCoordinates
import org.sucraft.common.chunk.ChunkCoordinates
import org.sucraft.common.math.toIntFloored
import kotlin.math.sqrt

/**
 * An immutable class that represents floating-point world coordinates
 * (typically the coordinates of a [Location])
 * regardless of whether the world is currently loaded.
 */
data class LocationCoordinates
@Deprecated("Use new(..) instead", replaceWith = ReplaceWith("LocationCoordinates.new()"))
constructor(
	val worldName: String,
	val x: Double,
	val y: Double,
	val z: Double,
	val yaw: Double,
	val pitch: Double
) : Comparable<LocationCoordinates> {

	// To JSON

	fun toJSON() = JSONObject().apply {
		put(worldNameDatabaseKey, worldName)
		put(xDatabaseKey, x)
		put(yDatabaseKey, y)
		put(zDatabaseKey, z)
		put(yawDatabaseKey, yaw)
		put(pitchDatabaseKey, pitch)
	}

	// Get the world

	val world: World?
		get() =
			try {
				Bukkit.getWorld(worldName)
			} catch (_: Exception) {
				null
			}

	// Get the location

	@Suppress("DeprecatedCallableAddReplaceWith")
	@Deprecated("This loads the chunk, which is usually not desired")
	fun getLocationLoadChunkIfNotLoaded() =
		world?.let { Location(it, x, y, z, yaw.toFloat(), pitch.toFloat()) }

	@Suppress("DEPRECATION")
	fun getLocationIfChunkIsLoaded() =
		if (isChunkLoaded) getLocationLoadChunkIfNotLoaded() else null

	// Get other locations

	fun getRelative(face: BlockFace) = getRelative(face.modX, face.modY, face.modZ)

	fun getRelative(dx: Int, dy: Int, dz: Int, dyaw: Double = 0.0, dpitch: Double = 0.0) =
		getRelative(dx.toDouble(), dy.toDouble(), dz.toDouble(), dyaw, dpitch)

	fun getRelative(dx: Double, dy: Double, dz: Double, dyaw: Double = 0.0, dpitch: Double = 0.0) =
		copy(x = x + dx, y = y + dy, z = z + dz, yaw = yaw + dyaw, pitch = pitch + dpitch)

	// Get the region

	val regionX
		get() = chunkCoordinates.regionX
	val regionZ
		get() = chunkCoordinates.regionZ

	val regionCoordinates
		get() = chunkCoordinates.regionCoordinates

	// Get the chunk

	val chunkX
		get() = x.toIntFloored() shr 4
	val chunkZ
		get() = z.toIntFloored() shr 4

	val chunkCoordinates
		get() = ChunkCoordinates.get(worldName, chunkX, chunkZ)

	val isChunkLoaded
		get() = chunkCoordinates.isLoaded

	// Get the block

	val blockX
		get() = x.toIntFloored()
	val blockY
		get() = y.toIntFloored()
	val blockZ
		get() = z.toIntFloored()

	val blockCoordinates
		get() = BlockCoordinates.get(worldName, blockX, blockY, blockZ)

	// Get the vector

	fun getVector() =
		Vector(x, y, z)

	// Distance

	fun distanceSquared(other: LocationCoordinates): Double {
		require(other.worldName == worldName) { "Tried to get distance between locations in different worlds" }
		val dx = x - other.x
		val dy = y - other.y
		val dz = z - other.z
		return dx * dx * +dy * dy + dz * dz
	}

	fun distance(other: LocationCoordinates) =
		sqrt(distanceSquared(other))

	fun distanceSquared(other: BlockCoordinates) =
		distanceSquared(other.locationCoordinates)

	fun distance(other: BlockCoordinates) =
		distance(other.locationCoordinates)

	fun BlockCoordinates.distanceSquared(other: LocationCoordinates) =
		other.distanceSquared(this)

	fun BlockCoordinates.distance(other: LocationCoordinates) =
		other.distance(this)

	// To readable string

	override fun toString() = "($worldName: $x, $y, $z -> yaw=$yaw, pitch=$pitch)"

	// Comparable

	override fun compareTo(other: LocationCoordinates) =
		worldName.compareTo(other.worldName).takeIf { it != 0 }
			?: x.compareTo(other.x).takeIf { it != 0 }
			?: y.compareTo(other.y).takeIf { it != 0 }
			?: z.compareTo(other.z).takeIf { it != 0 }
			?: yaw.compareTo(other.yaw).takeIf { it != 0 }
			?: pitch.compareTo(other.pitch)

	// Companion

	companion object {

		// Construction

		fun get(worldName: String, x: Double, y: Double, z: Double, yaw: Double = 0.0, pitch: Double = 0.0) =
			@Suppress("DEPRECATION")
			LocationCoordinates(worldName, x, y, z, yaw, pitch)

		fun World.getLocationCoordinates(x: Double, y: Double, z: Double, yaw: Double = 0.0, pitch: Double = 0.0) =
			get(name, x, y, z, yaw, pitch)

		fun Block.getLocationCoordinates(yaw: Double = 0.0, pitch: Double = 0.0) =
			get(world.name, x.toDouble(), y.toDouble(), z.toDouble(), yaw, pitch)

		fun Block.getCenterLocationCoordinates(yaw: Double = 0.0, pitch: Double = 0.0) =
			get(world.name, x + 0.5, y + 0.5, z + 0.5, yaw, pitch)

		fun Location.getCoordinates() =
			get(world.name, x, y, z, yaw.toDouble(), pitch.toDouble())

		// From JSON

		private const val worldNameDatabaseKey = "world_name"
		private const val xDatabaseKey = "x"
		private const val yDatabaseKey = "y"
		private const val zDatabaseKey = "z"
		private const val yawDatabaseKey = "yaw"
		private const val pitchDatabaseKey = "pitch"

		fun fromJSON(json: JSONObject) = get(
			json.getString(worldNameDatabaseKey),
			json.getDouble(xDatabaseKey),
			json.getDouble(yDatabaseKey),
			json.getDouble(zDatabaseKey),
			try {
				json.getDouble(yawDatabaseKey)
			} catch (_: JSONException) {
				0.0
			},
			try {
				json.getDouble(pitchDatabaseKey)
			} catch (_: JSONException) {
				0.0
			}
		)

	}

}