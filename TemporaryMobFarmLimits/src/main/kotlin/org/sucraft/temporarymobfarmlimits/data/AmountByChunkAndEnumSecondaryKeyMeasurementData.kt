/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.temporarymobfarmlimits.data

import org.sucraft.core.common.bukkit.chunk.EnumSecondaryKeyChunkRadiusMap
import org.sucraft.core.common.bukkit.chunk.SecondaryKeyChunkRadiusMap

abstract class AmountByChunkAndEnumSecondaryKeyMeasurementData<K : Enum<K>>(
	measurementName: String,
	protected val secondaryKeyType: Class<K>,
	// Settings
	chunkAmountToClassifyAsFarm: Long,
	markChunkDistance: Int,
	decreaseChunkAmountsIntervalInTicks: Long,
	decreaseChunkAmountsAmount: Long,
) : AmountByChunkAndSecondaryKeyMeasurementData<K>(
	measurementName,
	chunkAmountToClassifyAsFarm,
	markChunkDistance,
	decreaseChunkAmountsIntervalInTicks,
	decreaseChunkAmountsAmount
) {

	// Settings

	override fun constructAmountsMap(): SecondaryKeyChunkRadiusMap<K, Long> = EnumSecondaryKeyChunkRadiusMap(secondaryKeyType)

}