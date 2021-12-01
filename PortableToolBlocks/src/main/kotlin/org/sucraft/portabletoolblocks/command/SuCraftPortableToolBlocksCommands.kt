/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.portabletoolblocks.command

import org.bukkit.entity.Player
import org.sucraft.core.common.bukkit.command.CommonTabCompletion
import org.sucraft.core.common.sucraft.command.SuCraftCommand
import org.sucraft.core.common.sucraft.command.SuCraftCommands
import org.sucraft.core.common.sucraft.player.PlayerByInputString
import org.sucraft.portabletoolblocks.main.SuCraftPortableToolBlocksPlugin
import org.sucraft.portabletoolblocks.player.permission.SuCraftPortableToolBlocksPermissions


object SuCraftPortableToolBlocksCommands : SuCraftCommands<SuCraftPortableToolBlocksPlugin>(SuCraftPortableToolBlocksPlugin.getInstance()) {

	val CRAFTING_TABLE = SuCraftCommand.createPlayerOnly(
		this,
		"craftingtable",
		{ player, _, _, _ ->
			logger.info("${player.name} opened the portable crafting table");
			player.openWorkbench(null, true);
		},
		CommonTabCompletion.EMPTY
	)

	val ENDER_CHEST = SuCraftCommand.createPlayerOnly(
		this,
		"enderchest",
		onCommand@{ player, _, _, arguments ->
			val enderChestOwner: Player =
				if (!player.hasPermission(SuCraftPortableToolBlocksPermissions.ENDER_CHEST_OF_OTHER_PLAYERS) || arguments.isEmpty())
					player
				else
					PlayerByInputString.getOnlineOrSendErrorMessage(arguments[0], player) ?: return@onCommand
			if (enderChestOwner == player)
				logger.info("${player.name} opened the portable ender chest")
			else
				logger.info("${player.name} opened the ender chest of ${enderChestOwner.name}")
			player.openInventory(enderChestOwner.enderChest)
		},
		CommonTabCompletion.EMPTY
	)

}