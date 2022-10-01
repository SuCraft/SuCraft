/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.moremobheads

import org.bukkit.Material
import org.bukkit.entity.*
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.entity.EntityType.*
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent.DamageCause.*
import org.bukkit.event.entity.ItemSpawnEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import org.sucraft.common.event.on
import org.sucraft.common.itemstack.createPlayerHead
import org.sucraft.common.itemstack.material.isGroundOrWallHead
import org.sucraft.common.itemstack.meta.customModelData
import org.sucraft.common.itemstack.meta.displayName
import org.sucraft.common.itemstack.meta.runMetaOrNull
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.namespacedkey.toSuCraftNamespacedKey
import org.sucraft.common.persistentdata.setPersistentFlag
import org.sucraft.common.scheduler.runNextTick
import org.sucraft.common.text.unstyledText
import java.util.*
import kotlin.collections.HashSet

/**
 * Drops player heads with a skin of a mob, when a mob that normally would not drop a mob head
 * dies from a charged creeper explosion.
 */
object MoreMobHeads : SuCraftModule<MoreMobHeads>() {

	// Settings

	private val customMobHeadKey by lazy {
		"custom_mob_head".toSuCraftNamespacedKey()
	}

	/**
	 * Whether to drop a dragon head when the ender dragon gets killed by a charged creeper explosion.
	 */
	private const val dropDragonHead = true

	// Data

	/**
	 * Whether a custom mob head was dropped this tick for the creeper entity UUID - this makes sure there cannot be
	 * multiple custom mob heads that are dropped from a single charged creeper explosion.
	 * This limit exists in vanilla.
	 */
	private
	val mobHeadsDroppedThisTickCreeperUUIDs: MutableSet<UUID> = HashSet(0)

	// Events

	init {

		// Listen for entities dying to drop additional mob heads if the death was caused by a charged creeper
		on(EntityDeathEvent::class) {

			// Make sure the damage was done by a charged creeper explosion
			val lastDamageCause = entity.lastDamageCause as? EntityDamageByEntityEvent ?: return@on
			if (lastDamageCause.cause != ENTITY_EXPLOSION) return@on
			val creeperDamager = lastDamageCause.damager as? Creeper ?: return@on
			if (!creeperDamager.isPowered) return@on

			// Ignore if we already dropped a mob head this tick
			if (creeperDamager.uniqueId in mobHeadsDroppedThisTickCreeperUUIDs) return@on

			// Ignore entity types that always drop a mob head
			// (Note that the ender dragon does not drop in vanilla a head when killed by a charged creeper,
			// so we do not ignore it - we make it drop its existing head block instead)
			when (entityType) {
				CREEPER, SKELETON, WITHER_SKELETON, ZOMBIE -> run vanillaMobHeadDrop@{
					// Also we quickly check here if maybe one of them already drops a head this tick
					// (because of event iteration order it may be that in one tick, both a custom,
					// as well as some vanilla mob heads (which can be one per explosion) drop)
					if (drops.any { it.type.isGroundOrWallHead }) {
						// Special case: for creepers that are charged, we cancel the normal drop, so that we can
						// add our own replacement
						if ((entity as? Creeper)?.isPowered == true) {
							// Remove the planned drop
							drops.removeIf { it.type.isGroundOrWallHead }
							return@vanillaMobHeadDrop
						}
						setMobHeadWasDroppedThisTick(creeperDamager.uniqueId)
					}
					return@on
				}

				else -> {}
			}

			// Special case: if this is an ender dragon, we drop a dragon head
			// (Killing an ender dragon with a charged creeper is quite an effort compared to
			// the usual method of obtaining dragon heads, though)
			if (entityType == ENDER_DRAGON) {

				if (dropDragonHead) {
					// Add the dragon head to the drops
					drops.add(ItemStack(Material.DRAGON_HEAD))
				}

			} else {

				// Determine the player head to drop (if any)
				AddedMobHeads.dropsPerEntityType[entityType]?.run { castAndGetDrop(entity) }?.let { drop ->
					// Add the textured head to the drops
					drops.add(createPlayerHead(drop.uuid, drop.textures))
				}

			}

			// Do not drop another mob head this tick
			setMobHeadWasDroppedThisTick(creeperDamager.uniqueId)

		}

		// Listen to item drops in the world to set the correct display name and custom model data
		// (because they are not stored when a head is placed in the world)
		on(ItemSpawnEvent::class) {

			// Get the owner UUID of the item, if it is a head
			val skullOwnerUUID = entity.itemStack.runMetaOrNull(SkullMeta::class) {
				playerProfile?.run { id } ?: owningPlayer?.run { uniqueId }
			} ?: return@on // If null, the item is not a head or has no owner

			// Get the drop associated with this owner UUID
			val drop = AddedMobHeads.dropsPerSkullOwnerUUID[skullOwnerUUID]
				?: return@on // Otherwise, the item is not a custom mob head

			// Apply the custom display name and custom model data
			entity.itemStack = entity.itemStack.clone().apply {
				displayName(unstyledText(drop.displayName))
				customModelData = drop.customModelData
				setPersistentFlag(customMobHeadKey)
			}

		}

	}

	// Implementation

	private fun setMobHeadWasDroppedThisTick(creeperUUID: UUID) {
		if (mobHeadsDroppedThisTickCreeperUUIDs.add(creeperUUID)) {
			runNextTick {
				mobHeadsDroppedThisTickCreeperUUIDs.remove(creeperUUID)
			}
		}
	}

}