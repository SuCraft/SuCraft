/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.chunk

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.sucraft.common.region.RegionCoordinates

internal class RegionCoordinatesTest {

	@Test
	fun getRelativeWithinChessboardRadius() {
		val x = 5
		val z = -20
		val world1 = "one"
		val world2 = "two"
		for (radius in 0..4) {
			val relatives = RegionCoordinates.get(world1, x, z).getRelativeWithinChessboardRadius(radius).toList()
			for (dx in -10..10) {
				for (dz in -10..10) {
					val shouldContainWorld1Relative = dx >= -radius && dx <= radius && dz >= -radius && dz <= radius
					assertEquals(
						shouldContainWorld1Relative,
						RegionCoordinates.get(world1, x + dx, z + dz) in relatives,
						"Relative $dx, $dz should ${if (shouldContainWorld1Relative) "" else "not "}" +
								"be in relative list for radius $radius: $relatives"
					)
					assertFalse(RegionCoordinates.get(world2, x + dx, z + dz) in relatives)
				}
			}
			assertEquals((2 * radius + 1) * (2 * radius + 1), relatives.size)
		}
	}

}