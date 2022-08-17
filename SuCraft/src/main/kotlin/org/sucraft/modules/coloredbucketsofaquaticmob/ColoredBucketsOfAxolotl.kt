/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.coloredbucketsofaquaticmob

import net.kyori.adventure.text.format.NamedTextColor.GRAY
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextColor.color
import org.bukkit.Material.AXOLOTL_BUCKET
import org.bukkit.entity.Axolotl
import org.bukkit.entity.Axolotl.Variant.*
import org.bukkit.entity.EntityType.AXOLOTL
import org.bukkit.event.EventPriority.LOWEST
import org.bukkit.event.player.PlayerBucketEntityEvent
import org.bukkit.inventory.meta.AxolotlBucketMeta
import org.sucraft.common.event.on
import org.sucraft.common.itemstack.meta.addLore
import org.sucraft.common.itemstack.meta.axolotlbucket.variantOrNull
import org.sucraft.common.itemstack.meta.withMeta
import org.sucraft.common.module.SuCraftComponent
import org.sucraft.common.text.unstyledText

/**
 * Shows the color of axolotls in your inventory.
 */
object ColoredBucketsOfAxolotl : SuCraftComponent<ColoredBucketsOfAquaticMob>() {

	// Events

	init {
		// Change the bucket item when the player buckets the axolotl
		on(PlayerBucketEntityEvent::class, LOWEST) {
			if (entity.type != AXOLOTL) return@on
			if (entityBucket.type != AXOLOTL_BUCKET) return@on
			entityBucket.withMeta(AxolotlBucketMeta::class) {
				// Define how to update the bucket item
				fun updateBucket(variantName: String, color: TextColor, customModelData: Int?) {
					// Add the lore line
					addLore(0, unstyledText(variantName, color))
					// Set the custom model data
					if (customModelData != null) setCustomModelData(customModelData)
				}
				// Determine the name, color and custom model data based on the variant
				when (variantOrNull) {
					Axolotl.Variant.BLUE -> updateBucket("Blue", color(179, 183, 252), 4)
					CYAN -> updateBucket("Cyan", color(239, 247, 255), 3)
					Axolotl.Variant.GOLD -> updateBucket("Gold", color(254, 214, 29), 2)
					WILD -> updateBucket("Brown", color(139, 105, 77), 1)
					LUCY -> updateBucket("Pink", color(255, 199, 234), null)
					else -> updateBucket("Unknown variant", GRAY, null)
				}
			}
		}
	}

}