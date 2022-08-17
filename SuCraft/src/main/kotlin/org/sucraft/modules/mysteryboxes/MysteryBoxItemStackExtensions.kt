/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.mysteryboxes

import net.kyori.adventure.text.format.NamedTextColor.GRAY
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag.HIDE_POTION_EFFECTS
import org.bukkit.inventory.ItemStack
import org.sucraft.common.itemstack.material.dyeColor
import org.sucraft.common.itemstack.material.isUncoloredOrColoredShulkerBox
import org.sucraft.common.itemstack.meta.customModelData
import org.sucraft.common.itemstack.meta.runMeta
import org.sucraft.common.namespacedkey.toSuCraftNamespacedKey
import org.sucraft.common.persistentdata.getPersistentFlag
import org.sucraft.common.persistentdata.setPersistentFlag
import org.sucraft.common.text.component
import org.sucraft.common.text.nameSpaceSeparatedCapitalized
import org.sucraft.common.text.nonItalic
import org.sucraft.common.text.unstyledText

private val mysteryBoxKey by lazy {
	"mystery_box".toSuCraftNamespacedKey()
}
private const val mysteryBoxCustomModelData = 1

/**
 * Provides some [ItemStack] extensions related to mystery boxes to any class that implements this interface.
 */
interface MysteryBoxItemStackExtensions {

	val ItemStack?.isMysteryBox
		get() = this != null &&
				type.isUncoloredOrColoredShulkerBox &&
				// Check for the persistent key,
				// or alternatively the flag that hides the items (because older mystery boxes do not have the persistent key)
				(getPersistentFlag(mysteryBoxKey) || hasItemFlag(HIDE_POTION_EFFECTS))

	fun ItemStack.makeIntoMysteryBox(): ItemStack {
		require(type.isUncoloredOrColoredShulkerBox) { "not a shulker box" }
		return clone().apply {
			// Add the flag that hides the items
			addItemFlags(HIDE_POTION_EFFECTS)
			// Add the key that identifies mystery boxes
			setPersistentFlag(mysteryBoxKey)
			// Set the custom model data
			customModelData = mysteryBoxCustomModelData
			// Set a display name if none is present yet
			runMeta {
				if (!hasDisplayName()) {
					displayName(type.displayNameComponentAsMysteryBox)
				}
			}
			// Overwrite the lore
			lore(listOf(
				component(447637098078078657L, GRAY) {
					(+"☘ The contents of this Shulker Box").nonItalic()
				},
				component(446736857009798967L, GRAY) {
					(+"are a surprise!").nonItalic()
				}
			))
		}
	}

	val Material.displayNameAsMysteryBox
		get() = (dyeColor?.run { "$nameSpaceSeparatedCapitalized " } ?: "") +
				"Mystery Box"

	val Material.displayNameComponentAsMysteryBox
		get() = unstyledText(displayNameAsMysteryBox)

}