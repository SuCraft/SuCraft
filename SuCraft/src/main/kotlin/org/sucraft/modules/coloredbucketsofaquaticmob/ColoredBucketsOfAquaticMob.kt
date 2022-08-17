/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.coloredbucketsofaquaticmob

import org.sucraft.common.module.SuCraftModule

/**
 * Shows the color of aquatic mobs in your inventory,
 * by adding a colored lore to the item,
 * as well as setting CustomModelData for use with a resource pack.
 */
object ColoredBucketsOfAquaticMob : SuCraftModule<ColoredBucketsOfAquaticMob>() {

	// Components

	override val components = listOf(
		ColoredBucketsOfAxolotl,
		ColoredBucketsOfTropicalFish
	)

}