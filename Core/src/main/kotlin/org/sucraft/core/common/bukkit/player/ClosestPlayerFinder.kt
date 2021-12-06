/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.bukkit.player

import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import java.util.function.Predicate


@Suppress("MemberVisibilityCanBePrivate")
object ClosestPlayerFinder {

	fun getClosestPlayer(location: Location, predicate: ((Player) -> Boolean)? = null): Player? =
		location.world.players.asSequence().let { if (predicate == null) it else it.filter(predicate) }.minByOrNull{it.location.distanceSquared(location)}

	fun getClosestPlayer(entity: Entity, predicate: ((Player) -> Boolean)? = null): Player? =
		getClosestPlayer(entity.location, predicate)

}