/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.sucraft.persistentdata

import org.bukkit.NamespacedKey


object DefaultSuCraftNamespace {

	private const val defaultNamespace = "sucraft"

	@Suppress("DEPRECATION")
	fun getNamespacedKey(key: String) = NamespacedKey(defaultNamespace, key)

}