/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.entity

import org.bukkit.event.entity.EntityDamageByBlockEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.sucraft.common.block.description

private fun EntityDamageEvent.getDescriptionMapEntries(printEntitiesAsShort: Boolean) =
	listOfNotNull(
		"cancelled" to isCancelled,
		"cause" to cause,
		"damage" to damage,
		"final damage" to finalDamage,
		*(this as? EntityDamageByEntityEvent)?.run {
			arrayOf(
				"damager entity" to damager.getDescription(printEntitiesAsShort, true),
				"critical" to isCritical
			)
		} ?: emptyArray(),
		(this as? EntityDamageByBlockEvent)?.run { "damager block" to damager?.description }
	)

fun EntityDamageEvent.getDescriptionMap(printEntitiesAsShort: Boolean): Map<String, Any?> =
	getDescriptionMapEntries(printEntitiesAsShort).toMap()

val EntityDamageEvent.descriptionMap
	get() = getDescriptionMap(false)

fun EntityDamageEvent.getDescription(printEntitiesAsShort: Boolean) =
	"${javaClass.simpleName}{${getDescriptionMap(printEntitiesAsShort)}}"

val EntityDamageEvent.description
	get() = getDescription(false)