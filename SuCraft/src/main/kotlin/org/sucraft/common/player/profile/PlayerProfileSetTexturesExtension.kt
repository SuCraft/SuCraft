/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.player.profile

import com.destroystokyo.paper.profile.PlayerProfile
import com.destroystokyo.paper.profile.ProfileProperty

fun PlayerProfile.setTextures(textures: String) =
	setProperty(ProfileProperty("textures", textures))