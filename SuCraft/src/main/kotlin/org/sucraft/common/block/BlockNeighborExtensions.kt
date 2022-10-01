/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.block

import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.sucraft.common.block.BlockCoordinates.Companion.coordinates

val orthogonalBlockFaces = BlockFace.values().filter { it.isCartesian }.toTypedArray()

val BlockCoordinates.orthogonalNeighbors
	get() = orthogonalBlockFaces.asSequence().map(::getRelative)

val Block.orthogonalLoadedNeighbors
	get() = coordinates.orthogonalNeighbors.mapNotNull { it.blockIfChunkIsLoaded }