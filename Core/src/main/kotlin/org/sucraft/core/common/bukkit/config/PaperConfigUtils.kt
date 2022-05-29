/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.core.common.bukkit.config

import com.destroystokyo.paper.PaperWorldConfig
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld
import org.spigotmc.SpigotWorldConfig
import org.sucraft.core.common.bukkit.world.mainWorld


object PaperConfigUtils {

	fun modifyInPaperWorldConfig(world: World, modifier: (PaperWorldConfig) -> Unit) =
		modifier((world as CraftWorld).handle.paperConfig)

	fun modifyInPaperWorldsConfig(modifier: (PaperWorldConfig) -> Unit) =
		Bukkit.getWorlds().forEach { modifyInPaperWorldConfig(it, modifier) }

	fun <T> setInPaperWorldConfig(world: World, setter: (PaperWorldConfig, T) -> Unit, value: T) =
		modifyInPaperWorldConfig(world) { setter(it, value) }

	fun <T> setInPaperWorldsConfig(setter: (PaperWorldConfig, T) -> Unit, value: T) =
		modifyInPaperWorldsConfig { setter(it, value) }

	fun <T> getFromPaperMainWorldConfig(getter: (PaperWorldConfig) -> T): T =
		getFromPaperWorldConfig(mainWorld, getter)

	fun <T> getFromPaperWorldConfig(world: World, getter: (PaperWorldConfig) -> T): T =
		getter((world as CraftWorld).handle.paperConfig)

	fun modifyInSpigotWorldConfig(world: World, modifier: (SpigotWorldConfig) -> Unit) =
		modifier((world as CraftWorld).handle.spigotConfig)

	fun modifyInSpigotWorldsConfig(modifier: (SpigotWorldConfig) -> Unit) =
		Bukkit.getWorlds().forEach { modifyInSpigotWorldConfig(it, modifier) }

	fun <T> setInSpigotWorldConfig(world: World, setter: (SpigotWorldConfig, T) -> Unit, value: T) =
		modifyInSpigotWorldConfig(world) { setter(it, value) }

	fun <T> setInSpigotWorldsConfig(setter: (SpigotWorldConfig, T) -> Unit, value: T) =
		modifyInSpigotWorldsConfig { setter(it, value) }

	fun <T> getFromSpigotMainWorldConfig(getter: (SpigotWorldConfig) -> T): T =
		getFromSpigotWorldConfig(mainWorld, getter)

	fun <T> getFromSpigotWorldConfig(world: World, getter: (SpigotWorldConfig) -> T): T =
		getter((world as CraftWorld).handle.spigotConfig)

}