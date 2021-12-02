/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.general.string


object StringAnalysis {

	fun countCapitalLetters(string: String): Long {
		var count: Long = 0
		for (c in string.toCharArray()) {
			if (Character.isUpperCase(c)) {
				count++
			}
		}
		return count
	}

}