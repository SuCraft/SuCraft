/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.text

fun String.macroCaseToSpaceSeparatedCapitalized() =
	replace('_', ' ')
		.split(" ")
		.map { it.lowercase().replaceFirstChar(Character::toUpperCase) }
		.joinToString(" ") { it }

val <E : Enum<E>> E.nameSpaceSeparatedCapitalized
	get() =
		name.macroCaseToSpaceSeparatedCapitalized()