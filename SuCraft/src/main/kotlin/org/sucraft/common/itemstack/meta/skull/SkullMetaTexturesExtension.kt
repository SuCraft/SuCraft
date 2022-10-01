/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.itemstack.meta.skull

import org.bukkit.Bukkit
import org.bukkit.inventory.meta.SkullMeta
import org.sucraft.common.player.profile.setTextures
import java.util.*

fun SkullMeta.setPlayerProfile(uuid: UUID, textures: String) {
	playerProfile = Bukkit.createProfile(uuid).apply {
		setTextures(textures)
	}
}