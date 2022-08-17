/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.network

import net.minecraft.network.protocol.Packet
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer
import org.bukkit.entity.Player

// Commented out while ProtocolLib dependency is unnecessary

///**
// * Sends a [packet] to this player using [ProtocolLib][protocolManager].
// * @return Whether the packet was sent successfully.
// */
//fun Player.sendPacketWithProtocolLib(packet: PacketContainer) =
//	try {
//		protocolManager.sendServerPacket(this, packet)
//		true
//	} catch (_: InvocationTargetException) {
//		false
//	}
//
///**
// * Sends a PacketWrapper [packet] to this player using [ProtocolLib][protocolManager].
// * @return Whether the packet was sent successfully.
// */
//fun Player.sendPacketWithProtocolLib(packet: AbstractPacket) =
//	sendPacketWithProtocolLib(packet.handle)

/**
 * Sends a vanilla server packet to this player.
 * @return Whether the packet was sent successfully.
 */
fun Player.sendPacket(packet: Packet<*>) {
	(this as? CraftPlayer)?.handle?._connection()?._send(packet, null)
}