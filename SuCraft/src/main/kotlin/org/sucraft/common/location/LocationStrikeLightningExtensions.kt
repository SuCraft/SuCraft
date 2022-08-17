/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.location

import org.bukkit.Location

fun Location.strikeLightning() = world.strikeLightning(this)

fun Location.strikeLightningEffect() = world.strikeLightningEffect(this)