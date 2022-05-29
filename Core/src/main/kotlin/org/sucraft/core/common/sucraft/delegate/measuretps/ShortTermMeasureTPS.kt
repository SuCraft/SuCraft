/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.sucraft.delegate.measuretps

import org.sucraft.core.common.sucraft.plugin.SuCraftDelegate
import org.sucraft.core.common.sucraft.plugin.SuCraftDelegateHolder
import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
interface ShortTermMeasureTPS<P: SuCraftPlugin> : SuCraftDelegate<P> {

	// Companion (implementation)

	companion object : SuCraftDelegateHolder<ShortTermMeasureTPS<*>>()

	// Delegate name

	override fun getDelegateInterfaceName() = ShortTermMeasureTPS::class.simpleName!!

	// Interface

	fun getRecentTPS(): Double

	fun registerListener(listener: ShortTermMeasuredTPSListener)

}