/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.homes.event

import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.sucraft.common.event.and
import org.sucraft.modules.homes.Home

/**
 * An event called when a player teleports to a home (not necessarily their own home).
 */
open class PlayerTeleportToHomeEvent(player: Player, home: Home) : SimplePlayerHomeEvent(player, home) {

	// Event handlers

	override fun getHandlers() = handlerList and super.getHandlers()

	companion object {

		@Suppress("NON_FINAL_MEMBER_IN_OBJECT")
		@JvmStatic
		open val handlerList = HandlerList()

	}

}