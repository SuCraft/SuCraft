/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.core.common.bukkit.entity

import org.bukkit.entity.EntityType


object EntityUtils {

	fun canSpawnFromBucket(type: EntityType): Boolean {
		return when (type) {
			EntityType.AXOLOTL, EntityType.COD, EntityType.PUFFERFISH, EntityType.SALMON, EntityType.TROPICAL_FISH -> true
			else -> false
		}
	}

}