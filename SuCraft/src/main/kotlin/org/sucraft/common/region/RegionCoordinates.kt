/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */
package org.sucraft.common.region

import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.block.Block
import org.json.JSONObject
import org.sucraft.common.block.BlockCoordinates.Companion.getBlockCoordinates
import org.sucraft.common.chunk.ChunkCoordinates.Companion.coordinates
import org.sucraft.common.math.coordinatesWithinChessboardRadius2D
import org.sucraft.common.math.coordinatesWithinRadius2D

/**
 * An immutable class that represents the coordinates of a chunk region
 * regardless of whether the world is currently loaded.
 */
data class RegionCoordinates
@Deprecated("Use new(..) instead", replaceWith = ReplaceWith("RegionCoordinates.new()"))
constructor(
	val worldName: String,
	val x: Int,
	val z: Int
) : Comparable<RegionCoordinates> {

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

	// Get other region coordinates

	fun getRelative(dx: Int, dz: Int) = copy(x = x + dx, z = z + dz)

	fun getRelative(d: Pair<Int, Int>) = getRelative(d.first, d.second)

	fun getRelativeWithinChessboardRadius(radius: Int) =
		coordinatesWithinChessboardRadius2D(radius).map(::getRelative)

	fun getRelativeWithinChessboardRadius(radius: Double) =
		coordinatesWithinChessboardRadius2D(radius).map(::getRelative)

	fun getRelativeWithinRadius(radius: Int) =
		coordinatesWithinRadius2D(radius).map(::getRelative)

	fun getRelativeWithinRadius(radius: Double) =
		coordinatesWithinRadius2D(radius).map(::getRelative)

	// Get chunks

	val minChunkX
		get() = x * 32
	val minChunkZ
		get() = z * 32
	val maxChunkX
		get() = minBlockX + 31
	val maxChunkZ
		get() = minBlockZ + 31

	// Get blocks

	val minBlockX
		get() = minChunkX * 16
	val minBlockZ
		get() = minChunkZ * 16
	val maxBlockX
		get() = maxChunkX + 15
	val maxBlockZ
		get() = maxChunkZ + 15

	// To readable string

	override fun toString() = "Region($worldName: $x, $z)"

	// Comparable

	override fun compareTo(other: RegionCoordinates) =
		worldName.compareTo(other.worldName).takeIf { it != 0 }
			?: x.compareTo(other.x).takeIf { it != 0 }
			?: z.compareTo(other.z)

	// Companion

	companion object {

		// Construction

		fun get(worldName: String, x: Int, z: Int) =
			@Suppress("DEPRECATION")
			RegionCoordinates(worldName, x, z)

		fun World.getRegionCoordinates(x: Int, z: Int) =
			get(name, x, z)

		val Chunk.regionCoordinates
			get() = coordinates.regionCoordinates

		val Block.regionCoordinates
			get() = chunk.coordinates

		fun Location.getRegionCoordinates() =
			getBlockCoordinates().regionCoordinates

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