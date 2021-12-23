/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */
package org.sucraft.core.common.bukkit.chunk

import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.block.Block
import org.json.JSONObject
import org.sucraft.core.common.bukkit.block.BlockCoordinates


@Suppress("MemberVisibilityCanBePrivate")
data class RegionCoordinates(val worldName: String, val x: Int, val z: Int) : Comparable<RegionCoordinates> {

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

	// Get other region coordinates

	fun getRelative(dx: Int, dz: Int) = copy(x = x + dx, z = z + dz)

	// Get chunks

	val minChunkX get() = x * 32
	val minChunkZ get() = z * 32
	val maxChunkX get() = minBlockX + 31
	val maxChunkZ get() = minBlockZ + 31

	// Get blocks

	val minBlockX get() = minChunkX * 16
	val minBlockZ get() = minChunkZ * 16
	val maxBlockX get() = maxChunkX + 15
	val maxBlockZ get() = maxChunkZ + 15

	// To readable string

	override fun toString() = "Region($worldName: $x, $z)"

	// Comparable

	override fun compareTo(other: RegionCoordinates): Int {
		val worldCompare = worldName.compareTo(other.worldName)
		if (worldCompare != 0) return worldCompare
		val xCompare = x.compareTo(other.x)
		if (xCompare != 0) return xCompare
		return z.compareTo(other.z)
	}

	// Companion

	companion object {

		// Construction

		fun get(worldName: String, x: Int, z: Int) = RegionCoordinates(worldName, x, z)

		fun get(world: World, x: Int, z: Int) = get(world.name, x, z)

		fun get(chunk: Chunk) = ChunkCoordinates.get(chunk).regionCoordinates

		fun get(block: Block) = get(block.chunk)

		fun get(location: Location) = BlockCoordinates.get(location).chunkCoordinates.regionCoordinates

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