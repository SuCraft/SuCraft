/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.temporarymobfarmlimits.main

import org.sucraft.core.common.general.pattern.SingletonContainer
import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.temporarymobfarmlimits.creaturespawn.CreatureSpawnCancelListener
import org.sucraft.temporarymobfarmlimits.creaturespawn.CreatureSpawnMeasureListener
import org.sucraft.temporarymobfarmlimits.creaturespawn.CreatureSpawnMeasurementData
import org.sucraft.temporarymobfarmlimits.delegate.MobFarmWeightDelegate
import org.sucraft.temporarymobfarmlimits.raid.RaidCancelListener
import org.sucraft.temporarymobfarmlimits.raid.RaidMeasureListener
import org.sucraft.temporarymobfarmlimits.raid.RaidMeasurementData


class SuCraftTemporaryMobFarmLimitsPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftTemporaryMobFarmLimitsPlugin>()

	// Enable

	override fun onSuCraftPluginEnable() {
		// Initialize components
		MobFarmWeightDelegate
		CreatureSpawnMeasurementData
		CreatureSpawnMeasureListener
		CreatureSpawnCancelListener
		RaidMeasurementData
		RaidMeasureListener
		RaidCancelListener
	}

}