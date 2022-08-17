/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location

import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.event.entity.CreatureSpawnEvent

fun Location.spawnEntity(
	entityType: EntityType,
	spawnReason: CreatureSpawnEvent.SpawnReason,
	function: ((Entity) -> Unit)? = null
) =
	if (function != null)
		world.spawnEntity(this, entityType, spawnReason, function)
	else
		world.spawnEntity(this, entityType, spawnReason)

fun Location.spawnEntity(
	entityType: EntityType
) = world.spawnEntity(this, entityType)

fun <T : Entity> Location.spawn(
	`class`: Class<T>
) = world.spawn(this, `class`)

fun <T : Entity> Location.spawn(
	`class`: Class<T>,
	function: T.() -> Unit
) = world.spawn(this, `class`, function)

fun <T : Entity> Location.spawn(
	`class`: Class<T>,
	spawnReason: CreatureSpawnEvent.SpawnReason
) = world.spawn(this, `class`, spawnReason)

fun <T : Entity> Location.spawn(
	`class`: Class<T>,
	spawnReason: CreatureSpawnEvent.SpawnReason,
	function: T.() -> Unit
) = world.spawn(this, `class`, function, spawnReason)