/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.sucraft.delegate

import org.bukkit.entity.Entity
import org.sucraft.core.common.sucraft.plugin.SuCraftDelegate
import org.sucraft.core.common.sucraft.plugin.SuCraftDelegateHolder
import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
interface HarmlessEntities<P: SuCraftPlugin> : SuCraftDelegate<P> {

	// Companion (implementation)

	companion object : SuCraftDelegateHolder<HarmlessEntities<*>>()

	// Delegate name

	override fun getDelegateInterfaceName() = HarmlessEntities::class.simpleName!!

	// Interface

	fun isHarmless(entity: Entity): Boolean

	fun setHarmless(entity: Entity, harmless: Boolean)

}