/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.temporarymobfarmlimits.delegate

import org.sucraft.core.common.general.log.AbstractLogger
import org.sucraft.core.common.sucraft.delegate.MobFarmWeight
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.temporarymobfarmlimits.data.MobFarmWeightData
import org.sucraft.temporarymobfarmlimits.main.SuCraftTemporaryMobFarmLimitsPlugin

object MobFarmWeightDelegate : MobFarmWeight<SuCraftTemporaryMobFarmLimitsPlugin>, SuCraftComponent<SuCraftTemporaryMobFarmLimitsPlugin>(SuCraftTemporaryMobFarmLimitsPlugin.getInstance()) {

	// Initialization

	init {
		MobFarmWeight.registerImplementation(this)
	}

	// Delegate overrides

	override fun getDelegatePlugin(): SuCraftTemporaryMobFarmLimitsPlugin = plugin

	override fun getDelegateLogger(): AbstractLogger = logger

	// Implementation

	override fun getWeight() = MobFarmWeightData.getWeight()

	override fun setWeight(weight: Double) = MobFarmWeightData.setWeight(weight)

}