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

}