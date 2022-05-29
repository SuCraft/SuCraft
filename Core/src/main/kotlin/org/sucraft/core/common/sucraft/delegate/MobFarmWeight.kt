/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.sucraft.delegate

import org.sucraft.core.common.sucraft.plugin.SuCraftDelegate
import org.sucraft.core.common.sucraft.plugin.SuCraftDelegateHolder
import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
interface MobFarmWeight<P: SuCraftPlugin> : SuCraftDelegate<P> {

	// Companion (implementation)

	companion object : SuCraftDelegateHolder<MobFarmWeight<*>>()

	// Delegate name

	override fun getDelegateInterfaceName() = MobFarmWeight::class.simpleName!!

	// Interface

	fun getWeight(): Double

	fun setWeight(weight: Double)

}