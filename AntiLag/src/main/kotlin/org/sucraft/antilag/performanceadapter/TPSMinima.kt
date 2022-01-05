/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.antilag.performanceadapter

class TPSMinima(
	vararg val tps: Double
) {

	val atMaximumInterval get() = tps[0]
	val atMinimumInterval get() = tps[1]

}