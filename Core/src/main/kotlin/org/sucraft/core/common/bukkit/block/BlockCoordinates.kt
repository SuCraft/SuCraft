package org.sucraft.core.common.bukkit.block

import org.bukkit.World
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.json.JSONObject
import java.lang.Exception
import kotlin.math.sqrt


@Suppress("MemberVisibilityCanBePrivate")
data class BlockCoordinates(val worldName: String, val x: Int, val y: Int, val z: Int) : Comparable<BlockCoordinates> {

	fun toJSON() = JSONObject().also {
		it.put(worldNameDatabaseKey, worldName)
		it.put(xDatabaseKey, x)
		it.put(yDatabaseKey, y)
		it.put(zDatabaseKey, z)
	}

	val world: World?
		get() =
			try {
				Bukkit.getWorld(worldName)
			} catch (_: Exception) {
				null
			}
	/**
	 * Deprecated because this loads the chunk, when possibly unintended
	 */
	@Deprecated("This loads the chunk, when possibly unintended")
	val block get() = world?.getBlockAt(x, y, z)
	@Suppress("DEPRECATION")
	val blockIfChunkIsLoaded get() = if (isChunkLoaded) block else null
	val isChunkLoaded get() = world?.isChunkLoaded(chunkX, chunkZ) ?: false
	// / 16;
	val chunkX get() = x shr 4 // / 16;
	// / 16;
	val chunkZ get() = z shr 4 // / 16;
	val location get() = world?.let { Location(it, x.toDouble(), y.toDouble(), z.toDouble()) }

	override fun toString() = "($worldName: $x, $y, $z)"

	override fun compareTo(other: BlockCoordinates): Int {
		val worldCompare = worldName.compareTo(other.worldName)
		if (worldCompare != 0) return worldCompare
		val xCompare = x.compareTo(other.x)
		if (xCompare != 0) return xCompare
		val yCompare = y.compareTo(other.y)
		if (yCompare != 0) return yCompare
		return z.compareTo(other.z)
	}

	fun getRelative(face: BlockFace) = getRelative(face.modX, face.modY, face.modZ)

	fun getRelative(dx: Int, dy: Int, dz: Int) = getByCoordinates(worldName, x + dx, y + dy, z + dz)

	fun distanceSquared(other: BlockCoordinates): Double {
		require(other.worldName == worldName) { "Tried to get block distance between blocks in different worlds" }
		val dx = (x - other.x).toDouble()
		val dy = (y - other.y).toDouble()
		val dz = (z - other.z).toDouble()
		return dx * dx * +dy * dy + dz * dz
	}

	fun distance(other: BlockCoordinates) = sqrt(distanceSquared(other))

	companion object {

		private const val worldNameDatabaseKey = "world_name"
		private const val xDatabaseKey = "x"
		private const val yDatabaseKey = "y"
		private const val zDatabaseKey = "z"

		fun fromJSON(json: JSONObject) = getByCoordinates(
			json.getString(worldNameDatabaseKey),
			json.getInt(xDatabaseKey),
			json.getInt(yDatabaseKey),
			json.getInt(zDatabaseKey)
		)

		fun getByBlock(block: Block) = BlockCoordinates(block.world.name, block.x, block.y, block.z)

		fun getByNullableBlock(block: Block?) = block?.let(::getByBlock)

		fun getByCoordinates(worldName: String, x: Int, y: Int, z: Int) = BlockCoordinates(worldName, x, y, z)

		fun getByCoordinates(world: World, x: Int, y: Int, z: Int) = getByCoordinates(world.name, x, y, z)

	}

}