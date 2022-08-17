/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.io

import org.bukkit.World
import org.bukkit.World.Environment.NETHER
import org.bukkit.World.Environment.THE_END
import org.bukkit.entity.Player
import org.sucraft.common.region.RegionCoordinates
import org.sucraft.common.world.mainWorld
import java.io.File
import java.util.*

// Provides paths to and names of various folders and files belonging to worlds.

fun getRegionMCAFilename(regionX: Int, regionZ: Int) = """r.$regionX.$regionZ.mca"""

fun getRegionMCRFilename(regionX: Int, regionZ: Int) = """r.$regionX.$regionZ.mcr"""

fun getMapFilename(mapID: Int) = """map_$mapID.dat"""

/**
 * The parent folder of the MCA folders and related folders
 * (region, entities, poi, data, datapacks, playerdata, unknownplayers, advancements, stats)
 * for this world, for example `/world` or `/world_nether/DIM-1`.
 */
val World.mcaFoldersParentFolder
	get() = when (environment) {
		NETHER -> """DIM-1""" inside worldFolder
		THE_END -> """DIM1""" inside worldFolder
		else -> worldFolder
	}

/**
 * The region folder of this world, for example `/world/region`.
 */
val World.regionFolder get() = """region""" inside mcaFoldersParentFolder

/**
 * The entities folder of this world, for example `/world/entities`.
 */
val World.entitiesFolder get() = """entities""" inside mcaFoldersParentFolder

/**
 * The POI folder of this world, for example `/world/poi`.
 */
val World.poiFolder get() = """poi""" inside mcaFoldersParentFolder

/**
 * The data folder of this world, for example `/world/data`.
 */
val World.dataFolder get() = """data""" inside mcaFoldersParentFolder

/**
 * The data packs folder of the server, which is located in the main world folder,
 * for example `/world/datapacks`.
 */
val dataPacksFolder get() = """datapacks""" inside mainWorld.mcaFoldersParentFolder

/**
 * The player data folder of the server, which is located in the main world folder,
 * for example `/world/playerdata`.
 */
val playerDataFolder get() = """playerdata""" inside mainWorld.mcaFoldersParentFolder

/**
 * The unknown players folder of the server, which is located in the main world folder,
 * for example `/world/unknownplayers`.
 */
val unknownPlayersFolder get() = """unknownplayers""" inside mainWorld.mcaFoldersParentFolder

/**
 * The player advancements folder of the server, which is located in the main world folder,
 * for example `/world/advancements`.
 */
val advancementsFolder get() = """advancements""" inside mainWorld.mcaFoldersParentFolder

/**
 * The player stats folder of the server, which is located in the main world folder,
 * for example `/world/stats`.
 */
val statsFolder get() = """stats""" inside mainWorld.mcaFoldersParentFolder

/**
 * The region file for this region, for example `/world/region/r.2.-3.mca',
 * or null if this region's world is not loaded.
 */
val RegionCoordinates.regionFile get() = world?.let { getRegionMCAFilename(x, z) inside it.regionFolder }

/**
 * The entities file for this region, for example `/world/entities/r.2.-3.mca',
 * or null if this region's world is not loaded.
 */
val RegionCoordinates.entitiesFile get() = world?.let { getRegionMCAFilename(x, z) inside it.entitiesFolder }

/**
 * The POI file for this region, for example `/world/poi/r.2.-3.mca',
 * or null if this region's world is not loaded.
 */
val RegionCoordinates.poiFile get() = world?.let { getRegionMCAFilename(x, z) inside it.poiFolder }

/**
 * The level file for this world, for example `/world/level.dat`.
 */
val World.levelFile get() = """level.dat""" inside worldFolder

/**
 * The raids file for this world, for example `/world/data/raids.data`
 * or `/world_nether/DIM-1/data/raids_nether.dat`.
 */
val World.raidsFile
	get() = when (environment) {
		NETHER -> """raids_nether.dat"""
		THE_END -> """raids_end.dat"""
		else -> """raids.dat"""
	} inside dataFolder

/**
 * The fortress file for this world, for example `/world/data/Fortress.dat`.
 */
val World.fortressFile get() = """Fortress.dat""" inside dataFolder

/**
 * The mineshaft file for this world, for example `/world/data/Mineshaft.dat`.
 */
val World.mineshaftFile get() = """Mineshaft.dat""" inside dataFolder

/**
 * The ocean monument file for this world, for example `/world/data/Monument.dat`.
 */
val World.monumentFile get() = """Monument.dat""" inside dataFolder

/**
 * The stronghold file for this world, for example `/world/data/Stronghold.dat`.
 */
val World.strongholdFile get() = """Stronghold.dat""" inside dataFolder

/**
 * The desert and jungle temple file for this world, for example `/world/data/Temple.dat`.
 */
val World.templeFile get() = """Temple.dat""" inside dataFolder

/**
 * The village file for this world, for example `/world/data/Village.dat`.
 */
val World.villageFile get() = """Village.dat""" inside dataFolder

/**
 * The overworld villages file for this world, for example `/world/data/villages.dat`.
 */
val World.overworldVillagesFile get() = """villages.dat""" inside dataFolder

/**
 * The nether villages file for this world, for example `/world_nether/DIM-1/data/villages_nether.dat`.
 */
val World.netherVillagesFile get() = """villages_nether.dat""" inside dataFolder

/**
 * The end villages file for this world, for example `/world/DIM1/data/villages_end.dat`.
 */
val World.endVillagesFile get() = """villages_end.dat""" inside dataFolder

/**
 * The id counts file of the server, which is located in the main world data folder,
 * for example `/world/data/idcounts.dat`.
 */
val idCountsFolder get() = """idcounts.dat""" inside mainWorld.dataFolder

/**
 * @return The map file for the given map id, which is located in the main world data folder,
 * for example `/world/data/map_1037.dat`.
 */
fun getMapFile(mapID: Int) = getMapFilename(mapID) inside mainWorld.dataFolder

/**
 * The data file for the player with this UUID
 * (if no such player exists, this [File] instance is non-null but won't [exist][File.exists]),
 * which is located in the main world player data folder,
 * for example `/world/playerdata/0c17d615-c22f-3363-b9a4-e7b1e2561a74.dat`.
 */
val UUID.playerDataFile get() = """$this.dat""" inside playerDataFolder

/**
 * The data file for this player, which is located in the main world player data folder,
 * for example `/world/playerdata/0c17d615-c22f-3363-b9a4-e7b1e2561a74.dat`.
 */
val Player.dataFile get() = uniqueId.playerDataFile

/**
 * The advancements file for the player with this UUID
 * (if no such player exists, this [File] instance is non-null but won't [exist][File.exists]),
 * which is located in the main world player advancements folder,
 * for example `/world/advancements/0c17d615-c22f-3363-b9a4-e7b1e2561a74.dat`.
 */
val UUID.playerAdvancementsFile get() = """$this.dat""" inside advancementsFolder

/**
 * The advancements file for this player, which is located in the main world player advancements folder,
 * for example `/world/advancements/0c17d615-c22f-3363-b9a4-e7b1e2561a74.dat`.
 */
val Player.advancementsFile get() = uniqueId.playerAdvancementsFile

/**
 * The stats file for the player with this UUID
 * (if no such player exists, this [File] instance is non-null but won't [exist][File.exists]),
 * which is located in the main world player stats folder,
 * for example `/world/stats/0c17d615-c22f-3363-b9a4-e7b1e2561a74.dat`.
 */
val UUID.playerStatsFile get() = """$this.dat""" inside statsFolder

/**
 * The stats file for this player, which is located in the main world player stats folder,
 * for example `/world/stats/0c17d615-c22f-3363-b9a4-e7b1e2561a74.dat`.
 */
val Player.statsFile get() = uniqueId.playerStatsFile

/**
 * @return The old name-based player data file for the given case-sensitive player name,
 * which is located in the main world unknown players folder,
 * for example `/world/unknownplayers/Seph73.dat`.
 */
fun getOldNameBasedPlayerDataFile(caseSensitivePlayerName: String) =
	"""$caseSensitivePlayerName.dat""" inside unknownPlayersFolder

/**
 * @return The old name-based player stats file for the given case-sensitive player name,
 * which is located in the main world stats folder,
 * for example `/world/stats/Seph73.json`.
 */
fun getOldNameBasedPlayerStatsFile(caseSensitivePlayerName: String) =
	"""$caseSensitivePlayerName.json""" inside statsFolder