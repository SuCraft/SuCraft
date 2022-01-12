/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.monsterfreeareas.main

import com.sk89q.worldguard.bukkit.WorldGuardPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.monsterfreeareas.listener.MonsterSpawnListener


class SuCraftMonsterFreeAreasPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftMonsterFreeAreasPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Check for WorldGuard
		try {
			(server.pluginManager.getPlugin("WorldGuard") as WorldGuardPlugin?)
				?: logger.warning("Could not find WorldGuard plugin: preventing monster spawns in towns may not work")
		} catch (e: Exception) {
			logger.warning("Exception occurred while getting WorldGuard plugin: $e")
		}
		// Initialize components
		MonsterSpawnListener
	}

}