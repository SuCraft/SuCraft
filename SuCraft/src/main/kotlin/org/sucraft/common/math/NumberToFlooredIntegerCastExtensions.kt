/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.math

/**
 * @return Similar to [Number.toByte], but rounds to negative infinity instead of towards zero.
 */
fun Number.toByteFloored(): Byte {
	if (this is Byte) return this
	if (this is Short || this is Int || this is Long) return toByte()
	if (this is Float) return if (this >= 0.0 || this == toByte().toFloat()) toByte() else (toByte() - 1).toByte()
	return if ((this as Double) >= 0.0 || this == toByte().toDouble()) toByte() else (toByte() - 1).toByte()
}

/**
 * @return Similar to [Number.toShort], but rounds to negative infinity instead of towards zero.
 */
fun Number.toShortFloored(): Short {
	if (this is Short) return this
	if (this is Byte || this is Int || this is Long) return toShort()
	if (this is Float) return if (this >= 0.0 || this == toShort().toFloat()) toShort() else (toShort() - 1).toShort()
	return if ((this as Double) >= 0.0 || this == toShort().toDouble()) toShort() else (toShort() - 1).toShort()
}

/**
 * @return Similar to [Number.toInt], but rounds to negative infinity instead of towards zero.
 */
fun Number.toIntFloored(): Int {
	if (this is Int) return this
	if (this is Byte || this is Short || this is Long) return toInt()
	if (this is Float) return if (this >= 0.0 || this == toInt().toFloat()) toInt() else toInt() - 1
	return if ((this as Double) >= 0.0 || this == toInt().toDouble()) toInt() else toInt() - 1
}

/**
 * @return Similar to [Number.toLong], but rounds to negative infinity instead of towards zero.
 */
fun Number.toLongFloored(): Long {
	if (this is Long) return this
	if (this is Byte || this is Short || this is Int) return toLong()
	if (this is Float) return if (this >= 0.0 || this == toLong().toFloat()) toLong() else toLong() - 1
	return if ((this as Double) >= 0.0 || this == toLong().toDouble()) toLong() else toLong() - 1
}