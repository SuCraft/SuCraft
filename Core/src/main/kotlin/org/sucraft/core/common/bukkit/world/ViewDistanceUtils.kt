/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.core.common.bukkit.world

import net.minecraft.network.protocol.game.ClientboundSetSimulationDistancePacket
import org.bukkit.World
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer
import org.joor.Reflect.on
import kotlin.math.max
import kotlin.math.min


object ViewDistanceUtils {

	fun clampViewDistanceToAllowedPaperValues(viewDistance: Int) =
		min(32, max(2, viewDistance))

	/**
	 * This  method is based on com.froobworld.viewdistancetweaks.hook.viewdistance.SpigotSimulationDistanceHook.setDistance
	 * (https://github.com/froobynooby/ViewDistanceTweaks/blob/master/src/main/java/com/froobworld/viewdistancetweaks/hook/viewdistance/SpigotSimulationDistanceHook.java)
	 */
	fun setSimulationDistance(world: World, simulationDistance: Int) {

		val usedSimulationDistance = clampViewDistanceToAllowedPaperValues(simulationDistance)

		if (usedSimulationDistance == world.simulationDistance) return

		// Send updated simulation distance packet to players
		val packet = ClientboundSetSimulationDistancePacket(usedSimulationDistance)
		for (player in world.players)
			(player as CraftPlayer).getHandle().b.a(packet)

		// Set the simulation distance
		(world as CraftWorld).getHandle().let {
			it.k().b(usedSimulationDistance)
			// Update the Spigot world configuration values
			it.spigotConfig.simulationDistance = (on(it.k()).field("d").field("t").get() as Number).toInt() - 1
			it.spigotConfig.viewDistance = (on(it.k().a).field("worldViewDistance").get() as Number).toInt() - 1
		}

	}

}