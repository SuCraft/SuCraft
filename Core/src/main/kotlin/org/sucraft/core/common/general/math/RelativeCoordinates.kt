/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.core.common.general.math

import kotlin.math.ceil


object RelativeCoordinates {

	fun relativeWithinSquareRadius(radius: Int): Sequence<Pair<Int, Int>> = sequence {
		for (dx in -radius..radius) {
			for (dz in -radius..radius) {
				yield(Pair(dz, dz))
			}
		}
	}

	fun relativeWithinSquareRadius(radius: Double) = relativeWithinSquareRadius(ceil(radius).toInt())

	fun relativeWithinRadius(radius: Int) = relativeWithinSquareRadius(radius).filter { (dx, dz) -> dx * dx + dz * dz <= radius * radius }

	fun relativeWithinRadius(radius: Double) = relativeWithinSquareRadius(radius).filter { (dx, dz) -> dx * dx + dz * dz <= radius * radius }

}