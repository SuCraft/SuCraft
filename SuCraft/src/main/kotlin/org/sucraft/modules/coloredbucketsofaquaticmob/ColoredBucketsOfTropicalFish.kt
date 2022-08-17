/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.coloredbucketsofaquaticmob

import org.bukkit.Material.TROPICAL_FISH_BUCKET
import org.bukkit.entity.EntityType
import org.bukkit.event.EventPriority.LOWEST
import org.bukkit.event.player.PlayerBucketEntityEvent
import org.bukkit.inventory.meta.TropicalFishBucketMeta
import org.sucraft.common.event.on
import org.sucraft.common.itemstack.meta.tropicalfishbucket.customMagicValue
import org.sucraft.common.itemstack.meta.withMeta
import org.sucraft.common.module.SuCraftComponent

/**
 * Shows the color of tropical fish in your inventory.
 */
object ColoredBucketsOfTropicalFish : SuCraftComponent<ColoredBucketsOfAquaticMob>() {

	// Events

	init {
		// Change the bucket item when the player buckets the tropical fish
		on(PlayerBucketEntityEvent::class, LOWEST) {
			if (entity.type != EntityType.TROPICAL_FISH) return@on
			if (entityBucket.type != TROPICAL_FISH_BUCKET) return@on
			entityBucket.withMeta(TropicalFishBucketMeta::class) {
				// Define how to update the bucket item
				fun updateBucket(customModelData: Int?) {
					// Set the custom model data
					if (customModelData != null) setCustomModelData(customModelData)
				}
				// Determine the custom model data based on the variant
				// This is a custom calculation,
				// which returns values much smaller than the usual variant integer data
				// Note that we only set the custom model data if the magic value is non-zero
				updateBucket(customMagicValue.takeIf { it != 0 })
			}
		}
	}

}