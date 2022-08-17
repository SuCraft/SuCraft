/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.chunk

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ChunkRadiusMapTest {

	@Test
	fun setGet() {
		val map = ChunkRadiusMap<Long>()
		val coordinates = ChunkCoordinates.get("one", 5, 6)
		assertFalse(coordinates in map)
		assertNull(map[coordinates])
		val value = 5L
		map[coordinates] = value
		assertTrue(coordinates in map)
		assertEquals(value, map[coordinates])
	}

	@Test
	fun setGetNotContainsOthers() {
		val map = ChunkRadiusMap<Long>()
		val coordinates1 = ChunkCoordinates.get("one", 5, 6)
		val coordinates2 = ChunkCoordinates.get("one", 6, 7)
		val coordinates3 = ChunkCoordinates.get("one", 12, 13)
		val coordinates4 = ChunkCoordinates.get("two", 5, 6)
		sequenceOf(coordinates1, coordinates2, coordinates3, coordinates4).forEach {
			assertFalse(it in map)
			assertNull(map[it])
		}
		val value1 = 5L
		map[coordinates1] = value1
		assertTrue(coordinates1 in map)
		assertEquals(value1, map[coordinates1])
		sequenceOf(coordinates2, coordinates3, coordinates4).forEach {
			assertFalse(it in map)
			assertNull(map[it])
		}
		val value2 = 6L
		map[coordinates2] = value2
		assertTrue(coordinates1 in map)
		assertEquals(value1, map[coordinates1])
		assertTrue(coordinates2 in map)
		assertEquals(value2, map[coordinates2])
		sequenceOf(coordinates3, coordinates4).forEach {
			assertFalse(it in map)
			assertNull(map[it])
		}
	}

//	@Test
//	fun setGetRadius() {
//		val map = ChunkRadiusMap<Long>()
//		val world1 = "one"
//		val world2 = "two"
//		val coordinates = ChunkCoordinates.get(world1, 5, 6)
//		coordinates.getRelativeWithinChessboardRadius()
//		sequenceOf(coordinates1, coordinates2, coordinates3).forEach {
//			assertFalse(it in map)
//			assertNull(map[it])
//		}
//		val value1 = 5L
//		map[coordinates1] = value1
//		assertEquals(value1, map[coordinates1])
//		sequenceOf(coordinates2, coordinates3).forEach {
//			assertFalse(it in map)
//			assertNull(map[it])
//		}
//		val value2 = 6L
//		map[coordinates1] = value1
//		assertEquals(value1, map[coordinates1])
//		sequenceOf(coordinates2, coordinates3).forEach {
//			assertFalse(it in map)
//			assertNull(map[it])
//		}
//	}

}