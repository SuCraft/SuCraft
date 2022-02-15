/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.core.common.sucraft.delegate

import org.sucraft.core.common.sucraft.plugin.SuCraftDelegate
import org.sucraft.core.common.sucraft.plugin.SuCraftDelegateHolder
import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin


interface GlobalTickCounter<P: SuCraftPlugin> : SuCraftDelegate<P> {

	// Companion (implementation)

	companion object : SuCraftDelegateHolder<GlobalTickCounter<*>>()

	// Delegate name

	override fun getDelegateInterfaceName() = GlobalTickCounter::class.simpleName!!

	// Interface

	fun getValue(): Long

}