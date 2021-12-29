/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.bukkitapiextension.event.playerscoopentity

import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.inventory.ItemStack
import org.sucraft.bukkitapiextension.event.BukkitAPIExtensionEvent


data class PlayerScoopEntityEvent(
	val originalEvent: PlayerInteractEntityEvent,
	val resultingItem: ItemStack
) : BukkitAPIExtensionEvent() {

	// Derived properties

	val entity: Entity get() = originalEvent.rightClicked
	val player: Player get() = originalEvent.player

	// Handlers

	override fun getHandlers() = handlerList

	companion object {

		@JvmStatic
		val handlerList = HandlerList()

	}

}