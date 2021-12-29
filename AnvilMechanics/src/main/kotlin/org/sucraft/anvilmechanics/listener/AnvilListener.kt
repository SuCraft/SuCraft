/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.anvilmechanics.listener

import com.destroystokyo.paper.event.block.AnvilDamagedEvent
import org.bukkit.Bukkit
import org.bukkit.ChatColor.GRAY
import org.bukkit.ChatColor.WHITE
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.AnvilInventory
import org.bukkit.inventory.BlockInventoryHolder
import org.sucraft.anvilmechanics.main.SuCraftAnvilMechanicsPlugin
import org.sucraft.core.common.bukkit.block.BlockCoordinates
import org.sucraft.core.common.bukkit.item.EmptyItemStack
import org.sucraft.core.common.bukkit.scheduler.RunInFuture
import org.sucraft.core.common.sucraft.player.PlayerUUID
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import java.util.*


object AnvilListener : SuCraftComponent<SuCraftAnvilMechanicsPlugin>(SuCraftAnvilMechanicsPlugin.getInstance()) {

	// Settings

	/**
	 * The maximum repair cost that anvils support (just a technicality, set higher than reasonably could occur)
	 */
	private const val maximumRepairCost = 10000

	/**
	 * Repairs will never cost more than this
	 * To disable, set to an empty value
	 */
	private val repairCostCap: OptionalInt = OptionalInt.of(60)

	/**
	 * The level cost of renaming (cannot be 0 due to the server using 0 as a special value for a repair being impossible)
	 */
	private const val costToOnlyRename = 1

	/**
	 * Whether ty try to refund the level rename cost (since it cannot be 0, but we may want it to not cost any levels just to rename items)
	 */
	private const val tryToRefundRenameCost = true

	/**
	 * Interval in millis between messages that tell the player the level cost of repairs that are too high to display (the client refuses to display them in survival mode)
	 */
	private const val timeInMillisBetweenHighLevelCostMessages = 2000L // 2 seconds

	/**
	 * Protect the anvil from damage for this time after doing a rename operation, to prevent anvil from breaking by renaming
	 * TODO: This is not working: perhaps the damage event happens before the rename operation?
	 */
	private const val anvilCantBreakAfterRenameIntervalInTicks = 2L

	// Data

	private var lastHighLevelCostMessageTimestamps: MutableMap<Pair<PlayerUUID, Int>, Long> = HashMap(50)
	private val anvilsThatCantBreak: MutableList<BlockCoordinates> = ArrayList(5)

	// Events

	/**
	 * Handle clicks on anvil inventories to update information and prepare for modifications to vanilla behavior
	 */
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onInventoryClickEvent(event: InventoryClickEvent) {

		val player = event.whoClicked as? Player ?: return
		val inventory = event.inventory
		if (inventory is AnvilInventory) {

			// Update the anvil repair cost
			updateAnvilInventoryRepairCostAndSendHighCostMessageToPlayer(player, inventory)

			// After this check we know that the result slot was clicked, so this may be a repair action
			if (event.slotType != InventoryType.SlotType.RESULT) return
			updateAnvilRenameOrRepairCost(inventory)

			// This is for repairs that only rename an item
			run modifyRenames@{

				// Check that the result slot wasn't empty
				val result = inventory.result ?: return@modifyRenames
				if (result.type == Material.AIR) return@modifyRenames
				// Check that the second item slot is empty
				val secondItem = inventory.secondItem
				if (EmptyItemStack.isNotEmpty(secondItem)) return@modifyRenames

				// Store the old level
				val oldLevel = player.level
				// Prepare for preventing the anvil being damaged
				if (inventory.holder is BlockInventoryHolder) {
					val anvilBlockCoordinates: BlockCoordinates = BlockCoordinates.get((inventory.holder as BlockInventoryHolder).block)
					anvilsThatCantBreak.add(anvilBlockCoordinates)
					Bukkit.getScheduler().runTaskLater(plugin, Runnable { anvilsThatCantBreak.remove(anvilBlockCoordinates) }, anvilCantBreakAfterRenameIntervalInTicks)
				}
				// Schedule refunding the repair cost
				if (tryToRefundRenameCost) RunInFuture.forPlayerIfOnline(plugin, player, refundRenameCost@{
					try {
						// Check that the inventory instance is still valid by checking if it has viewers
						if (inventory.viewers.isEmpty()) return@refundRenameCost null
						// Check that the result slot is now empty
						val newResult = inventory.result
						if (EmptyItemStack.isNotEmpty(newResult)) return@refundRenameCost null
						// Check that the player's level changed correctly
						val newLevel = player.level
						if (newLevel != oldLevel - costToOnlyRename) return@refundRenameCost null
						// Reset the player's level to their old level
						player.level = oldLevel
					} catch (_: Exception) {
					}
				})

			}

		} else {

			// If this click was not in an anvil inventory, but the clicking player has an anvil inventory open (for example, they clicked in their bottom inventory), update the repair cost in the top inventory
			(player.openInventory.topInventory as? AnvilInventory)?.let { updateAnvilInventoryRepairCostAndSendHighCostMessageToPlayer(player, it) }

		}

	}

	/**
	 * Prevent damage to anvils
	 */
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	fun onAnvilDamaged(event: AnvilDamagedEvent) {
		// TODO: Currently cancelling any damage to anvils, since cancelling damage to anvils in the case of being rename isn't working
		event.isCancelled = true
		// Cancel damage to anvils that have just been used in a rename operation
		val anvilBlockCoordinates = (event.inventory.holder as? BlockInventoryHolder)?.block?.let(BlockCoordinates::get) ?: return
		if (anvilBlockCoordinates in anvilsThatCantBreak) event.isCancelled = true
	}

	/**
	 * Handle clicks on anvil inventories to update information
	 */
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onPrepareAnvil(event: PrepareAnvilEvent) {
		updateAnvilInventoryRepairCostAndSendHighCostMessageToPlayer(event.inventory)
	}

	/**
	 * Update an anvil inventory for all viewing players
	 */
	private fun updateAnvilInventoryRepairCostAndSendHighCostMessageToPlayer(inventory: AnvilInventory) {
		for (viewer in inventory.viewers) {
			if (viewer is Player) {
				updateAnvilInventoryRepairCostAndSendHighCostMessageToPlayer(viewer, inventory)
			}
		}
	}

	/**
	 * Update an anvil inventory for a specific viewing player
	 */
	private fun updateAnvilInventoryRepairCostAndSendHighCostMessageToPlayer(player: Player, inventory: AnvilInventory) {
		// Limit the maximum repair cost
		inventory.maximumRepairCost = maximumRepairCost
		// Wait a tick to see what changes the server makes to the inventory
		RunInFuture.forPlayerIfOnline(plugin, player, aTickLater@{
			try {
				if (inventory.viewers.isEmpty()) return@aTickLater null
				// Update the anvil repair cost
				updateAnvilRenameOrRepairCost(inventory)
				// Send a high cost to the player if necessary
				val result = inventory.result
				if (EmptyItemStack.isNotEmpty(result))
					if (inventory.repairCost >= 40)
						sendHighLevelCostToPlayer(it, inventory.repairCost)
				null
			} catch (_: Exception) {
			}
		})
	}

	/**
	 * Update an anvil's repair cost
	 */
	private fun updateAnvilRenameOrRepairCost(inventory: AnvilInventory) {
		val result = inventory.result ?: return
		if (result.type == Material.AIR) return
		val secondItem = inventory.secondItem
		// In case the second item is null, we know it is a repair operation
		if (EmptyItemStack.isEmpty(secondItem))
			if (inventory.repairCost > costToOnlyRename) inventory.repairCost = costToOnlyRename
		// Update the anvil's repair cost cap to remove the limit
		if (repairCostCap.isPresent && inventory.repairCost > repairCostCap.asInt)
			inventory.repairCost = repairCostCap.asInt
	}

	private fun sendHighLevelCostToPlayer(player: Player, cost: Int) {
		val playerAndCost: Pair<PlayerUUID, Int> = Pair(PlayerUUID.get(player), cost)
		val lastTimestamp = lastHighLevelCostMessageTimestamps[playerAndCost]
		// Return if the last similar message was too recent
		if (lastTimestamp != null && lastTimestamp + timeInMillisBetweenHighLevelCostMessages >= System.currentTimeMillis()) return
		// Store the timestamp to make sure we don't send too many repeated messages too quickly
		lastHighLevelCostMessageTimestamps[playerAndCost] = System.currentTimeMillis()
		// Send the message
		player.sendMessage("${GRAY}You tried to upgrade an item for a cost that was too expensive." +
				"The actual cost is ${WHITE}${cost}${GRAY} levels." +
				"It's not possible to display that (the \"Too Expensive!\" message is client-side)," +
				"but you can still do it if you want to: if you have enough levels and take the item from the result slot it will work.")
	}

	/**
	 * Clean stored data
	 */
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	fun onPlayerQuit(event: PlayerQuitEvent) {
		val playerUUID = PlayerUUID.get(event.player)
		with (lastHighLevelCostMessageTimestamps.iterator()) {
			forEach { if (it.key.first == playerUUID) remove() }
		}
	}

}