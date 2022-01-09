/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.core.common.sucraft.delegate.home

import lombok.Getter
import org.bukkit.event.HandlerList
import org.sucraft.core.common.bukkit.event.ArrayHandlerList


abstract class PlayerTeleportToHomeEvent : PlayerHomeEvent() {

	// Handlers

	override fun getHandlers(): HandlerList = ArrayHandlerList(handlerList, super.getHandlers())

	companion object {

		@Getter@JvmField
		val handlerList = HandlerList()

	}

}