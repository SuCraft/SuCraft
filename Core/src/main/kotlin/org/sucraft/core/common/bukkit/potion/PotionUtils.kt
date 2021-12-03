/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.bukkit.potion

import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionType


@Suppress("MemberVisibilityCanBePrivate")
object PotionUtils {

	fun hasNonBasePotionEffect(meta: PotionMeta) =
		meta.customEffects.isNotEmpty() || isPotionEffectNonBase(meta.basePotionData.type)

	fun isPotionEffectNonBase(type: PotionType) =
		when (type) {
			PotionType.WATER, PotionType.AWKWARD, PotionType.MUNDANE, PotionType.THICK -> false
			else -> true
		}

}