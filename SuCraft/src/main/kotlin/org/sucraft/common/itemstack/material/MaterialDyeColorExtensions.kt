/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.itemstack.material

import org.bukkit.DyeColor
import org.bukkit.Material

fun getDyeColorByPrefixedString(string: String) =
	DyeColor.values().first { string.startsWith(it.name, ignoreCase = true) }

val Material.dyeColor
	get() =
		if (
			isWoolFullBlock ||
			isWoolSlab ||
			isWoolStairs ||
			isDye ||
			isUnglazedColoredTerracottaFullBlock ||
			isUnglazedColoredTerracottaSlab ||
			isUnglazedColoredTerracottaStairs ||
			isGlazedTerracottaFullBlock ||
			isStainedGlass ||
			isStainedGlassPane ||
			isColoredShulkerBox ||
			isConcreteFullBlock ||
			isConcreteSlab ||
			isConcreteStairs ||
			isConcretePowderFullBlock ||
			isConcretePowderSlab ||
			isConcretePowderStairs ||
			isBed ||
			isColoredCandle ||
			isWoolCarpet
		) {
			getDyeColorByPrefixedString(keyKey)
		} else null

fun Iterable<Material>.firstWithDyeColor(dyeColor: DyeColor) =
	first { it.dyeColor == dyeColor }

fun Iterable<Material>.firstWithDyeColorOrNull(dyeColor: DyeColor) =
	firstOrNull { it.dyeColor == dyeColor }