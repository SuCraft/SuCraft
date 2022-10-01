/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.entity

import org.bukkit.entity.Panda
import org.bukkit.entity.Panda.Gene.*

val Panda.personality
	get() = if (!mainGene.isRecessive || mainGene == hiddenGene) mainGene else NORMAL