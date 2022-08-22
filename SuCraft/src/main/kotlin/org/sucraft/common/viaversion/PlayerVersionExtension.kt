/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.viaversion

import com.viaversion.viaversion.api.Via
import org.bukkit.entity.Player

val Player.clientVersion
	get() = Via.getAPI().getPlayerVersion(this)