/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.itemstack

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.sucraft.common.itemstack.meta.skull.setPlayerProfile
import java.util.*

fun createPlayerHead(uuid: UUID, textures: String, amount: Int = 1) =
	ItemStack(Material.PLAYER_HEAD, amount).apply {
		setPlayerProfile(uuid, textures)
	}