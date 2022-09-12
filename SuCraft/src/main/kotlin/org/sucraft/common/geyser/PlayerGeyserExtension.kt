/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.geyser

import org.bukkit.entity.Player
import org.geysermc.api.Geyser

val Player.isConnectedViaGeyser
	get() = Geyser.api().connectionByUuid(uniqueId) != null