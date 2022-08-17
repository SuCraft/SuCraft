/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.anvilmechanics

import com.destroystokyo.paper.event.block.AnvilDamagedEvent
import org.bukkit.GameMode
import org.bukkit.GameMode.CREATIVE
import org.bukkit.GameMode.SPECTATOR
import org.bukkit.entity.Player
import org.bukkit.event.EventPriority
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryType.SlotType.RESULT
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.AnvilInventory
import org.sucraft.common.block.BlockCoordinates
import org.sucraft.common.event.on
import org.sucraft.common.inventory.anvil.vanillaMaxSurvivalModeAnvilRepairCost
import org.sucraft.common.inventory.blockCoordinates
import org.sucraft.common.itemstack.isEmpty
import org.sucraft.common.itemstack.isNotEmpty
import org.sucraft.common.itemstack.takeAsNotEmpty
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.player.gamemode.sendCurrentGameModeChangePacket
import org.sucraft.common.player.gamemode.sendGameModeChangePacket
import org.sucraft.common.scheduler.runNextTick
import org.sucraft.modules.anvilmechanics.AnvilMechanics.costToOnlyRename
import org.sucraft.modules.anvilmechanics.AnvilMechanics.repairCostCap
import org.sucraft.modules.anvilmechanics.AnvilMechanics.updateFakeGameMode
import java.util.*

/**
 * Increases the maximum anvil level cost for which the anvil can be used.
 * Caps the anvil level cost to never be higher than a [set maximum][repairCostCap].
 * Makes renaming free in terms of level cost.
 * TODO Bug: renaming requires the player to have at least [one level][costToOnlyRename].
 * Makes renaming free in terms of anvil damage.
 */
object AnvilMechanics : SuCraftModule<AnvilMechanics>() {

	// Settings

	/**
	 * The maximum repair cost that anvils support
	 * (just a technicality, set higher than reasonably could occur).
	 */
	private const val maximumRepairCost = 10000

	/**
	 * Repairs will never cost more than this.
	 * To disable, set to null.
	 */
	@Suppress("RedundantNullableReturnType")
	private val repairCostCap: Int? = 69

	/**
	 * The level cost of renaming
	 * (cannot be 0 due to the server using 0 as a special value for a repair being impossible).
	 */
	private const val costToOnlyRename = 1

	/**
	 * Whether to try to refund the level rename cost
	 * (since it cannot be 0, but we may want it to not cost any levels just to rename items).
	 */
	private const val tryToRefundRenameCost = true

	/**
	 * Whether to prevent anvils from breaking from rename actions.
	 */
	private const val preventAnvilsFromBreakingAfterRenames = true

	// Data

	private var playersPotentiallyUnderMistakenGameMode: MutableSet<UUID> = HashSet(10)
	private val anvilsThatCantBreak: MutableSet<BlockCoordinates> = HashSet(64)
	private val toBeRemovedFromAnvilsThatCantBreakNextTick: MutableSet<BlockCoordinates> = HashSet(1)

	// Implementation

	/**
	 * Executes both [updateActionCost] and [updateFakeGameModesOfViewers].
	 */
	private fun AnvilInventory.updateActionCostAndFakeGameModesOfViewers() {
		updateActionCost()
		updateFakeGameModesOfViewers()
	}

	/**
	 * Executes both [updateActionCost] and [updateFakeGameModesOfViewers]
	 * in the next tick.
	 */
	private fun AnvilInventory.updateActionCostAndFakeGameModesOfViewersInNextTick() =
		runNextTick {
			updateActionCostAndFakeGameModesOfViewers()
		}

	/**
	 * Updates this anvil's repair cost:
	 * - The cost is capped by the [repairCostCap].
	 * - The cost is reduced to [costToOnlyRename] if the planned action is only to rename.
	 */
	private fun AnvilInventory.updateActionCost() {
		// Limit the maximum repair cost of the anvil
		maximumRepairCost = AnvilMechanics.maximumRepairCost
		// Don't do anything in regard to the current action cost if the result slot is empty
		if (result.isEmpty) return
		val secondItem = secondItem?.takeAsNotEmpty()
		// In case the second item is empty, we know it is a rename action
		if (secondItem == null)
			if (repairCost > costToOnlyRename)
				repairCost = costToOnlyRename
		// Cap the current cost to a maximum
		if (repairCostCap != null && repairCost > repairCostCap)
			repairCost = repairCostCap
	}

	/**
	 * Potentially send a [fake game mode][updateFakeGameMode] to the viewing players
	 * of this inventory.
	 */
	private fun AnvilInventory.updateFakeGameModesOfViewers() =
		viewers
			.mapNotNull { it as? Player }
			.forEach { it.updateFakeGameMode(this) }

	/**
	 * Updates the [GameMode] this player thinks they have
	 * (but not the actual game mode)
	 * according to the state of the anvil inventory.
	 */
	private fun Player.updateFakeGameMode(inventory: AnvilInventory) =
		updateFakeGameMode(
			when (gameMode) {
				// No fake mode is necessary if the player is already in creative mode
				CREATIVE -> false
				// The player can't be using the inventory in spectator mode
				SPECTATOR -> false
				// If the player can perform this repair in vanilla, no fake creative mode is needed
				else -> inventory.repairCost > vanillaMaxSurvivalModeAnvilRepairCost
			}
		)

	/**
	 * Updates the [GameMode] this player thinks they have
	 * (but not the actual game mode)
	 * according to whether this player should be in a fake creative mode.
	 */
	private fun Player.updateFakeGameMode(shouldBeInFakeCreativeGameMode: Boolean) {
		if (shouldBeInFakeCreativeGameMode && playersPotentiallyUnderMistakenGameMode.add(uniqueId))
			sendGameModeChangePacket(CREATIVE)
		else if (!shouldBeInFakeCreativeGameMode && playersPotentiallyUnderMistakenGameMode.remove(uniqueId))
			sendCurrentGameModeChangePacket()
	}

	// Events

	init {

		// Remove fake game modes when players close an anvil inventory
		on(InventoryCloseEvent::class) {
			(player as? Player)?.updateFakeGameMode(false)
		}

		// Handle clicks on anvil inventories to update the anvil and player state,
		// and prepare to refund the cost of renaming
		on(InventoryClickEvent::class, EventPriority.HIGHEST) {

			val player = whoClicked as? Player
				?: return@on
			val inventory = inventory as? AnvilInventory
				?: player.openInventory.topInventory as? AnvilInventory
				?: return@on

			// Update the anvil repair cost
			inventory.updateActionCost()
			// Potentially send a fake game mode to the player
			player.updateFakeGameMode(inventory)

			// The next tick, run the same updates again in case something changed in the meantime due to this event
			inventory.updateActionCostAndFakeGameModesOfViewersInNextTick()

		}

		// Handle clicks on anvil inventories to prepare to refund the cost of renaming
		on(InventoryClickEvent::class) {

			val player = whoClicked as? Player
				?: return@on
			val inventory = inventory as? AnvilInventory
				?: player.openInventory.topInventory as? AnvilInventory
				?: return@on

			// After this check we know that the result slot was clicked, so this may be an action execution
			if (slotType != RESULT) return@on

			// Prepare to refund levels if this was a rename action
			if (tryToRefundRenameCost) run maybeRefundRenameCost@{

				// Only if the result slot is not empty
				if (inventory.result.isEmpty) return@maybeRefundRenameCost
				// Only if the second item slot is empty
				if (inventory.secondItem.isNotEmpty) return@maybeRefundRenameCost

				// Store the old level
				val oldLevel = player.level
				// Schedule refunding the repair cost
				player.runNextTick {
					// Make sure that the inventory instance is still valid by checking if it has viewers
					if (inventory.viewers.isEmpty()) return@runNextTick
					// Make sure that the result slot is now empty
					if (inventory.result.isNotEmpty) return@runNextTick
					// Check that the player's level changed correctly
					if (level != oldLevel - costToOnlyRename) return@runNextTick
					// Reset the player's level to their old level
					level = oldLevel
				}

			}

		}

		// Prevent damage to anvils that have just been used in a rename operation
		on(AnvilDamagedEvent::class, EventPriority.LOWEST) {
			if (!preventAnvilsFromBreakingAfterRenames) return@on
			if (inventory.blockCoordinates?.let { it in anvilsThatCantBreak } == true)
				isCancelled = true
		}

		// Handle anvil preparations to update the anvil and player state,
		// and mark anvils as being unable to be broken if needed
		on(PrepareAnvilEvent::class, EventPriority.HIGHEST) {

			// Update the anvil repair cost and fake game modes of the viewing players
			inventory.updateActionCostAndFakeGameModesOfViewers()

			// The next tick, run the same updates again in case something changed in the meantime due to this event
			inventory.updateActionCostAndFakeGameModesOfViewersInNextTick()

		}

		// Handle anvil preparations to mark anvils as being unable to be broken if needed
		on(PrepareAnvilEvent::class) {

			// Mark or unmark the anvil as being able to be broken
			if (preventAnvilsFromBreakingAfterRenames)
				inventory.blockCoordinates?.let {
					// If a rename action was prepared
					if (result.isNotEmpty && inventory.secondItem.isEmpty) {
						anvilsThatCantBreak.add(it)
						toBeRemovedFromAnvilsThatCantBreakNextTick.remove(it)
					} else {
						toBeRemovedFromAnvilsThatCantBreakNextTick.add(it)
						runNextTick {
							if (toBeRemovedFromAnvilsThatCantBreakNextTick.remove(it))
								anvilsThatCantBreak.remove(it)
						}
					}
				}

		}

		// Clear stored data
		on(PlayerQuitEvent::class) {
			playersPotentiallyUnderMistakenGameMode.remove(player.uniqueId)
		}

	}

}