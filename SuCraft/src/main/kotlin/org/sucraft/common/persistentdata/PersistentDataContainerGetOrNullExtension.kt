/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.persistentdata

import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType

fun <T : Any, Z : Any> PersistentDataContainer.getOrNull(key: NamespacedKey, type: PersistentDataType<T, Z>) =
	try {
		get(key, type)
	} catch (_: Exception) {
		null
	}