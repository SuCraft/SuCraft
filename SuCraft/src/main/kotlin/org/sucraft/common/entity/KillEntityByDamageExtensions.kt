/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.entity

import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity

/**
 * Kills this entity by damaging it by the amount of health it has.
 */
fun LivingEntity.killByDamage(attempts: Int = 10) =
	repeat(attempts) {
		run { damage(health) }
		if (isDead) return
	}

/**
 * Kills this entity by damaging it by the amount of health it has.
 *
 * @param source The entity assumed to be the source of the damage dealt.
 */
fun LivingEntity.killByDamage(attempts: Int = 10, source: Entity? = null) =
	repeat(attempts) {
		run { damage(health, source) }
		if (isDead) return
	}