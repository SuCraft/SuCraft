/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.text

/**
 * @return A basic implementation for a value similar to [hashCode], but of a long type.
 */
fun String.longHashCode(): Long {
	var hash = hashCode().toLong()
	for (index in 0..16) {
		if (index >= length)
			break
		hash = 31 * hash + this[index].code
	}
	return hash
}