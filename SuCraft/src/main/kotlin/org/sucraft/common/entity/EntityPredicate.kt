/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.entity

import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity

typealias EntityPredicate = (entity: Entity) -> Boolean

typealias LivingEntityPredicate = (livingEntity: LivingEntity) -> Boolean

typealias EntityByTypePredicate<E> = (entity: E) -> Boolean