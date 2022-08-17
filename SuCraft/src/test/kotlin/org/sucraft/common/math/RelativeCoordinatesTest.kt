/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.math

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RelativeCoordinatesTest {

	@Test
	fun coordinatesWithinChessboardRadius2D() {
		for (radius in 0..4) {
			val relatives = coordinatesWithinChessboardRadius2D(radius).toList()
			for (dx in -10..10) {
				for (dz in -10..10) {
					val shouldContainRelative = dx >= -radius && dx <= radius && dz >= -radius && dz <= radius
					assertEquals(
						shouldContainRelative,
						dx to dz in relatives,
						"Relative $dx, $dz should ${if (shouldContainRelative) "" else "not "}" +
								"be in relative list for radius $radius"
					)
				}
			}
			assertEquals((2 * radius + 1) * (2 * radius + 1), relatives.size)
		}
	}

	@Test
	fun coordinatesWithinChessboardRadius2DWithCenterCoordinates() {
		val x = 5
		val z = -20
		for (radius in 0..4) {
			val relatives = coordinatesWithinChessboardRadius2D(x, z, radius).toList()
			for (dx in -10..10) {
				for (dz in -10..10) {
					val shouldContainRelative = dx >= -radius && dx <= radius && dz >= -radius && dz <= radius
					assertEquals(
						shouldContainRelative,
						x + dx to z + dz in relatives,
						"Relative $dx, $dz should ${if (shouldContainRelative) "" else "not "}" +
								"be in relative list for radius $radius"
					)
				}
			}
			assertEquals((2 * radius + 1) * (2 * radius + 1), relatives.size)
		}
	}

}