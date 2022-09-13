/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location.distance.nearbyentities.nearbyplayers

import org.bukkit.Location
import org.bukkit.entity.Player
import org.sucraft.common.entity.y
import org.sucraft.common.location.distance.distance
import org.sucraft.common.location.distance.distance2D
import org.sucraft.common.location.distance.distanceSquared
import org.sucraft.common.player.PlayerPredicate
import kotlin.math.abs

// PlayerDistancePredicate type alias and extension

typealias PlayerDistancePredicate = (playerDistance: Double, player: Player) -> Boolean

/**
 * @return This [PlayerDistancePredicate] as a [PlayerPredicate], assuming the distance
 * as the Euclidean distance between the given [fromLocation] and the player being tested.
 */
fun PlayerDistancePredicate.asPlayerPredicate(fromLocation: Location) =
	{ player: Player -> this(fromLocation.distance(player), player) }

// The extension that calls the Bukkit method and serves as the basis for the other extensions

/**
 * @return All [players][Player] within the given distance per axis from this [Location].
 * Any null distance is assumed to be infinite.
 */
fun Location.getNearbyPlayers(
	xDistance: Double?,
	yDistance: Double?,
	zDistance: Double?,
	predicate: PlayerPredicate? = null
) =
	if (xDistance == null && yDistance == null && zDistance == null)
		world.players.asSequence().run {
			if (predicate == null) this else filter(predicate)
		}
	else
		world.getNearbyPlayers(
			this,
			xDistance!!,
			yDistance!!,
			zDistance!!,
			predicate
		).asSequence()

// Get players within chessboard distance

/**
 * @return All [players][Player] within the given chessboard distance from this [Location].
 * A null distance is assumed to be infinite.
 */
fun Location.getNearbyPlayersChessboardDistance(
	distance: Double?,
	predicate: PlayerPredicate? = null
) = getNearbyPlayers(
	xDistance = distance,
	yDistance = distance,
	zDistance = distance,
	predicate
)

/**
 * @return All [players][Player] within the given chessboard distances from this [Location].
 * Any null distance is assumed to be infinite.
 */
fun Location.getNearbyPlayersChessboardDistance(
	xzDistance: Double?,
	yDistance: Double?,
	predicate: PlayerPredicate? = null
) = getNearbyPlayers(
	xDistance = xzDistance,
	yDistance = yDistance,
	zDistance = xzDistance,
	predicate
)

// Get players within Euclidean distance

/**
 * @return All [players][Player] within the given Euclidean distance from this [Location].
 * A null distance is assumed to be infinite.
 */
fun Location.getNearbyPlayers(
	distance: Double?,
	predicate: PlayerPredicate? = null
) = getNearbyPlayersChessboardDistance(
	distance = distance,
	predicate
).run {
	if (distance != null)
		filter { distance(it) <= distance }
	else this
}

/**
 * @return All [players][Player] within the given Euclidean distance from this [Location].
 * A null distance is assumed to be infinite.
 */
fun Location.getNearbyPlayers(
	distance: Double?,
	predicate: PlayerDistancePredicate
) = getNearbyPlayers(
	distance = distance,
	predicate.asPlayerPredicate(this)
)

/**
 * @return All [players][Player] within the given Euclidean distances from this [Location].
 * Any null distance is assumed to be infinite.
 */
fun Location.getNearbyPlayers(
	xzDistance: Double?,
	yDistance: Double?,
	predicate: PlayerPredicate? = null
) = getNearbyPlayersChessboardDistance(
	xzDistance = xzDistance,
	yDistance = yDistance,
	predicate
).run {
	if (xzDistance != null)
		filter { distance2D(it) <= xzDistance }
	else this
}.run {
	if (yDistance != null)
		filter { abs(y - it.y) <= yDistance }
	else this
}

/**
 * @return All [players][Player] with the given Euclidean distances from this [Location].
 * Any null distance is assumed to be infinite.
 */
fun Location.getNearbyPlayers(
	xzDistance: Double?,
	yDistance: Double?,
	predicate: PlayerDistancePredicate
) = getNearbyPlayers(
	xzDistance = xzDistance,
	yDistance = yDistance,
	predicate.asPlayerPredicate(this)
)

/**
 * @return All [players][Player] within the given distance per axis from this [Location].
 * Any null distance is assumed to be infinite.
 */
fun Location.getNearbyPlayers(
	xDistance: Double?,
	yDistance: Double?,
	zDistance: Double?,
	predicate: PlayerDistancePredicate
) = getNearbyPlayers(
	xDistance = xDistance,
	yDistance = yDistance,
	zDistance = zDistance,
	predicate.asPlayerPredicate(this)
)

// Get the closest player out of a selection of players

/**
 * @return The closest [player][Player] out of the given [players],
 * or null if the sequence is empty.
 */
fun Location.getClosestPlayer(players: Sequence<Player>) =
	players.minByOrNull { distanceSquared(it) }

// Get the closest player, optionally within a Euclidean distance

/**
 * @return The closest [player][Player] with at most the given Euclidean distance from this [Location],
 * or null if none exists.
 * A null distance is assumed to be infinite.
 */
fun Location.getClosestPlayer(
	maxDistance: Double? = null,
	predicate: PlayerPredicate? = null
) = getClosestPlayer(
	getNearbyPlayers(
		distance = maxDistance,
		predicate
	)
)

/**
 * @return The closest [player][Player] with at most the given Euclidean distance from this [Location],
 * or null if none exists.
 * A null distance is assumed to be infinite.
 */
fun Location.getClosestPlayer(
	maxDistance: Double? = null,
	predicate: PlayerDistancePredicate
) = getClosestPlayer(
	getNearbyPlayers(
		distance = maxDistance,
		predicate
	)
)

/**
 * @return The closest [player][Player] with at most the given Euclidean distances from this [Location],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Location.getClosestPlayer(
	maxXZDistance: Double?,
	maxYDistance: Double?,
	predicate: PlayerPredicate? = null
) = getClosestPlayer(
	getNearbyPlayers(
		xzDistance = maxXZDistance,
		yDistance = maxYDistance,
		predicate
	)
)

/**
 * @return The closest [player][Player] with at most the given Euclidean distances from this [Location],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Location.getClosestPlayer(
	maxXZDistance: Double?,
	maxYDistance: Double?,
	predicate: PlayerDistancePredicate
) = getClosestPlayer(
	getNearbyPlayers(
		xzDistance = maxXZDistance,
		yDistance = maxYDistance,
		predicate
	)
)

/**
 * @return The closest [player][Player] with at most the given distance per axis from this [Location],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Location.getClosestPlayer(
	maxXDistance: Double?,
	maxYDistance: Double?,
	maxZDistance: Double?,
	predicate: PlayerPredicate? = null
) = getClosestPlayer(
	getNearbyPlayers(
		xDistance = maxXDistance,
		yDistance = maxYDistance,
		zDistance = maxZDistance,
		predicate
	)
)

/**
 * @return The closest [player][Player] with at most the given distance per axis from this [Location],
 * or null if none exists.
 * Any null distance is assumed to be infinite.
 */
fun Location.getClosestPlayer(
	maxXDistance: Double?,
	maxYDistance: Double?,
	maxZDistance: Double?,
	predicate: PlayerDistancePredicate
) = getClosestPlayer(
	getNearbyPlayers(
		xDistance = maxXDistance,
		yDistance = maxYDistance,
		zDistance = maxZDistance,
		predicate
	)
)