/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.dropminecartsonleave

import org.bukkit.GameMode.CREATIVE
import org.bukkit.Material.MINECART
import org.bukkit.entity.Minecart
import org.bukkit.entity.Player
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.event.vehicle.VehicleExitEvent
import org.bukkit.inventory.ItemStack
import org.sucraft.common.event.on
import org.sucraft.common.location.dropItem
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.scheduler.runNextTickEvenIfOffline
import java.util.*

/**
 * Makes minecarts automatically drop if players leave them in survival mode,
 * or despawn if players leave them in creative mode.
 */
object DropMinecartsOnLeave : SuCraftModule<DropMinecartsOnLeave>() {

	// Internal data

	/**
	 * A [Player]'s [UUID][Player.getUniqueId] is stored in this set between exiting the vehicle
	 * and the action executed the next tick that amongst others potentially breaks the minecart and teleports the
	 * player to our own desired target location.
	 *
	 * This helps us prevent the normal sideways movement applied to the player when leaving a minecart.
	 */
	private val playersLeavingMinecart: MutableSet<UUID> = HashSet(1)

	// Events

	init {

		// Handle players leaving a minecart to drop and remove it
		on(VehicleExitEvent::class) {
			val player = exited as? Player ?: return@on
			val minecart = vehicle as? Minecart ?: return@on
			// Make sure we don't do anything if the player is being made to exit the minecart automatically
			// due to a teleportation of a player in a minecart being executed
			if (minecart.isInvulnerable) return@on
			// Take a location that is slightly over half a block higher,
			// since the player's location is much lower while in a minecart
			val targetLocationForItemAndPlayer = player.location.clone().add(0.0, 0.51, 0.0)
			val playerUUID = player.uniqueId
			playersLeavingMinecart.add(playerUUID)
			player.runNextTickEvenIfOffline {
				playersLeavingMinecart.remove(playerUUID)
				if (this == null) return@runNextTickEvenIfOffline
				// Do the same invulnerability check as above, just in case a teleportation started in the meantime
				if (minecart.isInvulnerable) return@runNextTickEvenIfOffline
				// If the player's game mode is not creative, drop the minecart as an item
				if (gameMode != CREATIVE)
					targetLocationForItemAndPlayer.dropItem(ItemStack(MINECART)) {
						// Items are normally dropped with a random velocity, but we don't want it to move horizontally
						it.velocity = it.velocity.setX(0).setZ(0)
					}
				// Remove the minecart
				if (!minecart.isDead)
					minecart.remove()
				// Teleport the player to the target location
				teleport(targetLocationForItemAndPlayer)
			}
		}

		// Handle players teleporting to cancel the normal sideways movement applied to players when leaving a minecart
		on(PlayerTeleportEvent::class, EventPriority.LOWEST) {
			if (player.uniqueId in playersLeavingMinecart)
				isCancelled = true
		}

	}

}