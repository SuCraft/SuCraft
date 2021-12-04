/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.customjockeys.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.CreatureSpawnEvent
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.customjockeys.data.CustomJockeyData
import org.sucraft.customjockeys.main.SuCraftCustomJockeysPlugin


object CreatureSpawnListener : SuCraftComponent<SuCraftCustomJockeysPlugin>(SuCraftCustomJockeysPlugin.getInstance()) {

	// Events

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onCreatureSpawn(event: CreatureSpawnEvent) {
		if (!CustomJockeyData.universalPredicateToSpawnJockeyFrom(event.entity, event.location, event.spawnReason)) return
		for (jockey in (CustomJockeyData.registeredJockeysByDetectedType[event.entityType] ?: return)) {
			if (!jockey.checkCanSpawnJockeyFrom(event.entity, event.location, event.spawnReason)) continue
			if (!jockey.evaluateChance()) continue
			if (jockey.spawnJockeyEntity(event.entity, event.location, jockey.getRandomSpawnedEntityType())) break
		}
	}

}