/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.antimobgrief.listener

import org.bukkit.block.BlockFace
import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityExplodeEvent
import org.sucraft.antimobgrief.main.SuCraftAntiMobGriefPlugin
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent


object ExplosionListener : SuCraftComponent<SuCraftAntiMobGriefPlugin>(SuCraftAntiMobGriefPlugin.getInstance(), "Explosion listener") {

	// Events

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	fun onEntityExplode(event: EntityExplodeEvent) {
		when (event.entity) {
			// Prevent creepers or withers from breaking blocks
			is Creeper,
			is Wither,
			is WitherSkull -> {
				event.blockList().clear()
			}
			// Prevent ghasts from breaking blocks
			// Instead of replacing broken blocks by fire, place fire on top of the blocks that would be broken
			is Ghast,
			is Fireball -> {
				val newBlockList = event.blockList().map { it.getRelative(BlockFace.UP) }.filter { it.type.isAir }
				event.blockList().clear()
				event.blockList().addAll(newBlockList);
			}
		}
	}

}