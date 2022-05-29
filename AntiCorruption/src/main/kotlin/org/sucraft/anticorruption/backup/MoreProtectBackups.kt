/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.anticorruption.backup

import org.bukkit.Bukkit
import org.sucraft.anticorruption.main.SuCraftAntiCorruptionPlugin
import org.sucraft.core.common.bukkit.io.PluginFolders
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import java.io.File

object MoreProtectBackups : SuCraftComponent<SuCraftAntiCorruptionPlugin>(SuCraftAntiCorruptionPlugin.getInstance()) {

	// Settings

	private const val pluginName = """MoreProtect"""

	// Implementation

	fun scheduleBackupForAllData(delayInMillis: Long? = null) {
		PlayerBackups.logger.info("Scheduling backup for all MoreProtect data...")
		if (Bukkit.getPluginManager().getPlugin(pluginName) == null) {
			logger.warning("Plugin $pluginName is not loaded, but any existing plugin files will still be backed up")
		}
		PluginFolders.getPluginDataFolder(pluginName).takeIf(File::isDirectory)?.listFiles()?.forEach {
			BackupTaskExecutor.scheduleBackup(FileToBackup(it.path), delayInMillis)
		}
	}

}