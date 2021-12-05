/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.playercompass.command

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.JoinConfiguration
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.sucraft.core.common.bukkit.command.CommonTabCompletion
import org.sucraft.core.common.bukkit.item.GuaranteedItemMetaGetter
import org.sucraft.core.common.bukkit.persistentdata.PersistentDataShortcuts
import org.sucraft.core.common.sucraft.command.SuCraftCommand
import org.sucraft.core.common.sucraft.command.SuCraftCommands
import org.sucraft.core.common.sucraft.player.PlayerUUID
import org.sucraft.playercompass.main.SuCraftPlayerCompassPlugin
import org.sucraft.playercompass.player.permission.SuCraftPlayerCompassPermissions
import org.sucraft.playercompass.tracker.PlayerCompassTracker
import org.sucraft.supporters.chat.SupportingMessages


object SuCraftPlayerCompassCommands : SuCraftCommands<SuCraftPlayerCompassPlugin>(SuCraftPlayerCompassPlugin.getInstance()) {

	val CREATE_OWN_COMPASS = SuCraftCommand.createPlayerOnly(
		this,
		"playercompass",
		onCommand@{ player, _, _, _ ->
			if (!player.hasPermission(SuCraftPlayerCompassPermissions.CREATE_OWN_COMPASS)) {
				SupportingMessages.sendOnlySupportersHaveAbility(player, "create compasses that always point to them (even when you give it to someone else)")
				return@onCommand
			}
			// Make sure the player is holding a compass
			val itemStack = player.inventory.itemInMainHand
			if (itemStack.type != Material.COMPASS) {
				player.sendMessage(Component.text("You must be holding a compass to make it a player compass!").color(NamedTextColor.WHITE))
				return@onCommand
			}
			// Make sure the compass the player is holding is not already a player compass
			if (PlayerCompassTracker.getTrackedUUID(itemStack) != null) {
				player.sendMessage(Component.text("You are already holding a player compass.").color(NamedTextColor.WHITE))
				return@onCommand
			}
			// Set the compass to track this player
			val itemMeta = GuaranteedItemMetaGetter.get(itemStack)
			PersistentDataShortcuts.PlayerUUID[itemMeta, PlayerCompassTracker.playerCompassTrackedPlayerPersistentDataNamespacedKey] = PlayerUUID.get(player)
			// Update the compass display name, if it does not already have one
			if (!itemMeta.hasDisplayName()) {
				itemMeta.displayName(
					Component.join(
						JoinConfiguration.noSeparators(),
						Component.text("Compass for "),
						Component.text(player.name).color(NamedTextColor.GOLD)
					).color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)
				)
			}
			// Update the lore
			val lore: MutableList<Component?> = itemMeta.lore() ?: ArrayList(2)
			lore.add(
				Component.join(
					JoinConfiguration.noSeparators(),
					Component.text("This player compass points to "),
					Component.text(player.name).color(TextColor.fromCSSHexString("#d6d6d6"))
				).color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)
			)
			lore.add(
				Component.text("when they are in the same world!").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)
			)
			itemMeta.lore(lore)
			itemStack.itemMeta = itemMeta
			// Update the compass direction right away
			PlayerCompassTracker.possiblyUpdateCompass(player.location, itemStack, player)
			player.updateInventory()
			logger.info("${player.name} created a player-tracking compass: $itemStack")
			player.sendMessage(
				Component.join(
					JoinConfiguration.noSeparators(),
					Component.text("You created a "),
					Component.text("player compass").color(NamedTextColor.WHITE),
					Component.text(" that always points to you!")
				).color(NamedTextColor.GRAY)
			)
		},
		CommonTabCompletion.EMPTY
	)

}