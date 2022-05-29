/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.temporarymobfarmlimits.raid

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.raid.RaidTriggerEvent
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.temporarymobfarmlimits.main.SuCraftTemporaryMobFarmLimitsPlugin

object RaidMeasureListener : SuCraftComponent<SuCraftTemporaryMobFarmLimitsPlugin>(SuCraftTemporaryMobFarmLimitsPlugin.getInstance()) {

	// Events

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onRaidTrigger(event: RaidTriggerEvent) =
		RaidMeasurementData.incrementAmount(event.raid.location)

}