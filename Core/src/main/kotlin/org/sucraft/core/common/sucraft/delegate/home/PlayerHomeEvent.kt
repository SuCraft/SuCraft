/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.core.common.sucraft.delegate.home

import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

abstract class PlayerHomeEvent : Event() {

	abstract fun getPlayer(): Player

	abstract fun isOwnHome(): Boolean

	// Handlers

	override fun getHandlers(): HandlerList = handlerList

	companion object {

		@Suppress("NON_FINAL_MEMBER_IN_OBJECT")
		@JvmStatic
		open val handlerList = HandlerList()

	}

}