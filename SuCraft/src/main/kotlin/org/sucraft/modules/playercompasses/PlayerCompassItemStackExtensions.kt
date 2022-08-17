/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.playercompasses

import org.bukkit.Material.COMPASS
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.sucraft.common.function.takeThisIf
import org.sucraft.common.itemstack.meta.customModelData
import org.sucraft.common.namespacedkey.toSuCraftNamespacedKey
import org.sucraft.common.persistentdata.removePersistent
import org.sucraft.common.persistentdata.typedvalueextensions.getPersistentUUID
import org.sucraft.common.persistentdata.typedvalueextensions.setPersistentUUID
import org.sucraft.common.player.getOnlinePlayer
import java.util.*

private val playerCompassTrackedUUIDKey by lazy {
	"player_compass_tracked_player".toSuCraftNamespacedKey()
}
private const val playerCompassCustomModelData = 1

private val oldPlayerCompassTrackedUUIDKey by lazy {
	@Suppress("DEPRECATION")
	NamespacedKey("martijnsextrafeatures", "compass_tracked_player")
}

/**
 * Provides some [ItemStack] extensions related to player compasses to any class that implements this interface.
 */
interface PlayerCompassItemStackManipulation {

	/**
	 * Whether this item stack is a player compass.
	 */
	val ItemStack?.isPlayerCompass get() = playerCompassTrackedUUID != null

	/**
	 * The [UUID] of the player that is tracked by this item stack as a player compass,
	 * or null if this item stack is not a player compass.
	 */
	val ItemStack?.playerCompassTrackedUUID
		get() = this?.takeThisIf { type == COMPASS }?.run {
			getPersistentUUID(playerCompassTrackedUUIDKey) ?: getPersistentUUID(oldPlayerCompassTrackedUUIDKey)
		}

	/**
	 * The online [Player] that is tracked by this item stack as a player compass,
	 * or null if this item stack is not a player compass, or if the player tracked by this item stack
	 * as a player compass is not online.
	 */
	val ItemStack?.playerCompassTrackedPlayer
		get() = playerCompassTrackedUUID?.getOnlinePlayer()

	fun ItemStack.setPlayerCompassTrackedPlayer(player: Player) =
		setPlayerCompassTrackedUUID(player.uniqueId)

	fun ItemStack.setPlayerCompassTrackedUUID(uuid: UUID) =
		removePersistent(oldPlayerCompassTrackedUUIDKey)
			.setPersistentUUID(playerCompassTrackedUUIDKey, uuid)
			.apply { customModelData = playerCompassCustomModelData }

}