/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.general.string


object StringConcatenation {

	fun concatenateSpaced(strings: Array<String?>, start: Int = 0, end: Int = strings.size): String {
		val resultBuilder = StringBuilder("")
		var i = start
		while (i < end && i < strings.size) {
			if (i != start) {
				resultBuilder.append(' ')
			}
			resultBuilder.append(strings[i])
			i++
		}
		return resultBuilder.toString()
	}

	fun concatenate(a: Array<String>, skipIfAlreadyEndsWithPer: Boolean = false, vararg b: String): Array<String> {
		val concatenated: MutableList<String> = ArrayList(a.size + b.size)
		for (string in a) {
			concatenated.add(string)
		}
		for (string in b) {
			if (!skipIfAlreadyEndsWithPer || concatenated[concatenated.size - 1] != string) {
				concatenated.add(string)
			}
		}
		return concatenated.toTypedArray()
	}

}