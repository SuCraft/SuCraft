/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.entity

import org.bukkit.entity.TropicalFish
import org.bukkit.entity.TropicalFish.Pattern.*

/**
 * A custom magic value for [TropicalFish.Pattern], that will not change if the Bukkit code changes.
 */
val TropicalFish.Pattern.customMagicValue
	get() = when (this) {
		KOB -> 0
		SUNSTREAK -> 1
		SNOOPER -> 2
		DASHER -> 3
		BRINELY -> 4
		SPOTTY -> 5
		FLOPPER -> 6
		STRIPEY -> 7
		GLITTER -> 8
		BLOCKFISH -> 9
		BETTY -> 10
		CLAYFISH -> 11
	}