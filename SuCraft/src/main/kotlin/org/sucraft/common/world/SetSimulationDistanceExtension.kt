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
fun World.forceSetSimulationDistance(newSimulationDistance: Int) {

	val usedSimulationDistance = clampViewDistanceToAllowedPaperValues(newSimulationDistance)

	if (usedSimulationDistance == simulationDistance) return

	// Send updated simulation distance packet to players
	val packet = ClientboundSetSimulationDistancePacket(usedSimulationDistance)
	for (player in players)
		(player as CraftPlayer).handle._connection()._send(packet, null)

	// Set the simulation distance
	(this as CraftWorld).handle.run {
		val chunkSource = _chunkSource()
		chunkSource._setSimulationDistance(usedSimulationDistance)
		// Update the Spigot world configuration values
		spigotConfig.simulationDistance =
			chunkSource._distanceManager()._simulationDistance() - 1
		spigotConfig.viewDistance =
			chunkSource._chunkMap()._vanillaWorldViewDistancePlusOne() - 1
	}

}