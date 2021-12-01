/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.lightningcommand.command

import org.sucraft.core.common.bukkit.command.CommonTabCompletion
import org.sucraft.core.common.sucraft.command.SuCraftCommand
import org.sucraft.core.common.sucraft.command.SuCraftCommands
import org.sucraft.lightningcommand.main.SuCraftLightningCommandPlugin
import org.sucraft.lightningcommand.player.permission.SuCraftLightningCommandPermissions
import org.sucraft.supporters.chat.SupportingMessages


object SuCraftLightningCommandCommands : SuCraftCommands<SuCraftLightningCommandPlugin>(SuCraftLightningCommandPlugin.getInstance()) {

	val LIGHTNING = SuCraftCommand.createPlayerOnly(
		this,
		"lightning",
		onCommand@{ player, _, _, _ ->
			if (!player.hasPermission(SuCraftLightningCommandPermissions.LIGHTNING)) {
				SupportingMessages.sendOnlySupportersHaveAbility(player, "create decorative lightning")
				return@onCommand
			}
			logger.info("${player.name} struck decorative lightning at ${player.location}")
			player.world.strikeLightningEffect(player.location)
		},
		CommonTabCompletion.EMPTY
	)

}