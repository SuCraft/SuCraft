/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.itemstack.material

import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.Material.*
import org.bukkit.Tag

fun getMaterialSetForDyeColor(materialKeyKeySuffix: String) =
	DyeColor.values().map { getMaterialByKeyKey("${it.name.lowercase()}_$materialKeyKeySuffix")!! }.toSet()

val woolFullBlocks: Set<Material> by lazy {
	Tag.WOOL_FULL_BLOCKS.values
}

val Material.isWoolFullBlock
	get() = Tag.WOOL_FULL_BLOCKS.isTagged(this)

val woolSlabs: Set<Material> by lazy {
	Tag.WOOL_SLABS.values
}

val Material.isWoolSlab
	get() = Tag.WOOL_SLABS.isTagged(this)

val woolStairs: Set<Material> by lazy {
	Tag.WOOL_STAIRS.values
}

val Material.isWoolStairs
	get() = Tag.WOOL_STAIRS.isTagged(this)

val woolCarpets: Set<Material> by lazy {
	Tag.WOOL_CARPETS.values
}

val Material.isWoolCarpet
	get() = Tag.WOOL_CARPETS.isTagged(this)

val woolOrMossCarpets by lazy {
	woolCarpets + setOf(Material.MOSS_CARPET)
}

val Material.isWoolOrMossCarpet
	get() = this in woolOrMossCarpets

val dyes by lazy {
	getMaterialSetForDyeColor("dye")
}

val Material.isDye
	get() = this in dyes

val unglazedUncoloredOrColoredTerracottaFullBlocks: Set<Material> by lazy {
	Tag.TERRACOTTA_FULL_BLOCKS.values
}

val Material.isUnglazedUncoloredOrColoredTerracottaFullBlock
	get() = Tag.TERRACOTTA_FULL_BLOCKS.isTagged(this)

val unglazedUncoloredOrColoredTerracottaSlabs: Set<Material> by lazy {
	Tag.TERRACOTTA_SLABS.values
}

val Material.isUnglazedUncoloredOrColoredTerracottaSlab
	get() = Tag.TERRACOTTA_SLABS.isTagged(this)

val unglazedUncoloredOrColoredTerracottaStairs: Set<Material> by lazy {
	Tag.TERRACOTTA_STAIRS.values
}

val Material.isUnglazedUncoloredOrColoredTerracottaStairs
	get() = Tag.TERRACOTTA_STAIRS.isTagged(this)

val unglazedColoredTerracottaFullBlocks by lazy {
	Tag.TERRACOTTA_FULL_BLOCKS.values - setOf(Material.TERRACOTTA)
}

val Material.isUnglazedColoredTerracottaFullBlock
	get() = this in unglazedColoredTerracottaFullBlocks

val unglazedColoredTerracottaSlabs by lazy {
	Tag.TERRACOTTA_SLABS.values
}

val Material.isUnglazedColoredTerracottaSlab
	get() = this in unglazedColoredTerracottaSlabs

val unglazedColoredTerracottaStairs by lazy {
	Tag.TERRACOTTA_STAIRS.values
}

val Material.isUnglazedColoredTerracottaStairs
	get() = this in unglazedColoredTerracottaStairs

val glazedTerracottaFullBlocks by lazy {
	getMaterialSetForDyeColor("glazed_terracotta")
}

val Material.isGlazedTerracottaFullBlock
	get() = this in glazedTerracottaFullBlocks

val stainedGlasses by lazy {
	getMaterialSetForDyeColor("stained_glass")
}

val Material.isStainedGlass
	get() = this in stainedGlasses

val stainedGlassPanes by lazy {
	getMaterialSetForDyeColor("stained_glass_pane")
}

val Material.isStainedGlassPane
	get() = this in stainedGlassPanes

val unstainedOrStainedGlasses by lazy {
	stainedGlasses + setOf(Material.GLASS)
}

val Material.isUnstainedOrStainedGlass
	get() = this in unstainedOrStainedGlasses

val unstainedOrStainedGlassPanes by lazy {
	stainedGlassPanes + setOf(Material.GLASS_PANE)
}

val Material.isUnstainedOrStainedGlassPane
	get() = this in unstainedOrStainedGlassPanes

val uncoloredOrColoredShulkerBoxes: Set<Material> by lazy {
	Tag.SHULKER_BOXES.values
}

val Material.isUncoloredOrColoredShulkerBox
	get() = Tag.SHULKER_BOXES.isTagged(this)

val coloredShulkerBoxes by lazy {
	uncoloredOrColoredShulkerBoxes - setOf(Material.SHULKER_BOX)
}

val Material.isColoredShulkerBox
	get() = this in coloredShulkerBoxes

val concreteFullBlocks by lazy {
	getMaterialSetForDyeColor("concrete")
}

val Material.isConcreteFullBlock
	get() = this in concreteFullBlocks

val concreteSlabs by lazy {
	getMaterialSetForDyeColor("concrete_slab")
}

val Material.isConcreteSlab
	get() = this in concreteSlabs

val concreteStairs by lazy {
	getMaterialSetForDyeColor("concrete_stairs")
}

val Material.isConcreteStairs
	get() = this in concreteStairs

val concretePowderFullBlocks by lazy {
	getMaterialSetForDyeColor("concrete_powder")
}

val Material.isConcretePowderFullBlock
	get() = this in concretePowderFullBlocks

val concretePowderSlabs by lazy {
	getMaterialSetForDyeColor("concrete_powder_slab")
}

val Material.isConcretePowderSlab
	get() = this in concretePowderSlabs

val concretePowderStairs by lazy {
	getMaterialSetForDyeColor("concrete_powder_stairs")
}

val Material.isConcretePowderStairs
	get() = this in concretePowderStairs

val beds: Set<Material> by lazy {
	Tag.BEDS.values
}

val Material.isBed
	get() = Tag.BEDS.isTagged(this)

val uncoloredOrColoredCandles: Set<Material> by lazy {
	Tag.CANDLES.values
}

val Material.isUncoloredOrColoredCandle
	get() = Tag.CANDLES.isTagged(this)

val coloredCandles by lazy {
	uncoloredOrColoredCandles - setOf(Material.CANDLE)
}

val Material.isColoredCandle
	get() = this in coloredCandles

val commandBlocks by lazy {
	setOf(COMMAND_BLOCK, CHAIN_COMMAND_BLOCK, REPEATING_COMMAND_BLOCK)
}

val Material.isCommandBlock
	get() = this in commandBlocks

val structureDefinitionBlocks by lazy {
	setOf(STRUCTURE_VOID, STRUCTURE_BLOCK, JIGSAW)
}

val Material.isStructureDefinitionBlock
	get() = this in structureDefinitionBlocks

val notMeantToBeOwnedByPlayers by lazy {
	commandBlocks + structureDefinitionBlocks + COMMAND_BLOCK_MINECART
}

val Material.isNotMeantToBeOwnedByPlayers
	get() = this in notMeantToBeOwnedByPlayers

val spawnEggs by lazy {
	Material.values().filter { it.key.key.contains("spawn_egg", ignoreCase = true) }.toSet()
}

val Material.isSpawnEgg
	get() = this in spawnEggs

val bannerPatterns by lazy {
	Material.values().filter { it.key.key.contains("banner_pattern", ignoreCase = true) }.toSet()
}

val Material.isBannerPattern
	get() = this in bannerPatterns

val musicDiscs by lazy {
	Tag.ITEMS_MUSIC_DISCS.values
}

val Material.isMusicDisc
	get() = this in musicDiscs

val groundHeads by lazy {
	setOf(CREEPER_HEAD, DRAGON_HEAD, PLAYER_HEAD, SKELETON_SKULL, WITHER_SKELETON_SKULL, ZOMBIE_HEAD)
}

val Material.isGroundHead
	get() = this in groundHeads

val wallHeads by lazy {
	setOf(
		CREEPER_WALL_HEAD,
		DRAGON_WALL_HEAD,
		PLAYER_WALL_HEAD,
		SKELETON_WALL_SKULL,
		WITHER_SKELETON_WALL_SKULL,
		ZOMBIE_WALL_HEAD
	)
}

val Material.isWallHead
	get() = this in wallHeads

val groundOrWallHeads by lazy {
	groundHeads + wallHeads
}

val Material.isGroundOrWallHead
	get() = this in groundOrWallHeads

val bucketsOfAquaticMob by lazy {
	setOf(
		TROPICAL_FISH_BUCKET,
		AXOLOTL_BUCKET,
		COD_BUCKET,
		PUFFERFISH_BUCKET,
		SALMON_BUCKET,
		TADPOLE_BUCKET
	)
}

val Material.isBucketOfAquaticMob
	get() = this in bucketsOfAquaticMob

val growingOrGrownAmethystClusters by lazy {
	setOf(AMETHYST_CLUSTER, LARGE_AMETHYST_BUD, MEDIUM_AMETHYST_BUD, SMALL_AMETHYST_BUD)
}

val Material.isGrowingOrGrownAmethystCluster
	get() = this in growingOrGrownAmethystClusters

val ores by lazy {
	Tag.COAL_ORES.values + Tag.COPPER_ORES.values + Tag.DIAMOND_ORES.values +
			Tag.EMERALD_ORES.values + Tag.GOLD_ORES.values + Tag.IRON_ORES.values +
			Tag.LAPIS_ORES.values + Tag.REDSTONE_ORES.values
}

val Material.isOre
	get() = this in ores

val nonRawNonCutCopperFullBlocks by lazy {
	setOf(COPPER_BLOCK, EXPOSED_COPPER, OXIDIZED_COPPER, WEATHERED_COPPER)
}

val Material.isNonRawNonCutCopperFullBlock
	get() = this in nonRawNonCutCopperFullBlocks

val nonRawNonCutCopperBlocks by lazy {
	nonRawNonCutCopperFullBlocks
}

val Material.isNonRawNonCutCopperBlock
	get() = this in nonRawNonCutCopperBlocks

val nonRawMetalFullBlocks by lazy {
	nonRawNonCutCopperFullBlocks + DIAMOND_BLOCK + IRON_BLOCK + GOLD_BLOCK + EMERALD_BLOCK + NETHERITE_BLOCK
}

val Material.isNonRawMetalFullBlock
	get() = this in nonRawMetalFullBlocks

val nonRawMetalStairs by lazy {
	setOf(
		SUCRAFT_DIAMOND_STAIRS,
		SUCRAFT_IRON_STAIRS,
		SUCRAFT_GOLD_STAIRS,
		SUCRAFT_EMERALD_STAIRS,
		SUCRAFT_NETHERITE_STAIRS
	)
}

val Material.isNonRawMetalStairs
	get() = this in nonRawMetalStairs

val nonRawMetalSlabs by lazy {
	setOf(SUCRAFT_DIAMOND_SLAB, SUCRAFT_IRON_SLAB, SUCRAFT_GOLD_SLAB, SUCRAFT_EMERALD_SLAB, SUCRAFT_NETHERITE_SLAB)
}

val Material.isNonRawMetalSlab
	get() = this in nonRawMetalSlabs

val nonRawMetalBlocks by lazy {
	nonRawMetalFullBlocks + nonRawMetalStairs + nonRawMetalSlabs
}

val Material.isNonRawMetalBlock
	get() = this in nonRawMetalBlocks

val bookshelves by lazy {
	Material.values().filter { it.key.key.contains("bookshelf", ignoreCase = true) }.toSet()
}

val Material.isBookshelf
	get() = this in bookshelves

val fires: Set<Material> by lazy {
	Tag.FIRE.values
}

val Material.isFire
	get() = this in fires

val rawMetalFullBlocks: Set<Material> by lazy {
	setOf(RAW_COPPER, RAW_GOLD, RAW_IRON)
}

val Material.isRawMetalFullBlock
	get() = this in rawMetalFullBlocks

val rawMetalStairs: Set<Material> by lazy {
	setOf(SUCRAFT_RAW_COPPER_STAIRS, SUCRAFT_RAW_GOLD_STAIRS, SUCRAFT_RAW_IRON_STAIRS)
}

val Material.isRawMetalStairs
	get() = this in rawMetalStairs

val rawMetalSlabs: Set<Material> by lazy {
	setOf(SUCRAFT_RAW_COPPER_SLAB, SUCRAFT_RAW_GOLD_SLAB, SUCRAFT_RAW_IRON_SLAB)
}

val Material.isRawMetalSlab
	get() = this in rawMetalSlabs

val rawMetalBlocks: Set<Material> by lazy {
	rawMetalFullBlocks + rawMetalStairs + rawMetalSlabs
}

val Material.isRawMetalBlock
	get() = this in rawMetalBlocks