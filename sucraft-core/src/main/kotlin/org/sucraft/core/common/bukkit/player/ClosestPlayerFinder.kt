/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.bukkit.player

import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import java.util.function.Predicate


object ClosestPlayerFinder {

	fun getClosestPlayer(location: Location, predicate: Predicate<Player>? = null): Player? =
		location.world.players.minByOrNull{it.location.distanceSquared(location)}

	fun getClosestPlayer(entity: Entity, predicate: Predicate<Player>? = null): Player? =
		getClosestPlayer(entity.location, predicate)

}