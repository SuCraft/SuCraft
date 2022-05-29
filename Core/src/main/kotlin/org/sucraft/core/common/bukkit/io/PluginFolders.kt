/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.bukkit.io

import java.io.File
import java.nio.file.Path

object PluginFolders {

	private const val pluginsFolderName = """plugins"""

	fun getPluginsFolder() = File(pluginsFolderName)

	fun getPluginDataFolder(pluginName: String): File = Path.of(getPluginsFolder().path, pluginName).toFile()

}