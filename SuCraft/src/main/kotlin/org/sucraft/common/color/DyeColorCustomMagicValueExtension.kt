/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.color

import org.bukkit.DyeColor
import org.bukkit.DyeColor.*

/**
 * A custom magic value for [DyeColor], that will not change if the Bukkit code changes.
 */
val DyeColor.customMagicValue
	get() = when (this) {
		WHITE -> 0
		ORANGE -> 1
		MAGENTA -> 2
		LIGHT_BLUE -> 3
		YELLOW -> 4
		LIME -> 5
		PINK -> 6
		GRAY -> 7
		LIGHT_GRAY -> 8
		CYAN -> 9
		PURPLE -> 10
		BLUE -> 11
		BROWN -> 12
		GREEN -> 13
		RED -> 14
		BLACK -> 15
	}