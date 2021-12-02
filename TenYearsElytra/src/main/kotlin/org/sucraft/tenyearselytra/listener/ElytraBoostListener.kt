/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.tenyearselytra.listener

import com.destroystokyo.paper.event.player.PlayerElytraBoostEvent
import org.bukkit.FireworkEffect
import org.bukkit.entity.Firework
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.inventory.meta.FireworkMeta
import org.bukkit.scheduler.BukkitTask
import org.sucraft.core.common.sucraft.player.PlayerUUID
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.tenyearselytra.data.ElytraData
import org.sucraft.tenyearselytra.fireworks.FestiveFireworks
import org.sucraft.tenyearselytra.main.SuCraftTenYearsElytraPlugin


object ElytraBoostListener : SuCraftComponent<SuCraftTenYearsElytraPlugin>(SuCraftTenYearsElytraPlugin.getInstance()) {

	// Events

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onPlayerElytraBoost(event: PlayerElytraBoostEvent) {
		val player = event.player
		val chestplate = player.inventory.chestplate
		if (!ElytraData.isFestiveElytra(chestplate)) return
		// Fix old lore if present
		ElytraData.fixOldLore(chestplate!!)?.let {
			logger.info("Fixing ten years elytra from ${chestplate.lore()!!.size} lore lines to ${it.lore()!!.size} lore lines for ${player.name}")
			player.inventory.chestplate = it
		}
		// Schedule to spawn fireworks
		FestiveFireworks.scheduleFireworks(player)
	}

}