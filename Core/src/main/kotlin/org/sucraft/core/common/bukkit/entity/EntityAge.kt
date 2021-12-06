/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.bukkit.entity

import org.bukkit.entity.Ageable
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Zombie


object EntityAge {

	fun isAdult(entity: LivingEntity): Boolean? {
		if (entity is Ageable) return entity.isAdult
		if (entity is Zombie) return entity.isAdult
		return null
	}

	fun isBaby(entity: LivingEntity) = isAdult(entity)?.let { !it }

	fun setAdult(entity: LivingEntity) {
		if (entity is Ageable) {
			entity.setAdult()
			return
		}
		if (entity is Zombie) {
			entity.setAdult()
			return
		}
	}

	fun setBaby(entity: LivingEntity) {
		if (entity is Ageable) {
			entity.setBaby()
			return
		}
		if (entity is Zombie) {
			entity.setBaby()
			return
		}
	}

}