/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.viaversionplayerlargepackets

import org.bukkit.Bukkit
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.viaversion.clientVersion

/**
 * Provides to the server that players with certain client versions (determined with
 * ViaVersion) can accept large packets.
 */
object ViaVersionPlayerLargePackets : SuCraftModule<ViaVersionPlayerLargePackets>() {

	// Settings

	const val minimumProtocolVersionThatCanAcceptLargePackets = 755 // 1.17

	// Initialization

	override fun onInitialize() {
		super.onInitialize()
		// Add the version predicate as a disjunct
		Bukkit.addCanAcceptLargePacketsDisjunct { player ->
			player.clientVersion >= minimumProtocolVersionThatCanAcceptLargePackets
		}
	}

}