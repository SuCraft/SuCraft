/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.inventory

import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack

/**
 * Updates each [item stack][ItemStack] in this inventory.
 * @param function The function to apply to each item stack.
 * @return Whether the contents of this inventory were changed.
 */
fun Inventory.updateContents(function: (ItemStack?) -> ItemStack?): Boolean {
	val contents = this.contents
	var contentsChanged = false
	contents.withIndex().forEach { (index, itemStack) ->
		val newItemStack = function(itemStack)
		// Optimization: first check for referential equality
		if (newItemStack === itemStack) return@forEach
		if (newItemStack == itemStack) return@forEach
		contents[index] = newItemStack
		contentsChanged = true
	}
	if (contentsChanged) this.contents = contents
	return contentsChanged
}

/**
 * Updates the [ItemStack] on the [cursor][InventoryView.getCursor] of this inventory view.
 * @param function The function to apply to the cursor.
 * @return Whether the cursor was changed.
 */
fun InventoryView.updateCursor(function: (ItemStack?) -> ItemStack?): Boolean {
	val newCursor = function(cursor)
	// Optimization: first check for referential equality
	if (newCursor === cursor) return false
	if (newCursor == cursor) return false
	cursor = newCursor
	return true
}

/**
 * Updates the [ItemStack] on the [cursor][Player.getItemOnCursor] of this player.
 * @param function The function to apply to the cursor.
 * @return Whether the cursor was changed.
 */
fun Player.updateCursor(function: (ItemStack) -> ItemStack?): Boolean {
	val newCursor = function(itemOnCursor)
	// Optimization: first check for referential equality
	if (newCursor === itemOnCursor) return false
	if (newCursor == itemOnCursor) return false
	setItemOnCursor(newCursor)
	return true
}

/**
 * Updates each [ItemStack] in the [top inventory][InventoryView.getTopInventory],
 * [bottom inventory][InventoryView.getBottomInventory] and
 * [cursor][InventoryView.getCursor] of this inventory view.
 * @param function The function to apply to each item stack.
 * @return Whether the contents of the top inventory, bottom inventory or cursor were changed.
 */
fun InventoryView.updateAllContents(function: (ItemStack?) -> ItemStack?) =
	topInventory.updateContents(function) or bottomInventory.updateContents(function) or updateCursor(function)

/**
 * Updates each [ItemStack] in the [inventory][Player.getInventory] and
 * [cursor][Player.getItemOnCursor] of this player.
 * @param function The function to apply to each item stack.
 * @return Whether the contents of the inventory or cursor were changed.
 */
fun Player.updateInventoryAndCursor(function: (ItemStack?) -> ItemStack?) =
	inventory.updateContents(function) or updateCursor(function)