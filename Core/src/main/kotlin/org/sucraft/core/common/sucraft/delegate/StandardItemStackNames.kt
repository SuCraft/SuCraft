/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.sucraft.delegate

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.sucraft.core.common.sucraft.plugin.SuCraftDelegate
import org.sucraft.core.common.sucraft.plugin.SuCraftDelegateHolder
import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin


interface StandardItemStackNames<P: SuCraftPlugin> : SuCraftDelegate<P> {

	// Companion (implementation)

	companion object : SuCraftDelegateHolder<StandardItemStackNames<*>>()

	// Delegate name

	override fun getDelegateInterfaceName() = StandardItemStackNames::class.simpleName!!

	// Interface

	fun get(itemStack: ItemStack) = get(itemStack.type)

	fun get(type: Material): String

}