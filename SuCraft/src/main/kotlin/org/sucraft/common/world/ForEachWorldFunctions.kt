/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.world

import org.bukkit.Bukkit
import org.bukkit.World
import org.sucraft.common.function.runEach

fun forEachWorld(action: (World) -> Unit) = Bukkit.getWorlds().forEach(action)

fun runEachWorld(action: World.() -> Unit) = Bukkit.getWorlds().runEach(action)