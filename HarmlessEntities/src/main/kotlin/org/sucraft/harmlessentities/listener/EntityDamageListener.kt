/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.harmlessentities.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.sucraft.core.common.sucraft.delegate.HarmlessEntities
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.harmlessentities.main.SuCraftHarmlessEntitiesPlugin


object EntityDamageListener : SuCraftComponent<SuCraftHarmlessEntitiesPlugin>(SuCraftHarmlessEntitiesPlugin.getInstance()) {

	// Events

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	fun onEntityDamageByEntity(event: EntityDamageByEntityEvent) {
		if (HarmlessEntities.get().isHarmless(event.damager)) event.isCancelled = true
	}

}