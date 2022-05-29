/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.temporarymobfarmlimits.creaturespawn

import org.bukkit.entity.EntityType
import org.sucraft.temporarymobfarmlimits.data.AmountByChunkAndEnumSecondaryKeyMeasurementData

object CreatureSpawnMeasurementData : AmountByChunkAndEnumSecondaryKeyMeasurementData<EntityType>(
	"creature spawns",
	EntityType::class.java,
	// Settings
	300,
	2,
	20L * 10, // 10 seconds
	3 // so 3 creatures per 10 seconds is allowed
)