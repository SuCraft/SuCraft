/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.homes.event

import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.sucraft.modules.homes.Home

/**
 * Base class for events that involve a [Player] and a [Home].
 */
abstract class PlayerHomeEvent : Event() {

	// Interface

	abstract val player: Player

	abstract val home: Home

	open val isOwnHome
		get() = home.isOwnedBy(player)

	// Event handlers

	override fun getHandlers() = handlerList

	companion object {

		@Suppress("NON_FINAL_MEMBER_IN_OBJECT")
		@JvmStatic
		open val handlerList = HandlerList()

	}

}