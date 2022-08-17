/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.block

import org.bukkit.block.Block

/**
 * The location at the center of the block
 * (rather than [the corner of the block with the smallest coordinates][Block.getLocation]).
 */
val Block.centerLocation
	get() = location.clone().add(0.5, 0.5, 0.5)