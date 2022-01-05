/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.temporarymobfarmlimits.data

import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.temporarymobfarmlimits.main.SuCraftTemporaryMobFarmLimitsPlugin


object MobFarmWeightData : SuCraftComponent<SuCraftTemporaryMobFarmLimitsPlugin>(SuCraftTemporaryMobFarmLimitsPlugin.getInstance()) {

	// Data

	private var weight = 1.0

	// Implementation

	fun getWeight() = weight

	fun setWeight(weight: Double) {
		this.weight = weight
	}

}