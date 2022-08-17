/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.text

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

fun createAmericanDecimalFormat(formatString: String) =
	DecimalFormatSymbols(Locale.getDefault()).apply {
		decimalSeparator = '.'
		groupingSeparator = ','
	}.let {
		DecimalFormat(formatString, it)
	}

fun defaultFormatDouble(value: Double, maxFractionDigits: Int = 2, useGrouping: Boolean = true): String =
	DecimalFormat().apply {
		maximumFractionDigits = maxFractionDigits
		isGroupingUsed = useGrouping
		decimalFormatSymbols = (DecimalFormatSymbols.getInstance().clone() as DecimalFormatSymbols).apply {
			decimalSeparator = '.'
			groupingSeparator = ','
		}
		minimumIntegerDigits = 1
	}.format(value)