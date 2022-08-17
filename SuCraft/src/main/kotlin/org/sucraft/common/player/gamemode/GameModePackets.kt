/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.player.gamemode

import net.minecraft.network.protocol.game.PacketPlayOutGameStateChange
import org.bukkit.GameMode
import org.bukkit.GameMode.*
import org.bukkit.entity.Player
import org.sucraft.common.network.sendPacket

typealias ClientboundGameEventPacket = PacketPlayOutGameStateChange

/**
 * The value of this game mode when included in a game mode change packet.
 */
val GameMode.changePacketValue
	get() = when (this) {
		SURVIVAL -> 0
		CREATIVE -> 1
		ADVENTURE -> 2
		SPECTATOR -> 3
	}

/**
 * Sends a packet to this player that pretends as if their [GameMode] was changed
 * to the given [gameMode].
 * @return Whether the packet was sent successfully.
 */
fun Player.sendGameModeChangePacket(gameMode: GameMode) =
	sendPacket(
		ClientboundGameEventPacket(ClientboundGameEventPacket._CHANGE_GAME_MODE(), gameMode.changePacketValue.toFloat())
	)
//	sendPacketWithProtocolLib(
//		WrapperPlayServerGameStateChange().apply {
//			reason = 3
//			value = gameMode.changePacketValue.toFloat()
//		}
//	)

/**
 * Sends a packet to this player that pretends as if their [GameMode] was just changed
 * to their actual current game mode.
 * @return Whether the packet was sent successfully.
 */
fun Player.sendCurrentGameModeChangePacket() =
	sendGameModeChangePacket(gameMode)