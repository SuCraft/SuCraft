/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.harmlessentities.delegate

import org.bukkit.entity.Entity
import org.sucraft.core.common.sucraft.delegate.HarmlessEntities
import org.sucraft.harmlessentities.data.HarmlessEntitiesData


object HarmlessEntitiesDelegate : HarmlessEntities {

	// Register as delegate

	init {
		HarmlessEntities.registerImplementation(this)
	}

	// Implementation

	override fun isHarmless(entity: Entity): Boolean = HarmlessEntitiesData.isHarmless(entity)

	override fun setHarmless(entity: Entity, harmless: Boolean) = HarmlessEntitiesData.setHarmless(entity, harmless)

}