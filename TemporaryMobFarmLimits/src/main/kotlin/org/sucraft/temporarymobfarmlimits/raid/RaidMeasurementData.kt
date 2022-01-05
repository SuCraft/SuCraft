/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.temporarymobfarmlimits.raid

import org.sucraft.temporarymobfarmlimits.data.AmountByChunkMeasurementData


object RaidMeasurementData : AmountByChunkMeasurementData(
	"raids",
	// Settings
	5,
	10,
	20L * 60 * 5, // 5 minute
	1 // so 1 raid per 5 minutes is allowed
)