/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.anticorruption.backup

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.sucraft.anticorruption.main.SuCraftAntiCorruptionPlugin
import org.sucraft.core.common.bukkit.io.PluginFolders
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import java.io.File
import java.nio.file.Path


object MultiverseBackups : SuCraftComponent<SuCraftAntiCorruptionPlugin>(SuCraftAntiCorruptionPlugin.getInstance()) {

	// Settings

	private const val corePluginName = """Multiverse-Core"""
	private const val portalsPluginName = """Multiverse-Portals"""
	private const val netherPortalsPluginName = """Multiverse-NetherPortals"""
	private const val inventoriesPluginName = """Multiverse-Inventories"""

	private val coreGeneralDataFilesnamesToBackup = arrayOf(
		"""config.yml""",
		"""worlds.yml"""
	)
	private val portalsGeneralDataFilesnamesToBackup = arrayOf(
		"""config.yml""",
		"""portals.yml"""
	)
	private val netherPortalsGeneralDataFilesnamesToBackup = arrayOf(
		"""config.yml"""
	)
	private val inventoriesGeneralDataFilesnamesToBackup = arrayOf(
		"""config.yml""",
		"""groups.yml"""
	)

	private fun getInventoriesGroupsFolder() = Path.of(PluginFolders.getPluginDataFolder(inventoriesPluginName).path, """groups""").toFile()

	private fun getInventoriesPlayersFolder() = Path.of(PluginFolders.getPluginDataFolder(inventoriesPluginName).path, """players""").toFile()

	private fun getInventoriesWorldsFolder() = Path.of(PluginFolders.getPluginDataFolder(inventoriesPluginName).path, """worlds""").toFile()

	// Implementation

	fun schedulePlayerBackup(player: Player, delayInMillis: Long? = null) {
		for (folder in arrayOf(getInventoriesGroupsFolder(), getInventoriesWorldsFolder())) {
			folder.takeIf(File::isDirectory)?.listFiles()?.asSequence()?.filter(File::isDirectory)?.forEach {
				for (extension in arrayOf("yml, json")) {
					val file = Path.of(it.path, """${player.name}.$extension""").toFile()
					if (file.exists())
						BackupTaskExecutor.scheduleBackup(FileToBackup(file.path), delayInMillis)
				}
			}
		}
		getInventoriesPlayersFolder().takeIf(File::isDirectory)?.let {
			for (playerIdentifierString in arrayOf(player.name, player.uniqueId.toString())) {
				for (extension in arrayOf("yml, json")) {
					val file = Path.of(it.path, """$playerIdentifierString.$extension""").toFile()
					if (file.exists())
						BackupTaskExecutor.scheduleBackup(FileToBackup(file.path), delayInMillis)
				}
			}
		}
	}

	fun scheduleBackupForAllGeneralData(delayInMillis: Long? = null) {
		PlayerBackups.logger.info("Scheduling backup for all Multiverse general data...")
		for ((pluginName, filenamesToBackup) in arrayOf(
			Pair(corePluginName, coreGeneralDataFilesnamesToBackup),
			Pair(portalsPluginName, portalsGeneralDataFilesnamesToBackup),
			Pair(netherPortalsPluginName, netherPortalsGeneralDataFilesnamesToBackup),
			Pair(inventoriesPluginName, inventoriesGeneralDataFilesnamesToBackup)
		)) {
			if (Bukkit.getPluginManager().getPlugin(pluginName) == null) {
				logger.warning("Plugin $pluginName is not loaded, but any existing plugin files will still be backed up")
			}
			filenamesToBackup.forEach {
				val file = Path.of(PluginFolders.getPluginDataFolder(pluginName).path, it).toFile()
				if (file.exists())
					BackupTaskExecutor.scheduleBackup(FileToBackup(file.path), delayInMillis)
			}
		}
	}

	fun scheduleBackupForAllOnlinePlayers(delayInMillis: Long? = null) {
		PlayerBackups.logger.info("Scheduling backup for all online players' Multiverse data...")
		Bukkit.getOnlinePlayers().forEach { schedulePlayerBackup(it, delayInMillis) }
	}

}