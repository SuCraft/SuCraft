/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.itemstack.meta.axolotlbucket

import org.bukkit.inventory.meta.AxolotlBucketMeta

/**
 * The [variant][AxolotlBucketMeta.getVariant], or null if none is [present][AxolotlBucketMeta.hasVariant].
 */
val AxolotlBucketMeta.variantOrNull get() = if (hasVariant()) variant else null