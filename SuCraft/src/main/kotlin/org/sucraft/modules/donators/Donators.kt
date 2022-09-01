/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.donators

import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.NamedTextColor.AQUA
import net.kyori.adventure.text.format.NamedTextColor.WHITE
import org.bukkit.entity.Player
import org.sucraft.common.function.runEach
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.text.component
import org.sucraft.common.text.sendMessage
import org.sucraft.modules.discordinformation.DiscordChannel
import org.sucraft.modules.offlineplayerinformation.OfflinePlayerInformation

/**
 * Donator perks are managed by permissions.
 * This module currently also provides a way to inform players of donating if they are not a donator.
 */
object Donators : SuCraftModule<Donators>() {

	// Dependencies

	override val dependencies = listOf(
		OfflinePlayerInformation
	)

	// Initialization

	override fun onInitialize() {
		super.onInitialize()
		// Make sure we know the donator names for each rank
		// Disabled because of some glitch in PermissionsBukkit that breaks the config.yml if this is called
//		PerpetualDonatorRank.values().runEach { startComputingPlayerNames() }
	}

	// Donation information

	private val discordDonationsInfoChannel = DiscordChannel.GUIDE

	private val donatingInfoLinkMessage = component(color = WHITE) {
		+"Check the Discord server ${discordDonationsInfoChannel.hashtagChannelName} channel:" +
				discordDonationsInfoChannel.getURLComponent(color = NamedTextColor.GREEN)
	}

	fun Player.sendOnlyDonatorsHaveAbility(ability: String) {
		sendMessage(
			395847994987234756L, AQUA,
			ability
		) {
			+"Donators can" + variable - """! \o/"""
		}
		sendDonatingInfoLink()
	}

	fun Player.sendDonatingInfoLink() =
		sendMessage(
			donatingInfoLinkMessage
		)

}