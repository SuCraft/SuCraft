/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.world

import org.bukkit.Bukkit
import org.bukkit.World

val mainWorld get(): World = Bukkit.getWorlds()[0]