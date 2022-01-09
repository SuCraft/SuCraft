/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.core.common.sucraft.delegate.home

import lombok.Getter
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList


abstract class PlayerHomeEvent : Event() {

	abstract fun getPlayer(): Player

	abstract fun isOwnHome(): Boolean

	// Handlers

	override fun getHandlers(): HandlerList = handlerList

	companion object {

		@Getter@JvmField
		val handlerList = HandlerList()

	}

}