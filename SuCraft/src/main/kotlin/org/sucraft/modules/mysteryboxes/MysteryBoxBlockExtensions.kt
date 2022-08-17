/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.mysteryboxes

import org.bukkit.block.Block
import org.bukkit.block.ShulkerBox
import org.sucraft.common.itemstack.material.isUncoloredOrColoredShulkerBox
import org.sucraft.modules.mysteryboxes.MysteryBoxes.displayNameComponentAsMysteryBox

/**
 * Provides some [Block] extensions related to mystery boxes to any class that implements this interface.
 */
interface MysteryBoxBlockExtensions {

	val Block.isMysteryBoxWithCustomMysteryBoxName
		get() =
			type.isUncoloredOrColoredShulkerBox &&
					(state as? ShulkerBox)?.customName()?.equals(type.displayNameComponentAsMysteryBox) == true

	/**
	 * Removes the title from a mystery box at this block,
	 * if applicable.
	 */
	fun Block.removeMysteryBoxTitleIfNecessary() {
		if (!isMysteryBoxWithCustomMysteryBoxName) return
		val shulkerBox = state as ShulkerBox
		shulkerBox.customName(null)
		shulkerBox.update()
	}

}