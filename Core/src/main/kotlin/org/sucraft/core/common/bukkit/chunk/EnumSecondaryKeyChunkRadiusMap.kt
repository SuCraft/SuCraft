/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.core.common.bukkit.chunk

import java.util.*


class EnumSecondaryKeyChunkRadiusMap<K : Enum<K>, V>(val type: Class<K>, initialCapacity: Int = 3) : SecondaryKeyChunkRadiusMap<K, V>(initialCapacity) {

	override fun constructSecondaryMap(): MutableMap<K, V> = EnumMap(type)

}