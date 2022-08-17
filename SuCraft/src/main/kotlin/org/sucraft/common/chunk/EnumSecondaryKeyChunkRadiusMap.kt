/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.chunk

import java.util.*

/**
 * A [SecondaryKeyChunkRadiusMap] implementation where the [key type][K] is an enum, allowing for a specifically
 * efficient implementation.
 */
class EnumSecondaryKeyChunkRadiusMap<K : Enum<K>, V>(val type: Class<K>, initialCapacity: Int = 3) :
	SecondaryKeyChunkRadiusMap<K, V>(initialCapacity) {

	override fun constructSecondaryMap(): MutableMap<K, V> = EnumMap(type)

}