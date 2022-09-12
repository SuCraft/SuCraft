/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.clientpacketacceptance

import org.bukkit.Bukkit
import org.sucraft.common.geyser.isConnectedViaGeyser
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.viaversion.clientVersion

/**
 * Provides to the server what players with certain client versions (determined with
 * ViaVersion and Geyser) can accept, specifically:
 * - Whether they can accept large packets.
 * - Whether they can accept a server resource pack.
 */
object ClientPacketAcceptance : SuCraftModule<ClientPacketAcceptance>() {

	// Settings

	const val minimumProtocolVersionThatCanAcceptLargePackets = 755 // 1.17

	// Initialization

	override fun onInitialize() {
		super.onInitialize()
		// Add the large packet predicate as a disjunct
		Bukkit.addCanAcceptLargePacketsDisjunct { player ->
			player.clientVersion >= minimumProtocolVersionThatCanAcceptLargePackets &&
					!player.isConnectedViaGeyser
		}
		// Add the server resource pack predicate as a disjunct
		Bukkit.addCanAcceptServerResourcePackDisjunct { player ->
			!player.isConnectedViaGeyser
		}
	}

}