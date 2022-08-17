/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.serverconfig

import org.bukkit.World
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld

val World.spigotConfig get() = (this as CraftWorld).handle.spigotConfig