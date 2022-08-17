/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.shorttermbackups

import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.bukkit.World
import org.bukkit.entity.Player
import org.sucraft.common.function.runEach
import org.sucraft.common.io.*
import org.sucraft.common.module.SuCraftComponent
import org.sucraft.common.player.runEachPlayer
import org.sucraft.common.region.RegionCoordinates
import org.sucraft.common.region.RegionCoordinates.Companion.regionCoordinates
import org.sucraft.common.time.TimeInMinutes
import org.sucraft.common.time.TimeLength
import org.sucraft.common.world.runEachWorld
import org.sucraft.modules.shorttermbackups.BackupTaskExecutor.startFileBackupTask
import java.io.File
import java.nio.file.Path
import java.util.*

/**
 * Provides extensions for backing up regions' files.
 */
object RegionBackupExtensions : SuCraftComponent<ShortTermBackups>() {

	// Settings

	private val minimumChunkInhabitedTimeToBackup = TimeInMinutes(10) // 1 player for 10 minutes

	// Provided functionality

	/**
	 * Schedules a backup of this chunk's region's files,
	 * if this chunk has been inhabited long enough.
	 */
	fun Chunk.scheduleRegionFilesBackupIfInhabitedLongEnough(delay: TimeLength? = null) {
		if (inhabitedTime >= minimumChunkInhabitedTimeToBackup.ticksExact) {
			regionCoordinates.scheduleFilesBackup(delay)
		}
	}

	/**
	 * Schedules a backup of this region's files.
	 */
	fun RegionCoordinates.scheduleFilesBackup(delay: TimeLength? = null) {
		startFileBackupTask(regionFile!!, delay)
		startFileBackupTask(entitiesFile!!, delay)
		startFileBackupTask(poiFile!!, delay)
	}

	/**
	 * Schedules a backup of every loaded chunk's region's files.
	 */
	fun scheduleBackupForAllLoadedChunkRegionFiles(delay: TimeLength? = null) {
		info("Scheduling backup for all loaded chunk regions...")
		Bukkit.getWorlds().asSequence().flatMap { world ->
			world.loadedChunks.asSequence()
				.filter { it.inhabitedTime >= minimumChunkInhabitedTimeToBackup.ticksExact }
				.map { it.regionCoordinates }
				.distinct()
		}.runEach { scheduleFilesBackup(delay) }
	}

}

/**
 * Provides extensions for backing up players' server data files.
 */
object PlayerServerDataBackupExtensions : SuCraftComponent<ShortTermBackups>() {

	// Provided functionality

	/**
	 * Schedules a backup of this player's server data files.
	 */
	fun Player.scheduleServerDataFilesBackup(delay: TimeLength? = null) =
		uniqueId.schedulePlayerServerDataFilesBackup(delay)

	/**
	 * Schedules a backup of the server ata files of the player with this UUID.
	 */
	fun UUID.schedulePlayerServerDataFilesBackup(delay: TimeLength? = null) {
		startFileBackupTask(playerDataFile, delay)
		startFileBackupTask(playerAdvancementsFile, delay)
		startFileBackupTask(playerStatsFile, delay)
	}

	/**
	 * Schedules a backup of every online player's server data files.
	 */
	fun scheduleBackupForAllOnlinePlayerServerDataFiles(delay: TimeLength? = null) {
		info("Scheduling backup for all online players...")
		runEachPlayer { scheduleServerDataFilesBackup(delay) }
	}

}

/**
 * Provides extensions for backing up Multiverse files.
 */
object MultiverseBackupExtensions : SuCraftComponent<ShortTermBackups>() {

	// Settings

	private const val corePluginName = """Multiverse-Core"""
	private const val portalsPluginName = """Multiverse-Portals"""
	private const val netherPortalsPluginName = """Multiverse-NetherPortals"""
	private const val inventoriesPluginName = """Multiverse-Inventories"""

	private val coreGeneralDataFilenamesToBackup = arrayOf(
		"""config.yml""",
		"""worlds.yml"""
	)
	private val portalsGeneralDataFilenamesToBackup = arrayOf(
		"""config.yml""",
		"""portals.yml"""
	)
	private val netherPortalsGeneralDataFilenamesToBackup = arrayOf(
		"""config.yml"""
	)
	private val inventoriesGeneralDataFilenamesToBackup = arrayOf(
		"""config.yml""",
		"""groups.yml"""
	)

	private val inventoriesGroupsFolder get() = """groups""" inside getPluginFolder(inventoriesPluginName)
	private val inventoriesPlayersFolder get() = """players""" inside getPluginFolder(inventoriesPluginName)
	private val inventoriesWorldsFolder get() = """worlds""" inside getPluginFolder(inventoriesPluginName)

	// Provided functionality

	/**
	 * Schedules a backup of this player's Multiverse files.
	 */
	fun Player.scheduleMultiverseFilesBackup(delay: TimeLength? = null) {
		sequenceOf(inventoriesGroupsFolder, inventoriesWorldsFolder)
			.filter(File::isDirectory)
			.flatMap { it.listFiles().asSequence() }
			.filterNotNull()
			.filter(File::isDirectory)
			.forEach {
				sequenceOf("yml", "json").forEach { extension ->
					val file = Path.of(it.path, """$name.$extension""").toFile()
					if (file.exists())
						startFileBackupTask(file, delay)
				}
			}
		inventoriesPlayersFolder.takeIf(File::isDirectory)?.let {
			sequenceOf(name, "$uniqueId").forEach { playerIdentifierString ->
				sequenceOf("yml", "json").forEach { extension ->
					val file = Path.of(it.path, """$playerIdentifierString.$extension""").toFile()
					if (file.exists())
						startFileBackupTask(file, delay)
				}
			}
		}
	}

	/**
	 * Schedules a backup of every online player's Multiverse files.
	 */
	fun scheduleBackupForAllOnlinePlayerMultiverseFiles(delay: TimeLength? = null) {
		info("Scheduling backup for all online players...")
		runEachPlayer { scheduleMultiverseFilesBackup(delay) }
	}

	/**
	 * Schedules a backup of all general Multiverse data files.
	 */
	fun scheduleBackupForAllGeneralMultiverseData(delay: TimeLength? = null) {
		info("Scheduling backup for all Multiverse general data...")
		sequenceOf(
			corePluginName to coreGeneralDataFilenamesToBackup,
			portalsPluginName to portalsGeneralDataFilenamesToBackup,
			netherPortalsPluginName to netherPortalsGeneralDataFilenamesToBackup,
			inventoriesPluginName to inventoriesGeneralDataFilenamesToBackup
		).forEach { (pluginName, filenamesToBackup) ->
			if (Bukkit.getPluginManager().getPlugin(pluginName) == null) {
				warning(
					"Plugin $pluginName is not loaded," +
							" but any existing plugin files will still be backed up"
				)
			}
			filenamesToBackup.forEach {
				val file = Path.of(getPluginFolder(pluginName).path, it).toFile()
				if (file.exists())
					startFileBackupTask(file, delay)
			}
		}
	}

}

/**
 * Provides extensions for backing up MoreProtect files.
 */
object MoreProtectBackups : SuCraftComponent<ShortTermBackups>() {

	// Settings

	private const val pluginName = """MoreProtect"""

	// Provided functionality

	/**
	 * Schedules a backup of all MoreProtect data files.
	 */
	fun scheduleBackupForAllMoreProtectData(delay: TimeLength? = null) {
		info("Scheduling backup for all MoreProtect data...")
		if (Bukkit.getPluginManager().getPlugin(pluginName) == null) {
			warning(
				"Plugin $pluginName is not loaded," +
						" but any existing plugin files will still be backed up"
			)
		}
		getPluginFolder(pluginName).takeIf(File::isDirectory)?.listFiles()?.forEach {
			startFileBackupTask(it, delay)
		}
	}

}

object WorldBackupExtensions : SuCraftComponent<ShortTermBackups>() {

	// Provided functionality

	/**
	 * Schedules a backup of this world's server data files.
	 */
	fun World.scheduleServerDataFilesBackup(delay: TimeLength? = null) {
		startFileBackupTask(levelFile, delay)
		dataFolder.takeIf(File::isDirectory)?.listFiles()?.forEach {
			startFileBackupTask(it, delay)
		}
	}

	/**
	 * Schedules a backup of every world's server data files.
	 */
	fun scheduleBackupForAllWorldServerDataFiles(delay: TimeLength? = null) {
		info("Scheduling backup for all world data...")
		runEachWorld { scheduleServerDataFilesBackup(delay) }
	}

}