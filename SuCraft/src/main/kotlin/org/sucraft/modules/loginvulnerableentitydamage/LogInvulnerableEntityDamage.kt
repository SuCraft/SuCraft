/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.loginvulnerableentitydamage

import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.sucraft.common.entity.description
import org.sucraft.common.entity.longDescription
import org.sucraft.common.event.on
import org.sucraft.common.module.SuCraftModule

/**
 * Logs Bukkit events of damage to or deaths of invulnerable entities to the console. Invulnerable entities should
 * not be able to take damage or die, but in some cases, such as when being teleported, they transition from
 * alive to dead for unknown reasons. Logging events to console should have no effect while everything works as
 * intended, but makes sure we know that something erroneous happened when it does.
 */
object LogInvulnerableEntityDamage : SuCraftModule<LogInvulnerableEntityDamage>() {

	// Events

	init {
		// Listen to entity damage to log damage to invulnerable entities
		on(EntityDamageEvent::class) {
			if (entity.isInvulnerable)
				warning("An invulnerable entity (${entity.longDescription}) received damage ($description)")
		}
		// Listen to entity deaths to log deaths of invulnerable entities
		on(EntityDeathEvent::class) {
			if (entity.isInvulnerable)
				warning("An invulnerable entity (${entity.longDescription}) died (is event cancelled: $isCancelled)")
		}
	}

}