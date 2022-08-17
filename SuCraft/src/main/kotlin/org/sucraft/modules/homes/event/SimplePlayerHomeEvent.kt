/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.homes.event

import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.sucraft.common.event.and
import org.sucraft.modules.homes.Home

/**
 * Base implementation of [PlayerHomeEvent] that uses backing field to implement [player] and [home].
 */
abstract class SimplePlayerHomeEvent(override val player: Player, override val home: Home) : PlayerHomeEvent() {

	// Event handlers

	override fun getHandlers() = handlerList and super.getHandlers()

	companion object {

		@Suppress("NON_FINAL_MEMBER_IN_OBJECT")
		@JvmStatic
		open val handlerList = HandlerList()

	}

}