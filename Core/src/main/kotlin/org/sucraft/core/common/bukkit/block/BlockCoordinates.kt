package org.sucraft.core.common.bukkit.block

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.util.Vector
import org.json.JSONObject
import org.sucraft.core.common.bukkit.chunk.ChunkCoordinates
import kotlin.math.sqrt

@Suppress("MemberVisibilityCanBePrivate")
data class BlockCoordinates(val worldName: String, val x: Int, val y: Int, val z: Int) : Comparable<BlockCoordinates> {

	// To JSON

	fun toJSON() = JSONObject().also {
		it.put(worldNameDatabaseKey, worldName)
		it.put(xDatabaseKey, x)
		it.put(yDatabaseKey, y)
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

	// Get the block

	@Suppress("DeprecatedCallableAddReplaceWith")
	@Deprecated("This loads the chunk, which is usually not desired")
	val blockLoadChunkIfNotLoaded get() = world?.getBlockAt(x, y, z)

	@Suppress("DEPRECATION")
	val blockIfChunkIsLoaded get() = if (isChunkLoaded) blockLoadChunkIfNotLoaded else null

	val location get() = world?.let { Location(it, x.toDouble(), y.toDouble(), z.toDouble()) }

	val vector get() = Vector(x.toDouble(), y.toDouble(), z.toDouble())

	// Get other blocks

	fun getRelative(face: BlockFace) = getRelative(face.modX, face.modY, face.modZ)

	fun getRelative(dx: Int, dy: Int, dz: Int) = copy(x = x + dx, y = y + dy, z = z + dz)

	// Get the chunk

	val chunkX get() = x shr 4
	val chunkZ get() = z shr 4

	val chunkCoordinates get() = ChunkCoordinates.get(worldName, chunkX, chunkZ)

	val isChunkLoaded get() = chunkCoordinates.isLoaded

	// Distance

	fun distanceSquared(other: BlockCoordinates): Double {
		require(other.worldName == worldName) { "Tried to get block distance between blocks in different worlds" }
		val dx = (x - other.x).toDouble()
		val dy = (y - other.y).toDouble()
		val dz = (z - other.z).toDouble()
		return dx * dx * +dy * dy + dz * dz
	}

	fun distance(other: BlockCoordinates) = sqrt(distanceSquared(other))

	// To readable string

	override fun toString() = "($worldName: $x, $y, $z)"

	// Comparable

	override fun compareTo(other: BlockCoordinates): Int {
		val worldCompare = worldName.compareTo(other.worldName)
		if (worldCompare != 0) return worldCompare
		val xCompare = x.compareTo(other.x)
		if (xCompare != 0) return xCompare
		val yCompare = y.compareTo(other.y)
		if (yCompare != 0) return yCompare
		return z.compareTo(other.z)
	}

	// Companion

	companion object {

		// World size bounds (not tight)

		const val blockCoordinateUpperBound = 31000000
		const val blockCoordinateLowerBound = -blockCoordinateUpperBound
		const val blockCoordinateRangeSize = blockCoordinateUpperBound - blockCoordinateLowerBound + 1

		// Construction

		fun get(worldName: String, x: Int, y: Int, z: Int) = BlockCoordinates(worldName, x, y, z)

		fun get(world: World, x: Int, y: Int, z: Int) = get(world.name, x, y, z)

		fun get(block: Block) = BlockCoordinates(block.world.name, block.x, block.y, block.z)

		fun get(location: Location) = get(location.world.name, location.blockX, location.blockY, location.blockZ)

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