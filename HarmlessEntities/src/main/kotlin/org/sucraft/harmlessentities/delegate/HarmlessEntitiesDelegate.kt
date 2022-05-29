/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.harmlessentities.delegate

import org.bukkit.entity.Entity
import org.sucraft.core.common.general.log.AbstractLogger
import org.sucraft.core.common.sucraft.delegate.HarmlessEntities
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.harmlessentities.data.HarmlessEntitiesData
import org.sucraft.harmlessentities.main.SuCraftHarmlessEntitiesPlugin

object HarmlessEntitiesDelegate : HarmlessEntities<SuCraftHarmlessEntitiesPlugin>, SuCraftComponent<SuCraftHarmlessEntitiesPlugin>(SuCraftHarmlessEntitiesPlugin.getInstance()) {

	// Initialization

	init {
		HarmlessEntities.registerImplementation(this)
	}

	// Delegate overrides

	override fun getDelegatePlugin(): SuCraftHarmlessEntitiesPlugin = plugin

	override fun getDelegateLogger(): AbstractLogger = logger

	// Implementation

	override fun isHarmless(entity: Entity): Boolean = HarmlessEntitiesData.isHarmless(entity)

	override fun setHarmless(entity: Entity, harmless: Boolean) = HarmlessEntitiesData.setHarmless(entity, harmless)

}