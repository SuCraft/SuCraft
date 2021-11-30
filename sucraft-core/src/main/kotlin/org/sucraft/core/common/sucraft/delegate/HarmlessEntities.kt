/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.sucraft.delegate

import org.bukkit.entity.Entity
import org.sucraft.core.common.sucraft.plugin.SuCraftDelegateHolder

interface HarmlessEntities {

	// Companion (implementation)

	companion object : SuCraftDelegateHolder<HarmlessEntities>()

	// Interface

	fun isHarmless(entity: Entity): Boolean

	fun setHarmless(entity: Entity, harmless: Boolean)

}