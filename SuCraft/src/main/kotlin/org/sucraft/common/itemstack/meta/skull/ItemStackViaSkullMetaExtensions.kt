/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.itemstack.meta.skull

import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import org.sucraft.common.itemstack.meta.runMeta
import org.sucraft.common.itemstack.meta.withMeta
import java.util.*

var ItemStack.owningPlayer
	/**
	 * @throws IllegalArgumentException If the meta is not an instance of [SkullMeta].
	 */
	@Throws(IllegalArgumentException::class)
	get() = runMeta(SkullMeta::class) { owningPlayer }
	/**
	 * @throws IllegalArgumentException If the meta is not an instance of [SkullMeta].
	 */
	@Throws(IllegalArgumentException::class)
	set(value) {
		withMeta(SkullMeta::class) { owningPlayer = value }
	}

var ItemStack.playerProfile
	/**
	 * @throws IllegalArgumentException If the meta is not an instance of [SkullMeta].
	 */
	@Throws(IllegalArgumentException::class)
	get() = runMeta(SkullMeta::class) { playerProfile }
	/**
	 * @throws IllegalArgumentException If the meta is not an instance of [SkullMeta].
	 */
	@Throws(IllegalArgumentException::class)
	set(value) {
		withMeta(SkullMeta::class) { playerProfile = value }
	}

/**
 * @throws IllegalArgumentException If the meta is not an instance of [SkullMeta].
 */
@Throws(IllegalArgumentException::class)
fun ItemStack.setPlayerProfile(uuid: UUID, textures: String) =
	runMeta(SkullMeta::class) { setPlayerProfile(uuid, textures) }