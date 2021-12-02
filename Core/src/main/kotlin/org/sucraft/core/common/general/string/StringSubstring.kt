/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.general.string


object StringSubstring {

	fun safeSubstring(string: String, start: Int, end: Int = string.length): String {
		var safeEnd = end
		if (safeEnd > string.length)  safeEnd = string.length
		var safeStart = start
		if (safeStart < 0)  safeStart = 0
		return if (safeEnd <= safeStart) "" else string.substring(safeStart, safeEnd)
	}

}