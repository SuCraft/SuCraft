/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.harmlessentities.data

import org.bukkit.NamespacedKey
import org.bukkit.entity.Entity
import org.bukkit.persistence.PersistentDataHolder
import org.sucraft.core.common.bukkit.persistentdata.PersistentDataShortcuts
import org.sucraft.core.common.sucraft.persistentdata.DefaultSuCraftNamespace


object HarmlessEntitiesData {

	private val harmlessEntityNamespacedKey = DefaultSuCraftNamespace.getNamespacedKey("harmless")
	// Checked for backwards compatability
	private val oldHarmlessEntityNamespacedKey = NamespacedKey("harmlessentities", "harmless")

	fun isHarmless(entity: Entity) =
		PersistentDataShortcuts.Tag[entity, harmlessEntityNamespacedKey] || PersistentDataShortcuts.Tag[entity, oldHarmlessEntityNamespacedKey]

	fun setHarmless(entity: Entity, harmless: Boolean) {
		// Remove the old tag if still present
		PersistentDataShortcuts.remove(entity, oldHarmlessEntityNamespacedKey)
		// Add or remove the tag as appropriate
		if (harmless) PersistentDataShortcuts.Tag.set(entity, harmlessEntityNamespacedKey) else PersistentDataShortcuts.remove(entity, harmlessEntityNamespacedKey)
	}

}