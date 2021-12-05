/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.bukkit.scheduler

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask
import org.sucraft.core.common.bukkit.inventory.InventoryCounter
import org.sucraft.core.common.sucraft.player.PlayerUUID


object RunInFuture {

	fun forPlayerIfOnline(plugin: JavaPlugin, player: Player, action: (Player) -> Any?, delay: Long = 1): BukkitTask {
		val uuid = PlayerUUID.get(player)
		return Bukkit.getScheduler().runTaskLater(plugin, Runnable {
			uuid.getOnlinePlayer()?.let(action)
		}, delay)
	}

	fun useInventoryDifference(plugin: JavaPlugin, player: Player, action: (onlinePlayer: Player, inventoryDifference: Map<ItemStack, Int>) -> Any?, preservePredicate: ((ItemStack) -> Boolean)? = null, delay: Long = 1) {
		val beforeInventory =
			if (preservePredicate == null)
				InventoryCounter.getOrderedSnapshot(player.inventory)
			else
				InventoryCounter.getOrderedSnapshot(player.inventory, preservePredicate)
		forPlayerIfOnline(
			plugin,
			player,
			{ onlinePlayer ->
				action(
					onlinePlayer,
					if (preservePredicate == null)
						InventoryCounter.getDifferenceMap(beforeInventory, onlinePlayer.inventory.contents)
					else
						InventoryCounter.getDifferenceMap(beforeInventory, onlinePlayer.inventory.contents, preservePredicate)
				)
			},
			delay
		)
	}

}