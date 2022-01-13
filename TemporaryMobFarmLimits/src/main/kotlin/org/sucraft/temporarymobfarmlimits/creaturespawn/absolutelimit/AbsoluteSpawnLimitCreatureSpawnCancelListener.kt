/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.temporarymobfarmlimits.creaturespawn.absolutelimit

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.CreatureSpawnEvent
import org.sucraft.core.common.bukkit.chunk.ChunkCoordinates
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.temporarymobfarmlimits.creaturespawn.CreatureSpawnMeasurementData
import org.sucraft.temporarymobfarmlimits.main.SuCraftTemporaryMobFarmLimitsPlugin


object AbsoluteSpawnLimitCreatureSpawnCancelListener : SuCraftComponent<SuCraftTemporaryMobFarmLimitsPlugin>(SuCraftTemporaryMobFarmLimitsPlugin.getInstance()) {

	// Events

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	fun onCreatureSpawn(event: CreatureSpawnEvent) {
		if (!AbsoluteSpawnLimitData.areAbsoluteNaturalSpawnsLimitedForEntityType(event.entityType)) return
		if (!AbsoluteSpawnLimitData.areSpawnsOfSpawnReasonLimited(event.spawnReason)) return
		if (!CreatureSpawnMeasurementData.isProbablyFarm(event.entityType, ChunkCoordinates.get(event.location))) return
		if (!AbsoluteSpawnLimitData.evaluateChanceForEntityToSpawn(event.entityType))
			event.isCancelled = true
	}

}