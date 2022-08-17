/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.entity

import org.bukkit.entity.Ageable
import org.bukkit.entity.Entity

/**
 * Whether this entity is an [adult][Ageable.isAdult] if it is [Ageable],
 * or null if it is not [Ageable].
 */
val Entity.isAdultIfAgeableElseNull
	get() = (this as? Ageable)?.isAdult

/**
 * Whether this entity is a [baby][Ageable.isAdult] if it is [Ageable],
 * or null if it is not [Ageable].
 */
val Entity.isBabyIfAgeableElseNull
	get() = isAdultIfAgeableElseNull?.let { !it }

/**
 * Whether this entity is an [adult][Ageable.isAdult] if it is [Ageable],
 * or false if it is not [Ageable].
 */
val Entity.isAdultAgeable
	get() = isAdultIfAgeableElseNull ?: false

/**
 * Whether this entity is a [baby][Ageable.isAdult] if it is [Ageable],
 * or false if it is not [Ageable].
 */
val Entity.isBabyAgeable
	get() = isBabyIfAgeableElseNull ?: false

/**
 * Makes this entity an [adult][Ageable.setAdult] if it is [Ageable].
 */
fun Entity.setAdultIfAgeable() =
	(this as? Ageable)?.setAdult()

/**
 * Makes this entity a [baby][Ageable.setBaby] if it is [Ageable].
 */
fun Entity.setBabyIfAgeable() =
	(this as? Ageable)?.setBaby()