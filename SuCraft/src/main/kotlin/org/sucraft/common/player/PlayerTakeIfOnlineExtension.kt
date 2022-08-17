/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.player

import org.bukkit.entity.Player

/**
 * @return This player, if they are online, otherwise null.
 */
fun Player.takeIfOnline() = takeIf { isOnline }