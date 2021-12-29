/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.mysteryboxes.data

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.ShulkerBox
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.sucraft.core.common.bukkit.item.ItemStackBuilder
import org.sucraft.core.common.bukkit.material.MaterialGroups
import org.sucraft.core.common.sucraft.delegate.StandardItemStackNames


@Suppress("MemberVisibilityCanBePrivate")
object MysteryBoxData {

	fun isMysteryBox(itemStack: ItemStack?): Boolean {
		if (itemStack == null) return false
		if (itemStack.type !in MaterialGroups.shulkerBox) return false
		// TODO improve check
		// Check for the flag that hides the items
		return ItemStackBuilder.create(itemStack).hasItemFlag(ItemFlag.HIDE_POTION_EFFECTS)
	}

	fun makeMysteryBox(itemStack: ItemStack): ItemStack {
		require(itemStack.type in MaterialGroups.shulkerBox) { "itemStack is not a shulker box" }
		val builder = ItemStackBuilder.create(itemStack)
		// Add the flag that hides the items
		builder.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS)
		// Set a display name if none is present yet
		if (!builder.hasDisplayName()) {
			builder.setDisplayNameComponent(getMysteryBoxDisplayName(itemStack.type))
		}
		// Overwrite the lore
		builder.setLoreComponent(
			lore = arrayOf(
				Component.text("☘ The contents of this Shulker Box").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
				Component.text("are a surprise!").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)
			)
		)
		return builder.build()
	}

	fun getMysteryBoxDisplayName(type: Material) = Component.text(StandardItemStackNames.get().get(type).replace("Shulker", "Mystery")).color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)

	fun isMysteryBoxDisplayName(type: Material, name: Component) = name == getMysteryBoxDisplayName(type)

	fun isMysteryBox(block: Block): Boolean {
		if (block.type !in MaterialGroups.shulkerBox) return false
		val shulkerBox = (block.state as? ShulkerBox) ?: return false
		val customName = shulkerBox.customName() ?: return false
		return isMysteryBoxDisplayName(block.type, customName)
	}

	fun fixMysteryBoxTitleIfNecessary(block: Block) {
		if (!isMysteryBox(block)) return
		val shulkerBox = block.state as ShulkerBox
		shulkerBox.customName(null)
		shulkerBox.update()
	}

}