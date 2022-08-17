/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.world

import net.minecraft.network.protocol.game.ClientboundSetSimulationDistancePacket
import org.bukkit.World
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer
import org.joor.Reflect.on

/**
 * This method is based on com.froobworld.viewdistancetweaks.hook.viewdistance.SpigotSimulationDistanceHook.setDistance.
 */
fun World.forceSetSimulationDistance(simulationDistance: Int) {

	val usedSimulationDistance = clampViewDistanceToAllowedPaperValues(simulationDistance)

	if (usedSimulationDistance == simulationDistance) return

	// Send updated simulation distance packet to players
	val packet = ClientboundSetSimulationDistancePacket(usedSimulationDistance)
	for (player in players)
		(player as CraftPlayer).handle._connection()._send(packet, null)

	// Set the simulation distance
	// TODO use deobfuscation helper here
	(this as CraftWorld).handle.run {
		k().b(usedSimulationDistance)
		// Update the Spigot world configuration values
		spigotConfig.simulationDistance =
			(on(k()).field("c").field("t").get() as Number).toInt() - 1
		spigotConfig.viewDistance =
			(on(k().a).field("vanillaWorldViewDistancePlusOne").get() as Number).toInt() - 1
	}

}