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

	/**
	 * Current value: release 1.17.
	 */
	const val minimumProtocolVersionThatCanAcceptLargePackets = 755

	/**
	 * Current value: release 1.12.
	 * This is not because of something in this version, but because the model overrides for items
	 * used to display block entities are all overrides on items that exist in version 1.12 or earlier,
	 * so any earlier version misses some of these items and therefore displays strange floating armor stand
	 * entities (with a head item of the intended item replaced by an earlier version by ViaVersion)
	 * instead of the intended block entities.
	 */
	const val minimumProtocolVersionThatCanCorrectlyDisplayServerResourcePack = 335

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
			player.clientVersion >= minimumProtocolVersionThatCanCorrectlyDisplayServerResourcePack &&
					!player.isConnectedViaGeyser
		}
		// Add the non-Minecraft recipes predicate as a disjunct
		Bukkit.addCanAcceptNonMinecraftRecipesDisjunct {
			// Currently we assume all clients can accept recipes with a different namespace than "minecraft:",
			// but this is not certain: maybe some parts of Bedrock clients or Just Enough Items or similar mods
			// cannot accept them
			true
		}
	}

}