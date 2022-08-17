/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.block

import org.bukkit.block.Block
import org.sucraft.common.block.BlockCoordinates.Companion.coordinates

// TODO make better
/**
 * A printable description for this block.
 */
val Block.description: String
	get() = "Block{$coordinates}"