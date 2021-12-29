/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.bukkit.io

import org.bukkit.Bukkit
import org.bukkit.World
import org.sucraft.core.common.bukkit.chunk.RegionCoordinates
import java.io.File
import java.nio.file.Path
import java.util.*


object WorldFolders {

	val mainWorld get(): World = Bukkit.getWorlds()[0]

	val regionFolderName = """region"""
	val entitiesFolderName = """entities"""
	val poiFolderName = """poi"""
	val dataFolderName = """data"""
	val dataPacksFolderName = """datapacks"""
	val playerDataFolderName = """playerdata"""
	val unknownPlayersFolderName = """unknownplayers"""
	val advancementsFolderName = """advancements"""
	val statsFolderName = """stats"""

	fun getRegionMCAFilename(regionX: Int, regionZ: Int) =
		"""r.$regionX.$regionZ.mca"""

	fun getRegionMCRFilename(regionX: Int, regionZ: Int) =
		"""r.$regionX.$regionZ.mcr"""

	fun getMapFilename(mapID: Int) =
		"""map_$mapID.dat"""

	fun getMCAFoldersParentFolder(world: World): File {
		val worldFolder = world.worldFolder
		return when (world.environment) {
			World.Environment.NETHER -> Path.of(worldFolder.path, """DIM-1""").toFile()
			World.Environment.THE_END -> Path.of(worldFolder.path, """DIM1""").toFile()
			else -> worldFolder
		}
	}

	fun getRegionFolder(world: World): File =
		Path.of(getMCAFoldersParentFolder(world).path, regionFolderName).toFile()

	fun getEntitiesFolder(world: World): File =
		Path.of(getMCAFoldersParentFolder(world).path, entitiesFolderName).toFile()

	fun getPOIFolder(world: World): File =
		Path.of(getMCAFoldersParentFolder(world).path, poiFolderName).toFile()

	fun getDataFolder(world: World): File =
		Path.of(getMCAFoldersParentFolder(world).path, dataFolderName).toFile()

	fun getDataPacksFolder(world: World): File =
		Path.of(getMCAFoldersParentFolder(world).path, dataPacksFolderName).toFile()

	fun getDataPacksFolder(): File =
		getDataPacksFolder(mainWorld)

	fun getPlayerDataFolder(world: World): File =
		Path.of(getMCAFoldersParentFolder(world).path, playerDataFolderName).toFile()

	fun getPlayerDataFolder(): File =
		getPlayerDataFolder(mainWorld)

	fun getUnknownPlayersFolder(world: World): File =
		Path.of(getMCAFoldersParentFolder(world).path, unknownPlayersFolderName).toFile()

	fun getUnknownPlayersFolder(): File =
		getUnknownPlayersFolder(mainWorld)

	fun getAdvancementsFolder(world: World): File =
		Path.of(getMCAFoldersParentFolder(world).path, advancementsFolderName).toFile()

	fun getAdvancementsFolder(): File =
		getAdvancementsFolder(mainWorld)

	fun getStatsFolder(world: World): File =
		Path.of(getMCAFoldersParentFolder(world).path, statsFolderName).toFile()

	fun getStatsFolder(): File =
		getStatsFolder(mainWorld)

	fun getRegionFile(world: World, regionX: Int, regionZ: Int): File =
		Path.of(getRegionFolder(world).path, getRegionMCAFilename(regionX, regionZ)).toFile()

	fun getRegionFile(regionCoordinates: RegionCoordinates): File? =
		regionCoordinates.world?.let { getRegionFile(it, regionCoordinates.x, regionCoordinates.z) }

	fun getEntitiesFile(world: World, regionX: Int, regionZ: Int): File =
		Path.of(getEntitiesFolder(world).path, getRegionMCAFilename(regionX, regionZ)).toFile()

	fun getEntitiesFile(regionCoordinates: RegionCoordinates): File? =
		regionCoordinates.world?.let { getEntitiesFile(it, regionCoordinates.x, regionCoordinates.z) }

	fun getPOIFile(world: World, regionX: Int, regionZ: Int): File =
		Path.of(getPOIFolder(world).path, getRegionMCAFilename(regionX, regionZ)).toFile()

	fun getPOIFile(regionCoordinates: RegionCoordinates): File? =
		regionCoordinates.world?.let { getPOIFile(it, regionCoordinates.x, regionCoordinates.z) }

	fun getLevelFile(world: World): File =
		Path.of(world.worldFolder.path, """level.dat""").toFile()

	fun getRaidsFile(world: World): File =
		Path.of(
			getDataFolder(world).path, when (world.environment) {
			World.Environment.NETHER -> """raids_nether.dat"""
			World.Environment.THE_END -> """raids_end.dat"""
			else -> """raids.dat"""
		}).toFile()

	fun getFortressFile(world: World): File =
		Path.of(getDataFolder(world).path, """Fortress.dat""").toFile()

	fun getMineshaftFile(world: World): File =
		Path.of(getDataFolder(world).path, """Mineshaft.dat""").toFile()

	fun getMonumentFile(world: World): File =
		Path.of(getDataFolder(world).path, """Monument.dat""").toFile()

	fun getStrongholdFile(world: World): File =
		Path.of(getDataFolder(world).path, """Stronghold.dat""").toFile()

	fun getTempleFile(world: World): File =
		Path.of(getDataFolder(world).path, """Temple.dat""").toFile()

	fun getVillageFile(world: World): File =
		Path.of(getDataFolder(world).path, """Village.dat""").toFile()

	fun getOverworldVillagesFile(world: World): File =
		Path.of(getDataFolder(world).path, """villages.dat""").toFile()

	fun getNetherVillagesFile(world: World): File =
		Path.of(getDataFolder(world).path, """villages_nether.dat""").toFile()

	fun getEndVillagesFile(world: World): File =
		Path.of(getDataFolder(world).path, """villages_end.dat""").toFile()

	fun getIDCountsFile(world: World): File =
		Path.of(getDataFolder(world).path, """idcounts.dat""").toFile()

	fun getIDCountsFile(): File =
		getIDCountsFile(mainWorld)

	fun getMapFile(world: World, mapID: Int): File =
		Path.of(getDataFolder(world).path, getMapFilename(mapID)).toFile()

	fun getMapFile(mapID: Int): File =
		getMapFile(mainWorld, mapID)

	fun getPlayerDataFile(world: World, uuid: UUID): File =
		Path.of(getPlayerDataFolder(world).path, """$uuid.dat""").toFile()

	fun getPlayerDataFile(uuid: UUID): File =
		getPlayerDataFile(mainWorld, uuid)

	fun getAdvancementsFile(world: World, uuid: UUID): File =
		Path.of(getAdvancementsFolder(world).path, """$uuid.json""").toFile()

	fun getAdvancementsFile(uuid: UUID): File =
		getAdvancementsFile(mainWorld, uuid)

	fun getStatsFile(world: World, uuid: UUID): File =
		Path.of(getStatsFolder(world).path, """$uuid.json""").toFile()

	fun getStatsFile(uuid: UUID): File =
		getStatsFile(mainWorld, uuid)

	fun getOldNameBasedPlayerDataFile(world: World, caseSensitivePlayerName: String): File =
		Path.of(getUnknownPlayersFolder(world).path, """$caseSensitivePlayerName.dat""").toFile()

	fun getOldNameBasedPlayerDataFile(caseSensitivePlayerName: String): File =
		getOldNameBasedStatsFile(mainWorld, caseSensitivePlayerName)

	fun getOldNameBasedStatsFile(world: World, caseSensitivePlayerName: String): File =
		Path.of(getStatsFolder(world).path, """$caseSensitivePlayerName.json""").toFile()

	fun getOldNameBasedStatsFile(caseSensitivePlayerName: String): File =
		getOldNameBasedStatsFile(mainWorld, caseSensitivePlayerName)

}