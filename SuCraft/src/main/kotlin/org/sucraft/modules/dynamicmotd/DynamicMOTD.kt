/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.dynamicmotd

import com.destroystokyo.paper.event.server.PaperServerListPingEvent
import org.sucraft.common.event.on
import org.sucraft.common.module.AbstractSuCraftComponent
import org.sucraft.common.module.SuCraftModule
import org.sucraft.modules.donators.Donators

/**
 * Customizes the MOTD shown in the server ping list, based on the number of online players.
 */
object DynamicMOTD : SuCraftModule<DynamicMOTD>() {

	// Dependencies

	override val dependencies = listOf(
		Donators
	)

	// Events

	init {
		on(PaperServerListPingEvent::class) {
			motd(pickMOTD(address))
		}
	}

}