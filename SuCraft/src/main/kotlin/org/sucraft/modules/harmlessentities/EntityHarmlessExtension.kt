/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.harmlessentities

import org.bukkit.entity.Entity
import org.bukkit.entity.Projectile
import org.sucraft.common.persistentdata.getPersistentFlag
import org.sucraft.common.persistentdata.removePersistent
import org.sucraft.common.persistentdata.setPersistentFlag
import org.sucraft.modules.harmlessentities.HarmlessEntities.harmlessEntityKey
import org.sucraft.modules.harmlessentities.HarmlessEntities.oldHarmlessEntityKey

var Entity.isHarmless: Boolean
	get() = getPersistentFlag(harmlessEntityKey) || getPersistentFlag(oldHarmlessEntityKey)
	set(value) {
		if (value)
			setPersistentFlag(harmlessEntityKey)
		else {
			removePersistent(harmlessEntityKey)
			removePersistent(oldHarmlessEntityKey)
		}
	}

/**
 * @return Whether this entity is [harmless][isHarmless],
 * or whether it is transitively caused by a harmless entity,
 * such as a [Projectile] fired by a harmless entity.
 */
val Entity.isTransitivelyHarmless
	get() = isHarmless ||
			(this as? Projectile)?.shooter?.let { it as? Entity }?.isHarmless == true