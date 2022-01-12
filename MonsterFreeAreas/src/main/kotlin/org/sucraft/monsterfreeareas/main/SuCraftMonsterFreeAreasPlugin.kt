/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.monsterfreeareas.main

import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.monsterfreeareas.listener.MonsterSpawnListener


class SuCraftMonsterFreeAreasPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftMonsterFreeAreasPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		MonsterSpawnListener
	}

}