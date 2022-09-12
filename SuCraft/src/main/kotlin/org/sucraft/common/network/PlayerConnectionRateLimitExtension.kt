/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.network

import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer
import io.netty.handler.traffic.ChannelTrafficShapingHandler
import org.bukkit.entity.Player
import org.sucraft.modules.uuidcommand.UUIDCommand.Commands.uuid
import java.util.*
import kotlin.collections.HashMap

private val existingTrafficShapingHandlers: MutableMap<UUID, ChannelTrafficShapingHandler> = HashMap()

/**
 * Rate-limits the network connection of the player to the given maximum speed.
 * This only affects the write speed, i.e. the rate at which data is sent to the client.
 * This overrides any previous rate limits already applied through this function.
 *
 * @param maxSpeed The maximum network send speed to apply, in bytes/second.
 */
fun Player.rateLimitNetworkConnection(maxSpeed: Long) {
	existingTrafficShapingHandlers.compute(uniqueId) { _, handler ->
		if (handler != null) {
			handler.writeLimit = maxSpeed
			return@compute handler
		}
		val newHandler = ChannelTrafficShapingHandler(maxSpeed, 0L)
		(player as CraftPlayer).handle._connection()
			._connection()
			._channel()
			.pipeline().addFirst(newHandler)
		return@compute newHandler
	}
}