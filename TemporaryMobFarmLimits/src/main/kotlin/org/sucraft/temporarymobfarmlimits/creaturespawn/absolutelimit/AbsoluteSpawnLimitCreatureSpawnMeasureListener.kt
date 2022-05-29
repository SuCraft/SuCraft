/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.temporarymobfarmlimits.creaturespawn.absolutelimit

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.CreatureSpawnEvent
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.temporarymobfarmlimits.main.SuCraftTemporaryMobFarmLimitsPlugin

object AbsoluteSpawnLimitCreatureSpawnMeasureListener : SuCraftComponent<SuCraftTemporaryMobFarmLimitsPlugin>(SuCraftTemporaryMobFarmLimitsPlugin.getInstance()) {

	// Events

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onCreatureSpawn(event: CreatureSpawnEvent) {
		if (!AbsoluteSpawnLimitData.areAbsoluteNaturalSpawnsLimitedForEntityType(event.entityType)) return
		AbsoluteSpawnLimitData.incrementCount(event.entityType)
	}

}