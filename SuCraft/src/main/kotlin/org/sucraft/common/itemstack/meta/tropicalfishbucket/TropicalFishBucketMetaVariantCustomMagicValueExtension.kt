/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.itemstack.meta.tropicalfishbucket

import org.bukkit.DyeColor
import org.bukkit.entity.TropicalFish
import org.bukkit.inventory.meta.TropicalFishBucketMeta
import org.sucraft.common.color.customMagicValue
import org.sucraft.common.entity.customMagicValue

/**
 * A custom magic value for the [TropicalFishBucketMeta] variant,
 * based on the
 * - [pattern][TropicalFishBucketMeta.getPattern],
 * - [body color][TropicalFishBucketMeta.getBodyColor] and
 * - [pattern color][TropicalFishBucketMeta.getPatternColor],
 * that will not change if the Bukkit code changes.
 *
 * This uses the magic values [TropicalFish.Pattern.customMagicValue]
 * and [DyeColor.customMagicValue].
 *
 * This value is 0 if this meta has no variant.
 * This value is always at least 1 if this meta has a variant, and accommodates for
 * 30 patterns (instead of the actual existing 12)
 * and 32 body colors and pattern colors (instead of the actual existing 16)
 * as a margin for future additions.
 */
val TropicalFishBucketMeta.customMagicValue
	get() =
		if (hasVariant())
			1 + pattern.customMagicValue + 30 * (bodyColor.customMagicValue + 32 * patternColor.customMagicValue)
		else
			0
