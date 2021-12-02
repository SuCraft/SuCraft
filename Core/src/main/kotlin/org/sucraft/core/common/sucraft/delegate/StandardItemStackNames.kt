/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.sucraft.delegate

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.sucraft.core.common.sucraft.plugin.SuCraftDelegateHolder


interface StandardItemStackNames {

	// Companion (implementation)

	companion object : SuCraftDelegateHolder<StandardItemStackNames>()

	// Interface

	fun get(itemStack: ItemStack) = get(itemStack.type)

	fun get(type: Material): String

}