/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.bukkitapiextension.event.playerscoopentity

import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.sucraft.bukkitapiextension.event.EventDispatcher
import org.sucraft.core.common.bukkit.scheduler.RunInFuture
import java.util.*


object PlayerScoopEntityEventDispatcher : EventDispatcher<PlayerScoopEntityEvent>() {

	// Settings

	private val entityScoopMaterialByEntityType: MutableMap<EntityType, Material> = EnumMap(EntityType::class.java)

	init {
		entityScoopMaterialByEntityType[EntityType.AXOLOTL] = Material.AXOLOTL_BUCKET
		entityScoopMaterialByEntityType[EntityType.COD] = Material.COD_BUCKET
		entityScoopMaterialByEntityType[EntityType.PUFFERFISH] = Material.PUFFERFISH_BUCKET
		entityScoopMaterialByEntityType[EntityType.SALMON] = Material.SALMON_BUCKET
		entityScoopMaterialByEntityType[EntityType.TROPICAL_FISH] = Material.TROPICAL_FISH_BUCKET
	}

	private val itemTypesUsableToScoop = arrayOf(Material.BUCKET, Material.WATER_BUCKET)

	// Events

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onPlayerInteractEntity(event: PlayerInteractEntityEvent) {
		val expectedResultingItemType: Material = entityScoopMaterialByEntityType[event.rightClicked.type] ?: return
		val usedItem = event.player.inventory.getItem(event.hand) ?: return
		if (usedItem.type in itemTypesUsableToScoop) {
			RunInFuture.useInventoryDifference(plugin, event.player, forInventoryDifference@{ _, inventoryDifference ->
				for ((itemStack, amountChange) in inventoryDifference) {
					if (itemStack.type == expectedResultingItemType && amountChange > 0) {
						fire(PlayerScoopEntityEvent(event, itemStack))
						return@forInventoryDifference null
					}
				}
			}, preservePredicate = { it.type == expectedResultingItemType} )
		}
	}

}