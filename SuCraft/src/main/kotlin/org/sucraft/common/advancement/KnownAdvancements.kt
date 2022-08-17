/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.advancement

import org.bukkit.Bukkit
import org.bukkit.NamespacedKey

val diamondsAdvancement by lazy {
	Bukkit.getAdvancement(NamespacedKey.minecraft("story/mine_diamond"))!!
}