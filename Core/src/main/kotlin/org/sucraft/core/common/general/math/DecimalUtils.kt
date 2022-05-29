/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.general.math

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

object DecimalUtils {

	fun createAmericanDecimalFormat(formatString: String): DecimalFormat {
		val symbols = DecimalFormatSymbols(Locale.getDefault())
		symbols.decimalSeparator = '.'
		symbols.groupingSeparator = ','
		return DecimalFormat(formatString, symbols)
	}

	fun defaultFormatDouble(value: Double, maxFractionDigits: Int = 2, useGrouping: Boolean = true): String {
		val decimalFormat = DecimalFormat()
		decimalFormat.maximumFractionDigits = maxFractionDigits
		decimalFormat.isGroupingUsed = useGrouping
		val symbols = DecimalFormatSymbols.getInstance().clone() as DecimalFormatSymbols
		symbols.decimalSeparator = '.'
		symbols.groupingSeparator = ','
		decimalFormat.decimalFormatSymbols = symbols
		decimalFormat.minimumIntegerDigits = 1
		return decimalFormat.format(value)
	}

}