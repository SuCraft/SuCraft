/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.preventmobexplosionblockdamage

import org.bukkit.block.BlockFace.UP
import org.bukkit.entity.*
import org.bukkit.event.entity.EntityExplodeEvent
import org.sucraft.common.event.on
import org.sucraft.common.module.SuCraftModule

/**
 * Prevents some explosions from mobs from breaking blocks.
 */
object PreventMobExplosionBlockDamage : SuCraftModule<PreventMobExplosionBlockDamage>() {

	// Events

	init {
		// Listen for explosions to cancel them if they are undesired
		on(EntityExplodeEvent::class) {
			when (entity) {
				// Prevent creeper or wither explosions from breaking blocks
				is Creeper,
				is Wither,
				is WitherSkull -> {
					blockList().clear()
				}
				// Prevent ghasts from breaking blocks
				// Instead of replacing broken blocks by fire, place fire on top of the blocks that would be broken
				is Ghast,
				is Fireball -> {
					val newBlockList = blockList().asSequence()
						.map { it.getRelative(UP) }
						.filter { it.type.isAir }
					blockList().clear()
					blockList().addAll(newBlockList)
				}
			}
		}
	}

}