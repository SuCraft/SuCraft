/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.monsterfreeareas.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.CreatureSpawnEvent
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.monsterfreeareas.data.MonsterFreeAreasData
import org.sucraft.monsterfreeareas.main.SuCraftMonsterFreeAreasPlugin

object MonsterSpawnListener : SuCraftComponent<SuCraftMonsterFreeAreasPlugin>(SuCraftMonsterFreeAreasPlugin.getInstance()) {

	// Events

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	fun onCreatureSpawn(event: CreatureSpawnEvent) {

		// Check that we want to prevent spawns of this entity type
		if (!MonsterFreeAreasData.isTypeThatIsUndesirableToSpawnInCities(event.entityType)) return
		// Check that we want to prevent spawns that happened for this reason
		if (!MonsterFreeAreasData.isSpawnReasonCertainlyUndesirableIfMonsterInCity(event.spawnReason)) return

		val location = event.location

		// Only block spawns above a certain minimum y, except for those that spawn in the deep ocean: those will be blocked at all heights
		if (
			location.y < MonsterFreeAreasData.minimumYForSpawnToBeUndesirableUnlessDeepOceanHostile
			&& !MonsterFreeAreasData.isDeepOceanHostileType(event.entityType)
		) return

		// Check the region the location is in to determine if it is indeed a place we want to prevent spawns
		try {
			if (MonsterFreeAreasData.isInCityButNotInFarm(location)) {
				event.isCancelled = true
			}
		} catch (e: Exception) {
			logger.warning("An exception occurred while checking WorldGuard regions for monster-free areas: $e")
		}

	}

}