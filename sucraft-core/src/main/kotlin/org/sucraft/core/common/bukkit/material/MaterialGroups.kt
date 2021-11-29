package org.sucraft.core.common.bukkit.material

import org.bukkit.Material
import org.bukkit.Material.*
import org.sucraft.core.common.general.datastructure.PredicateContainer

typealias MaterialGroup = PredicateContainer<Material>

// TODO MUST POTENTIALLY BE UPDATED EVERY VERSION
@Suppress("MemberVisibilityCanBePrivate")
object MaterialGroups {

	val damageDealingBlock = MaterialGroup {
		when (it) {
			in fallingBlock,
			CACTUS,
			FIRE,
			LAVA,
			LAVA_CAULDRON,
			MAGMA_BLOCK,
			POINTED_DRIPSTONE,
			POWDER_SNOW,
			POWDER_SNOW_BUCKET,
			SOUL_FIRE,
			SWEET_BERRY_BUSH,
			WITHER_ROSE -> true
			else -> false
		}
	}

	val explosiveBlock = MaterialGroup {
		when (it) {
			in bed,
			RESPAWN_ANCHOR,
			TNT -> true
			else -> false
		}
	}

	val fallingBlock = MaterialGroup {
		when (it) {
			in anvil,
			in concretePowder,
			in sand,
			GRAVEL -> true
			else -> false
		}
	}

	val goldenApple = arrayOf(
		ENCHANTED_GOLDEN_APPLE,
		GOLDEN_APPLE
	)

	val itemFrame = arrayOf(
		GLOW_ITEM_FRAME,
		ITEM_FRAME
	)

	val rightClickPlaceableEntity = MaterialGroup {
		when (it) {
			in boat,
			in itemFrame,
			in minecart,
			ARMOR_STAND,
			END_CRYSTAL,
			LEAD,
			PAINTING -> true
			else -> false
		}
	}

	val sand = arrayOf(
		RED_SAND,
		SAND
	)

	val trappedOrUntrappedChest = arrayOf(
		CHEST,
		TRAPPED_CHEST
	)

	val minecart = arrayOf(
		CHEST_MINECART,
		COMMAND_BLOCK_MINECART,
		FURNACE_MINECART,
		HOPPER_MINECART,
		MINECART,
		TNT_MINECART
	)

	val potion = MaterialGroup {
		when (it) {
			in throwablePotion,
			POTION -> true
			else -> false
		}
	}

	val throwablePotion = arrayOf(
		LINGERING_POTION,
		SPLASH_POTION
	)

	val throwableDamagingProjectile = MaterialGroup {
		when (it) {
			in throwablePotion,
			EGG,
			SNOWBALL,
			TRIDENT -> true
			else -> false
		}
	}

	val bowFireableDamagingProjectile = MaterialGroup {
		when (it) {
			in bowFireableArrow,
			FIREWORK_ROCKET -> true
			else -> false
		}
	}

	val bowFireableArrow = arrayOf(
		ARROW,
		SPECTRAL_ARROW,
		TIPPED_ARROW
	)

	val nonRawUncutCopperBlock = arrayOf(
		COPPER_BLOCK,
		EXPOSED_COPPER,
		OXIDIZED_COPPER,
		WAXED_COPPER_BLOCK,
		WAXED_EXPOSED_COPPER,
		WAXED_OXIDIZED_COPPER,
		WAXED_WEATHERED_COPPER,
		WEATHERED_COPPER
	)

	val nonRawCutCopperBlock = arrayOf(
		CUT_COPPER,
		EXPOSED_CUT_COPPER,
		OXIDIZED_CUT_COPPER,
		WAXED_CUT_COPPER,
		WAXED_EXPOSED_CUT_COPPER,
		WAXED_OXIDIZED_CUT_COPPER,
		WAXED_WEATHERED_CUT_COPPER,
		WEATHERED_CUT_COPPER
	)

	val nonRawCopperBlock = MaterialGroup {
		when (it) {
			in nonRawUncutCopperBlock,
			in nonRawCutCopperBlock -> true
			else -> false
		}
	}

	val quartzBlock = arrayOf(
		CHISELED_QUARTZ_BLOCK,
		SMOOTH_QUARTZ,
		QUARTZ_BLOCK,
		QUARTZ_PILLAR
	)

	val rawMetal = arrayOf(
		RAW_COPPER,
		RAW_GOLD,
		RAW_IRON
	)

	val rawMetalBlock = arrayOf(
		RAW_COPPER_BLOCK,
		RAW_GOLD_BLOCK,
		RAW_IRON_BLOCK
	)

	val metalOrMineralBlock = MaterialGroup {
		when (it) {
			in nonRawCopperBlock,
			in quartzBlock,
			in rawMetalBlock,
			in amethystBlock,
			COAL_BLOCK,
			DIAMOND_BLOCK,
			EMERALD_BLOCK,
			GOLD_BLOCK,
			IRON_BLOCK,
			LAPIS_BLOCK,
			NETHERITE_BLOCK,
			REDSTONE_BLOCK -> true
			else -> false
		}
	}

	val stoneOre = arrayOf(
		COAL_ORE,
		COPPER_ORE,
		DIAMOND_ORE,
		EMERALD_ORE,
		GOLD_ORE,
		IRON_ORE,
		LAPIS_ORE,
		REDSTONE_ORE
	)

	val deepslateOre = arrayOf(
		DEEPSLATE_COAL_ORE,
		DEEPSLATE_COPPER_ORE,
		DEEPSLATE_DIAMOND_ORE,
		DEEPSLATE_EMERALD_ORE,
		DEEPSLATE_GOLD_ORE,
		DEEPSLATE_IRON_ORE,
		DEEPSLATE_LAPIS_ORE,
		DEEPSLATE_REDSTONE_ORE
	)

	val netherrackOre = arrayOf(
		NETHER_GOLD_ORE,
		NETHER_QUARTZ_ORE
	)

	val netherOre = MaterialGroup {
		when (it) {
			in netherrackOre,
			ANCIENT_DEBRIS -> true
			else -> false
		}
	}

	val overworldOre = MaterialGroup {
		when (it) {
			in stoneOre,
			in deepslateOre -> true
			else -> false
		}
	}

	val ore = MaterialGroup {
		when (it) {
			in netherOre,
			in overworldOre -> true
			else -> false
		}
	}

	val spawnEgg = arrayOf(
		AXOLOTL_SPAWN_EGG,
		BAT_SPAWN_EGG,
		BEE_SPAWN_EGG,
		BLAZE_SPAWN_EGG,
		CAT_SPAWN_EGG,
		CAVE_SPIDER_SPAWN_EGG,
		CHICKEN_SPAWN_EGG,
		COD_SPAWN_EGG,
		COW_SPAWN_EGG,
		CREEPER_SPAWN_EGG,
		DOLPHIN_SPAWN_EGG,
		DONKEY_SPAWN_EGG,
		DRAGON_EGG,
		DROWNED_SPAWN_EGG,
		ELDER_GUARDIAN_SPAWN_EGG,
		ENDERMAN_SPAWN_EGG,
		ENDERMITE_SPAWN_EGG,
		EVOKER_SPAWN_EGG,
		FOX_SPAWN_EGG,
		GHAST_SPAWN_EGG,
		GLOW_SQUID_SPAWN_EGG,
		GOAT_SPAWN_EGG,
		GUARDIAN_SPAWN_EGG,
		HOGLIN_SPAWN_EGG,
		HORSE_SPAWN_EGG,
		HUSK_SPAWN_EGG,
		LLAMA_SPAWN_EGG,
		MAGMA_CUBE_SPAWN_EGG,
		MOOSHROOM_SPAWN_EGG,
		MULE_SPAWN_EGG,
		OCELOT_SPAWN_EGG,
		PANDA_SPAWN_EGG,
		PARROT_SPAWN_EGG,
		PHANTOM_SPAWN_EGG,
		PIGLIN_BRUTE_SPAWN_EGG,
		PIGLIN_SPAWN_EGG,
		PILLAGER_SPAWN_EGG,
		POLAR_BEAR_SPAWN_EGG,
		PUFFERFISH_SPAWN_EGG,
		RABBIT_SPAWN_EGG,
		RAVAGER_SPAWN_EGG,
		SALMON_SPAWN_EGG,
		SHEEP_SPAWN_EGG,
		SHULKER_SPAWN_EGG,
		SILVERFISH_SPAWN_EGG,
		SKELETON_HORSE_SPAWN_EGG,
		SKELETON_SPAWN_EGG,
		SLIME_SPAWN_EGG,
		SPIDER_SPAWN_EGG,
		SQUID_SPAWN_EGG,
		STRAY_SPAWN_EGG,
		STRIDER_SPAWN_EGG,
		TRADER_LLAMA_SPAWN_EGG,
		TROPICAL_FISH_SPAWN_EGG,
		TURTLE_SPAWN_EGG,
		VEX_SPAWN_EGG,
		VILLAGER_SPAWN_EGG,
		VINDICATOR_SPAWN_EGG,
		WANDERING_TRADER_SPAWN_EGG,
		WITCH_SPAWN_EGG,
		WITHER_SKELETON_SPAWN_EGG,
		WOLF_SPAWN_EGG,
		ZOGLIN_SPAWN_EGG,
		ZOMBIE_HORSE_SPAWN_EGG,
		ZOMBIE_VILLAGER_SPAWN_EGG,
		ZOMBIFIED_PIGLIN_SPAWN_EGG
	)

	val mossyCobblestone = arrayOf(
		MOSSY_COBBLESTONE,
		MOSSY_COBBLESTONE_SLAB,
		MOSSY_COBBLESTONE_STAIRS,
		MOSSY_COBBLESTONE_WALL
	)

	val infestedBlock = arrayOf(
		INFESTED_CHISELED_STONE_BRICKS,
		INFESTED_COBBLESTONE,
		INFESTED_CRACKED_STONE_BRICKS,
		INFESTED_DEEPSLATE,
		INFESTED_MOSSY_STONE_BRICKS,
		INFESTED_STONE,
		INFESTED_STONE_BRICKS
	)

	val mossyStoneBricks = arrayOf(
		INFESTED_MOSSY_STONE_BRICKS,
		MOSSY_STONE_BRICK_SLAB,
		MOSSY_STONE_BRICK_STAIRS,
		MOSSY_STONE_BRICK_WALL,
		MOSSY_STONE_BRICKS
	)

	val hasMossySides = MaterialGroup {
		when (it) {
			in mossyCobblestone,
			in mossyStoneBricks -> true
			else -> false
		}
	}

	val vine = arrayOf(
		CAVE_VINES,
		CAVE_VINES_PLANT,
		TWISTING_VINES,
		VINE,
		WEEPING_VINES
	)

	val fishBucket = arrayOf(
		AXOLOTL_BUCKET,
		COD_BUCKET,
		PUFFERFISH_BUCKET,
		SALMON_BUCKET,
		TROPICAL_FISH_BUCKET
	)

	val nonEmptyBucket = MaterialGroup {
		when (it) {
			in fishBucket,
			LAVA_BUCKET,
			POWDER_SNOW_BUCKET,
			WATER_BUCKET -> true
			else -> false
		}
	}

	val bucket = MaterialGroup {
		when (it) {
			in nonEmptyBucket,
			BUCKET -> true
			else -> false
		}
	}

	val doubleFlower = arrayOf(
		LILAC,
		PEONY,
		ROSE_BUSH,
		SUNFLOWER
	)

	val unstrippedOverworldLog = arrayOf(
		ACACIA_LOG,
		BIRCH_LOG,
		DARK_OAK_LOG,
		JUNGLE_LOG,
		OAK_LOG,
		SPRUCE_LOG
	)

	val strippedOverworldLog = arrayOf(
		STRIPPED_ACACIA_LOG,
		STRIPPED_BIRCH_LOG,
		STRIPPED_DARK_OAK_LOG,
		STRIPPED_JUNGLE_LOG,
		STRIPPED_OAK_LOG,
		STRIPPED_SPRUCE_LOG
	)

	val unstrippedOverworldWood = arrayOf(
		ACACIA_WOOD,
		BIRCH_WOOD,
		DARK_OAK_WOOD,
		JUNGLE_WOOD,
		OAK_WOOD,
		SPRUCE_WOOD
	)

	val strippedOverworldWood = arrayOf(
		STRIPPED_ACACIA_WOOD,
		STRIPPED_BIRCH_WOOD,
		STRIPPED_DARK_OAK_WOOD,
		STRIPPED_JUNGLE_WOOD,
		STRIPPED_OAK_WOOD,
		STRIPPED_SPRUCE_WOOD
	)

	val unstrippedNetherStem = arrayOf(
		CRIMSON_STEM,
		WARPED_STEM
	)

	val strippedNetherStem = arrayOf(
		STRIPPED_CRIMSON_STEM,
		STRIPPED_WARPED_STEM
	)

	val unstrippedNetherHyphae = arrayOf(
		CRIMSON_HYPHAE,
		WARPED_HYPHAE
	)

	val strippedNetherHyphae = arrayOf(
		STRIPPED_CRIMSON_HYPHAE,
		STRIPPED_WARPED_HYPHAE
	)

	val strippedOrUnstrippedOverworldLog = MaterialGroup {
		when (it) {
			in strippedOverworldLog,
			in unstrippedOverworldLog -> true
			else -> false
		}
	}

	val strippedOrUnstrippedOverworldWood = MaterialGroup {
		when (it) {
			in strippedOverworldWood,
			in unstrippedOverworldWood -> true
			else -> false
		}
	}

	val strippedOrUnstrippedNetherStem = MaterialGroup {
		when (it) {
			in strippedNetherStem,
			in unstrippedNetherStem -> true
			else -> false
		}
	}

	val strippedOrUnstrippedNetherHyphae = MaterialGroup {
		when (it) {
			in strippedNetherHyphae,
			in unstrippedNetherHyphae -> true
			else -> false
		}
	}

	val strippedOrUnstrippedLogOrStem = MaterialGroup {
		when (it) {
			in strippedOrUnstrippedOverworldLog,
			in strippedOrUnstrippedNetherStem -> true
			else -> false
		}
	}

	val strippedOrUnstrippedWoodOrHyphae = MaterialGroup {
		when (it) {
			in strippedOrUnstrippedOverworldLog,
			in strippedOrUnstrippedNetherStem -> true
			else -> false
		}
	}

	val strippedOrUnstrippedLogOrWoodOrStemOrHyphae = MaterialGroup {
		when (it) {
			in strippedOrUnstrippedLogOrStem,
			in strippedOrUnstrippedWoodOrHyphae -> true
			else -> false
		}
	}

	val overworldSapling = arrayOf(
		ACACIA_SAPLING,
		BIRCH_SAPLING,
		DARK_OAK_SAPLING,
		JUNGLE_SAPLING,
		OAK_SAPLING,
		SPRUCE_SAPLING
	)

	val boat = arrayOf(
		ACACIA_BOAT,
		BIRCH_BOAT,
		DARK_OAK_BOAT,
		JUNGLE_BOAT,
		OAK_BOAT,
		SPRUCE_BOAT
	)

	val leaves = arrayOf(
		ACACIA_LEAVES,
		AZALEA_LEAVES,
		BIRCH_LEAVES,
		DARK_OAK_LEAVES,
		FLOWERING_AZALEA_LEAVES,
		JUNGLE_LEAVES,
		OAK_LEAVES,
		SPRUCE_LEAVES
	)

	val planks = arrayOf(
		ACACIA_PLANKS,
		BIRCH_PLANKS,
		CRIMSON_PLANKS,
		DARK_OAK_PLANKS,
		JUNGLE_PLANKS,
		OAK_PLANKS,
		SPRUCE_PLANKS,
		WARPED_PLANKS
	)

	val woodenSlab = arrayOf(
		ACACIA_SLAB,
		BIRCH_SLAB,
		CRIMSON_SLAB,
		DARK_OAK_SLAB,
		JUNGLE_SLAB,
		OAK_SLAB,
		PETRIFIED_OAK_SLAB,
		SPRUCE_SLAB,
		WARPED_SLAB
	)

	val blackstoneSlab = arrayOf(
		BLACKSTONE_SLAB,
		POLISHED_BLACKSTONE_BRICK_SLAB,
		POLISHED_BLACKSTONE_SLAB
	)

	val sandstoneSlab = arrayOf(
		CUT_RED_SANDSTONE_SLAB,
		CUT_SANDSTONE_SLAB,
		RED_SANDSTONE_SLAB,
		SANDSTONE_SLAB,
		SMOOTH_RED_SANDSTONE_SLAB,
		SMOOTH_SANDSTONE_SLAB
	)

	val quartzSlab = arrayOf(
		SMOOTH_QUARTZ_SLAB,
		QUARTZ_SLAB
	)

	val stoneBrickSlab = arrayOf(
		MOSSY_STONE_BRICK_SLAB,
		STONE_BRICK_SLAB
	)

	val cobblestoneSlab = arrayOf(
		COBBLESTONE_SLAB,
		MOSSY_COBBLESTONE_SLAB
	)

	val prismarineSlab = arrayOf(
		DARK_PRISMARINE_SLAB,
		PRISMARINE_BRICK_SLAB,
		PRISMARINE_SLAB
	)

	val netherBrickSlab = arrayOf(
		NETHER_BRICK_SLAB,
		RED_NETHER_BRICK_SLAB
	)

	val deepslateSlab = arrayOf(
		COBBLED_DEEPSLATE_SLAB,
		DEEPSLATE_BRICK_SLAB,
		DEEPSLATE_TILE_SLAB,
		POLISHED_DEEPSLATE_SLAB
	)

	val copperSlab = arrayOf(
		CUT_COPPER_SLAB,
		EXPOSED_CUT_COPPER_SLAB,
		OXIDIZED_CUT_COPPER_SLAB,
		WAXED_CUT_COPPER_SLAB,
		WAXED_EXPOSED_CUT_COPPER_SLAB,
		WAXED_OXIDIZED_CUT_COPPER_SLAB,
		WAXED_WEATHERED_CUT_COPPER_SLAB,
		WEATHERED_CUT_COPPER_SLAB
	)

	val nonWoodenSlab = MaterialGroup {
		when (it) {
			in blackstoneSlab,
			in sandstoneSlab,
			in quartzSlab,
			in stoneBrickSlab,
			in cobblestoneSlab,
			in prismarineSlab,
			in netherBrickSlab,
			in deepslateSlab,
			in copperSlab,
			ANDESITE_SLAB,
			BRICK_SLAB,
			DIORITE_SLAB,
			END_STONE_BRICK_SLAB,
			GRANITE_SLAB,
			POLISHED_ANDESITE_SLAB,
			POLISHED_DIORITE_SLAB,
			POLISHED_GRANITE_SLAB,
			PURPUR_SLAB,
			SMOOTH_STONE_SLAB,
			STONE_SLAB -> true
			else -> false
		}
	}

	val slab = MaterialGroup {
		when (it) {
			in woodenSlab,
			in nonWoodenSlab -> true
			else -> false
		}
	}

	val horseArmor = arrayOf(
		DIAMOND_HORSE_ARMOR,
		GOLDEN_HORSE_ARMOR,
		IRON_HORSE_ARMOR,
		LEATHER_HORSE_ARMOR
	)

	val woodenStairs = arrayOf(
		ACACIA_STAIRS,
		BIRCH_STAIRS,
		CRIMSON_STAIRS,
		DARK_OAK_STAIRS,
		JUNGLE_STAIRS,
		OAK_STAIRS,
		SPRUCE_STAIRS,
		WARPED_STAIRS
	)

	val blackstoneStairs = arrayOf(
		BLACKSTONE_STAIRS,
		POLISHED_BLACKSTONE_BRICK_STAIRS,
		POLISHED_BLACKSTONE_STAIRS
	)

	val sandstoneStairs = arrayOf(
		SANDSTONE_STAIRS,
		SMOOTH_RED_SANDSTONE_STAIRS,
		SMOOTH_SANDSTONE_STAIRS,
		RED_SANDSTONE_STAIRS
	)

	val quartzStairs = arrayOf(
		SMOOTH_QUARTZ_STAIRS,
		QUARTZ_STAIRS
	)

	val stoneBrickStairs = arrayOf(
		MOSSY_STONE_BRICK_STAIRS,
		STONE_BRICK_STAIRS
	)

	val cobblestoneStairs = arrayOf(
		COBBLESTONE_STAIRS,
		MOSSY_COBBLESTONE_STAIRS
	)

	val prismarineStairs = arrayOf(
		DARK_PRISMARINE_STAIRS,
		PRISMARINE_BRICK_STAIRS,
		PRISMARINE_STAIRS
	)

	val netherBrickStairs = arrayOf(
		NETHER_BRICK_STAIRS,
		RED_NETHER_BRICK_STAIRS
	)

	val deepslateStairs = arrayOf(
		COBBLED_DEEPSLATE_STAIRS,
		DEEPSLATE_BRICK_STAIRS,
		DEEPSLATE_TILE_STAIRS,
		POLISHED_DEEPSLATE_STAIRS
	)

	val copperStairs = arrayOf(
		CUT_COPPER_STAIRS,
		EXPOSED_CUT_COPPER_STAIRS,
		OXIDIZED_CUT_COPPER_STAIRS,
		WAXED_CUT_COPPER_STAIRS,
		WAXED_EXPOSED_CUT_COPPER_STAIRS,
		WAXED_OXIDIZED_CUT_COPPER_STAIRS,
		WAXED_WEATHERED_CUT_COPPER_STAIRS,
		WEATHERED_CUT_COPPER_STAIRS
	)

	val nonWoodenStairs = MaterialGroup {
		when (it) {
			in blackstoneStairs,
			in sandstoneStairs,
			in quartzStairs,
			in stoneBrickStairs,
			in cobblestoneStairs,
			in prismarineStairs,
			in netherBrickStairs,
			in deepslateStairs,
			in copperStairs,
			ANDESITE_STAIRS,
			BRICK_STAIRS,
			DIORITE_STAIRS,
			END_STONE_BRICK_STAIRS,
			GRANITE_STAIRS,
			POLISHED_ANDESITE_STAIRS,
			POLISHED_DIORITE_STAIRS,
			POLISHED_GRANITE_STAIRS,
			PURPUR_STAIRS,
			STONE_STAIRS -> true
			else -> false
		}
	}

	val stairs = MaterialGroup {
		when (it) {
			in woodenStairs,
			in nonWoodenStairs -> true
			else -> false
		}
	}

	val woodenTrapdoor = arrayOf(
		ACACIA_TRAPDOOR,
		BIRCH_TRAPDOOR,
		CRIMSON_TRAPDOOR,
		DARK_OAK_TRAPDOOR,
		JUNGLE_TRAPDOOR,
		OAK_TRAPDOOR,
		SPRUCE_TRAPDOOR,
		WARPED_TRAPDOOR
	)

	val trapdoor = MaterialGroup {
		when (it) {
			in woodenTrapdoor,
			IRON_TRAPDOOR -> true
			else -> false
		}
	}

	val woodenDoor = arrayOf(
		ACACIA_DOOR,
		BIRCH_DOOR,
		CRIMSON_DOOR,
		DARK_OAK_DOOR,
		JUNGLE_DOOR,
		OAK_DOOR,
		SPRUCE_DOOR,
		WARPED_DOOR
	)

	val door = MaterialGroup {
		when (it) {
			in woodenDoor,
			IRON_DOOR -> true
			else -> false
		}
	}

	val woodenButton = arrayOf(
		ACACIA_BUTTON,
		BIRCH_BUTTON,
		CRIMSON_BUTTON,
		DARK_OAK_BUTTON,
		JUNGLE_BUTTON,
		OAK_BUTTON,
		SPRUCE_BUTTON,
		WARPED_BUTTON
	)

	val stoneButton = arrayOf(
		STONE_BUTTON,
		POLISHED_BLACKSTONE_BUTTON
	)

	val button = MaterialGroup {
		when (it) {
			in woodenButton,
			in stoneButton -> true
			else -> false
		}
	}

	val woodenPressurePlate = arrayOf(
		ACACIA_PRESSURE_PLATE,
		BIRCH_PRESSURE_PLATE,
		CRIMSON_PRESSURE_PLATE,
		DARK_OAK_PRESSURE_PLATE,
		JUNGLE_PRESSURE_PLATE,
		OAK_PRESSURE_PLATE,
		SPRUCE_PRESSURE_PLATE,
		WARPED_PRESSURE_PLATE
	)

	val stonePressurePlate = arrayOf(
		STONE_PRESSURE_PLATE,
		POLISHED_BLACKSTONE_PRESSURE_PLATE
	)

	val weightedPressurePlate = arrayOf(
		LIGHT_WEIGHTED_PRESSURE_PLATE,
		HEAVY_WEIGHTED_PRESSURE_PLATE
	)

	val pressurePlate = MaterialGroup {
		when (it) {
			in woodenPressurePlate,
			in stonePressurePlate,
			in weightedPressurePlate -> true
			else -> false
		}
	}

	val fenceGate = arrayOf(
		ACACIA_FENCE_GATE,
		BIRCH_FENCE_GATE,
		CRIMSON_FENCE_GATE,
		DARK_OAK_FENCE_GATE,
		JUNGLE_FENCE_GATE,
		OAK_FENCE_GATE,
		SPRUCE_FENCE_GATE,
		WARPED_FENCE_GATE
	)

	val woodenFence = arrayOf(
		ACACIA_FENCE,
		BIRCH_FENCE,
		CRIMSON_FENCE,
		DARK_OAK_FENCE,
		JUNGLE_FENCE,
		OAK_FENCE,
		SPRUCE_FENCE,
		WARPED_FENCE
	)

	val fence = MaterialGroup {
		when (it) {
			in woodenFence,
			NETHER_BRICK_FENCE -> true
			else -> false
		}
	}

	val wallSign = arrayOf(
		ACACIA_WALL_SIGN,
		BIRCH_WALL_SIGN,
		CRIMSON_WALL_SIGN,
		DARK_OAK_WALL_SIGN,
		JUNGLE_WALL_SIGN,
		OAK_WALL_SIGN,
		SPRUCE_WALL_SIGN,
		WARPED_WALL_SIGN
	)

	val standingSign = arrayOf(
		ACACIA_SIGN,
		BIRCH_SIGN,
		CRIMSON_SIGN,
		DARK_OAK_SIGN,
		JUNGLE_SIGN,
		OAK_SIGN,
		SPRUCE_SIGN,
		WARPED_SIGN
	)

	val standingOrWallSign = MaterialGroup {
		when (it) {
			in standingSign,
			in wallSign -> true
			else -> false
		}
	}

	val anvil = arrayOf(
		ANVIL,
		CHIPPED_ANVIL,
		DAMAGED_ANVIL
	)

	val concrete = arrayOf(
		BLACK_CONCRETE,
		BLUE_CONCRETE,
		BROWN_CONCRETE,
		CYAN_CONCRETE,
		GRAY_CONCRETE,
		GREEN_CONCRETE,
		LIGHT_BLUE_CONCRETE,
		LIGHT_GRAY_CONCRETE,
		LIME_CONCRETE,
		MAGENTA_CONCRETE,
		ORANGE_CONCRETE,
		PINK_CONCRETE,
		PURPLE_CONCRETE,
		RED_CONCRETE,
		WHITE_CONCRETE,
		YELLOW_CONCRETE
	)

	val concretePowder = arrayOf(
		BLACK_CONCRETE_POWDER,
		BLUE_CONCRETE_POWDER,
		BROWN_CONCRETE_POWDER,
		CYAN_CONCRETE_POWDER,
		GRAY_CONCRETE_POWDER,
		GREEN_CONCRETE_POWDER,
		LIGHT_BLUE_CONCRETE_POWDER,
		LIGHT_GRAY_CONCRETE_POWDER,
		LIME_CONCRETE_POWDER,
		MAGENTA_CONCRETE_POWDER,
		ORANGE_CONCRETE_POWDER,
		PINK_CONCRETE_POWDER,
		PURPLE_CONCRETE_POWDER,
		RED_CONCRETE_POWDER,
		WHITE_CONCRETE_POWDER,
		YELLOW_CONCRETE_POWDER
	)

	val dye = arrayOf(
		BLACK_DYE,
		BLUE_DYE,
		BROWN_DYE,
		CYAN_DYE,
		GRAY_DYE,
		GREEN_DYE,
		LIGHT_BLUE_DYE,
		LIGHT_GRAY_DYE,
		LIME_DYE,
		MAGENTA_DYE,
		ORANGE_DYE,
		PINK_DYE,
		PURPLE_DYE,
		RED_DYE,
		WHITE_DYE,
		YELLOW_DYE
	)

	val woolCarpet = arrayOf(
		BLACK_CARPET,
		BLUE_CARPET,
		BROWN_CARPET,
		CYAN_CARPET,
		GRAY_CARPET,
		GREEN_CARPET,
		LIGHT_BLUE_CARPET,
		LIGHT_GRAY_CARPET,
		LIME_CARPET,
		MAGENTA_CARPET,
		ORANGE_CARPET,
		PINK_CARPET,
		PURPLE_CARPET,
		RED_CARPET,
		WHITE_CARPET,
		YELLOW_CARPET
	)

	val carpet1_17 = MaterialGroup {
		when (it) {
			in woolCarpet,
			MOSS_CARPET -> true
			else -> false
		}
	}

	val wool = arrayOf(
		BLACK_WOOL,
		BLUE_WOOL,
		BROWN_WOOL,
		CYAN_WOOL,
		GRAY_WOOL,
		GREEN_WOOL,
		LIGHT_BLUE_WOOL,
		LIGHT_GRAY_WOOL,
		LIME_WOOL,
		MAGENTA_WOOL,
		ORANGE_WOOL,
		PINK_WOOL,
		PURPLE_WOOL,
		RED_WOOL,
		WHITE_WOOL,
		YELLOW_WOOL
	)

	val candle = arrayOf(
		BLACK_CANDLE,
		BLUE_CANDLE,
		BROWN_CANDLE,
		CYAN_CANDLE,
		GRAY_CANDLE,
		GREEN_CANDLE,
		LIGHT_BLUE_CANDLE,
		LIGHT_GRAY_CANDLE,
		LIME_CANDLE,
		MAGENTA_CANDLE,
		ORANGE_CANDLE,
		PINK_CANDLE,
		PURPLE_CANDLE,
		RED_CANDLE,
		WHITE_CANDLE,
		YELLOW_CANDLE
	)

	val bed = arrayOf(
		BLACK_BED,
		BLUE_BED,
		BROWN_BED,
		CYAN_BED,
		GRAY_BED,
		GREEN_BED,
		LIGHT_BLUE_BED,
		LIGHT_GRAY_BED,
		LIME_BED,
		MAGENTA_BED,
		ORANGE_BED,
		PINK_BED,
		PURPLE_BED,
		RED_BED,
		WHITE_BED,
		YELLOW_BED
	)

	val unglazedColoredTerracotta = arrayOf(
		BLACK_TERRACOTTA,
		BLUE_TERRACOTTA,
		BROWN_TERRACOTTA,
		CYAN_TERRACOTTA,
		GRAY_TERRACOTTA,
		GREEN_TERRACOTTA,
		LIGHT_BLUE_TERRACOTTA,
		LIGHT_GRAY_TERRACOTTA,
		LIME_TERRACOTTA,
		MAGENTA_TERRACOTTA,
		ORANGE_TERRACOTTA,
		PINK_TERRACOTTA,
		PURPLE_TERRACOTTA,
		RED_TERRACOTTA,
		WHITE_TERRACOTTA,
		YELLOW_TERRACOTTA
	)

	val glazedTerracotta = arrayOf(
		BLACK_GLAZED_TERRACOTTA,
		BLUE_GLAZED_TERRACOTTA,
		BROWN_GLAZED_TERRACOTTA,
		CYAN_GLAZED_TERRACOTTA,
		GRAY_GLAZED_TERRACOTTA,
		GREEN_GLAZED_TERRACOTTA,
		LIGHT_BLUE_GLAZED_TERRACOTTA,
		LIGHT_GRAY_GLAZED_TERRACOTTA,
		LIME_GLAZED_TERRACOTTA,
		MAGENTA_GLAZED_TERRACOTTA,
		ORANGE_GLAZED_TERRACOTTA,
		PINK_GLAZED_TERRACOTTA,
		PURPLE_GLAZED_TERRACOTTA,
		RED_GLAZED_TERRACOTTA,
		WHITE_GLAZED_TERRACOTTA,
		YELLOW_GLAZED_TERRACOTTA
	)

	val unglazedColoredOrUncoloredTerracotta = MaterialGroup {
		when (it) {
			in unglazedColoredTerracotta,
			TERRACOTTA -> true
			else -> false
		}
	}

	val glazedOrUnglazedColoredTerracotta = MaterialGroup {
		when (it) {
			in glazedTerracotta,
			in unglazedColoredTerracotta -> true
			else -> false
		}
	}

	val glazedOrUnglazedColoredOrUncoloredTerracotta = MaterialGroup {
		when (it) {
			in glazedOrUnglazedColoredTerracotta,
			TERRACOTTA -> true
			else -> false
		}
	}

	val coloredShulkerBox = arrayOf(
		BLACK_SHULKER_BOX,
		BLUE_SHULKER_BOX,
		BROWN_SHULKER_BOX,
		CYAN_SHULKER_BOX,
		GRAY_SHULKER_BOX,
		GREEN_SHULKER_BOX,
		LIGHT_BLUE_SHULKER_BOX,
		LIGHT_GRAY_SHULKER_BOX,
		LIME_SHULKER_BOX,
		MAGENTA_SHULKER_BOX,
		ORANGE_SHULKER_BOX,
		PINK_SHULKER_BOX,
		PURPLE_SHULKER_BOX,
		RED_SHULKER_BOX,
		WHITE_SHULKER_BOX,
		YELLOW_SHULKER_BOX
	)

	val shulkerBox = MaterialGroup {
		when (it) {
			in coloredShulkerBox,
			SHULKER_BOX -> true
			else -> false
		}
	}

	val dyedCandle = arrayOf(
		BLACK_CANDLE,
		BLUE_CANDLE,
		BROWN_CANDLE,
		CYAN_CANDLE,
		GRAY_CANDLE,
		GREEN_CANDLE,
		LIGHT_BLUE_CANDLE,
		LIGHT_GRAY_CANDLE,
		LIME_CANDLE,
		MAGENTA_CANDLE,
		ORANGE_CANDLE,
		PINK_CANDLE,
		PURPLE_CANDLE,
		RED_CANDLE,
		WHITE_CANDLE,
		YELLOW_CANDLE
	)

	val dyedOrUndyedCandle = MaterialGroup {
		when (it) {
			in dyedCandle,
			CANDLE -> true
			else -> false
		}
	}

	val dyedCandleCake = arrayOf(
		BLACK_CANDLE_CAKE,
		BLUE_CANDLE_CAKE,
		BROWN_CANDLE_CAKE,
		CYAN_CANDLE_CAKE,
		GRAY_CANDLE_CAKE,
		GREEN_CANDLE_CAKE,
		LIGHT_BLUE_CANDLE_CAKE,
		LIGHT_GRAY_CANDLE_CAKE,
		LIME_CANDLE_CAKE,
		MAGENTA_CANDLE_CAKE,
		ORANGE_CANDLE_CAKE,
		PINK_CANDLE_CAKE,
		PURPLE_CANDLE_CAKE,
		RED_CANDLE_CAKE,
		WHITE_CANDLE_CAKE,
		YELLOW_CANDLE_CAKE
	)

	val dyedOrUndyedCandleCake = MaterialGroup {
		when (it) {
			in dyedCandle,
			CANDLE_CAKE -> true
			else -> false
		}
	}

	val growingOrGrownAmethystCluster = arrayOf(
		AMETHYST_CLUSTER,
		LARGE_AMETHYST_BUD,
		MEDIUM_AMETHYST_BUD,
		SMALL_AMETHYST_BUD
	)

	val amethystBlock = arrayOf(
		AMETHYST_BLOCK,
		BUDDING_AMETHYST
	)

	val bannerPattern = arrayOf(
		CREEPER_BANNER_PATTERN,
		FLOWER_BANNER_PATTERN,
		GLOBE_BANNER_PATTERN,
		MOJANG_BANNER_PATTERN,
		PIGLIN_BANNER_PATTERN,
		SKULL_BANNER_PATTERN
	)

	val wallBanner = arrayOf(
		BLACK_WALL_BANNER,
		BLUE_WALL_BANNER,
		BROWN_WALL_BANNER,
		CYAN_WALL_BANNER,
		GRAY_WALL_BANNER,
		GREEN_WALL_BANNER,
		LIGHT_BLUE_WALL_BANNER,
		LIGHT_GRAY_WALL_BANNER,
		LIME_WALL_BANNER,
		MAGENTA_WALL_BANNER,
		ORANGE_WALL_BANNER,
		PINK_WALL_BANNER,
		PURPLE_WALL_BANNER,
		RED_WALL_BANNER,
		WHITE_WALL_BANNER,
		YELLOW_WALL_BANNER
	)

	val standingBanner = arrayOf(
		BLACK_BANNER,
		BLUE_BANNER,
		BROWN_BANNER,
		CYAN_BANNER,
		GRAY_BANNER,
		GREEN_BANNER,
		LIGHT_BLUE_BANNER,
		LIGHT_GRAY_BANNER,
		LIME_BANNER,
		MAGENTA_BANNER,
		ORANGE_BANNER,
		PINK_BANNER,
		PURPLE_BANNER,
		RED_BANNER,
		WHITE_BANNER,
		YELLOW_BANNER
	)

	val stainedGlass = arrayOf(
		BLACK_STAINED_GLASS,
		BLUE_STAINED_GLASS,
		BROWN_STAINED_GLASS,
		CYAN_STAINED_GLASS,
		GRAY_STAINED_GLASS,
		GREEN_STAINED_GLASS,
		LIGHT_BLUE_STAINED_GLASS,
		LIGHT_GRAY_STAINED_GLASS,
		LIME_STAINED_GLASS,
		MAGENTA_STAINED_GLASS,
		ORANGE_STAINED_GLASS,
		PINK_STAINED_GLASS,
		PURPLE_STAINED_GLASS,
		RED_STAINED_GLASS,
		WHITE_STAINED_GLASS,
		YELLOW_STAINED_GLASS
	)

	val stainedOrRegularGlass = MaterialGroup {
		when (it) {
			in stainedGlass,
			GLASS -> true
			else -> false
		}
	}

	val stainedOrTintedOrRegularGlass = MaterialGroup {
		when (it) {
			in stainedOrRegularGlass,
			TINTED_GLASS -> true
			else -> false
		}
	}

	val stainedGlassPane = arrayOf(
		BLACK_STAINED_GLASS_PANE,
		BLUE_STAINED_GLASS_PANE,
		BROWN_STAINED_GLASS_PANE,
		CYAN_STAINED_GLASS_PANE,
		GRAY_STAINED_GLASS_PANE,
		GREEN_STAINED_GLASS_PANE,
		LIGHT_BLUE_STAINED_GLASS_PANE,
		LIGHT_GRAY_STAINED_GLASS_PANE,
		LIME_STAINED_GLASS_PANE,
		MAGENTA_STAINED_GLASS_PANE,
		ORANGE_STAINED_GLASS_PANE,
		PINK_STAINED_GLASS_PANE,
		PURPLE_STAINED_GLASS_PANE,
		RED_STAINED_GLASS_PANE,
		WHITE_STAINED_GLASS_PANE,
		YELLOW_STAINED_GLASS_PANE
	)

	val stainedOrRegularGlassPane = MaterialGroup {
		when (it) {
			in stainedGlassPane,
			GLASS_PANE -> true
			else -> false
		}
	}

	val standingOrWallBanner = MaterialGroup {
		when (it) {
			in standingBanner,
			in wallBanner -> true
			else -> false
		}
	}

	val mushroomBlock = arrayOf(
		BROWN_MUSHROOM_BLOCK,
		RED_MUSHROOM_BLOCK
	)

	val mushroomBlockOrStem = MaterialGroup {
		when (it) {
			in mushroomBlock,
			MUSHROOM_STEM -> true
			else -> false
		}
	}

	val aliveCoral = arrayOf(
		BRAIN_CORAL,
		BUBBLE_CORAL,
		FIRE_CORAL,
		HORN_CORAL,
		TUBE_CORAL
	)

	val deadCoral = arrayOf(
		DEAD_BRAIN_CORAL,
		DEAD_BUBBLE_CORAL,
		DEAD_FIRE_CORAL,
		DEAD_HORN_CORAL,
		DEAD_TUBE_CORAL
	)

	val aliveOrDeadCoral = MaterialGroup {
		when (it) {
			in aliveCoral,
			in deadCoral -> true
			else -> false
		}
	}

	val aliveCoralBlock = arrayOf(
		BRAIN_CORAL_BLOCK,
		BUBBLE_CORAL_BLOCK,
		FIRE_CORAL_BLOCK,
		HORN_CORAL_BLOCK,
		TUBE_CORAL_BLOCK
	)

	val deadCoralBlock = arrayOf(
		DEAD_BRAIN_CORAL_BLOCK,
		DEAD_BUBBLE_CORAL_BLOCK,
		DEAD_FIRE_CORAL_BLOCK,
		DEAD_HORN_CORAL_BLOCK,
		DEAD_TUBE_CORAL_BLOCK
	)

	val aliveOrDeadCoralBlock = MaterialGroup {
		when (it) {
			in aliveCoralBlock,
			in deadCoralBlock -> true
			else -> false
		}
	}

	val aliveCoralNonWallFan = arrayOf(
		BRAIN_CORAL_FAN,
		BUBBLE_CORAL_FAN,
		FIRE_CORAL_FAN,
		HORN_CORAL_FAN,
		TUBE_CORAL_FAN
	)

	val deadCoralNonWallFan = arrayOf(
		DEAD_BRAIN_CORAL_FAN,
		DEAD_BUBBLE_CORAL_FAN,
		DEAD_FIRE_CORAL_FAN,
		DEAD_HORN_CORAL_FAN,
		DEAD_TUBE_CORAL_FAN
	)

	val aliveOrDeadCoralNonWallFan = MaterialGroup {
		when (it) {
			in aliveCoralNonWallFan,
			in deadCoralNonWallFan -> true
			else -> false
		}
	}

	val aliveCoralWallFan = arrayOf(
		BRAIN_CORAL_WALL_FAN,
		BUBBLE_CORAL_WALL_FAN,
		FIRE_CORAL_WALL_FAN,
		HORN_CORAL_WALL_FAN,
		TUBE_CORAL_WALL_FAN
	)

	val deadCoralWallFan = arrayOf(
		DEAD_BRAIN_CORAL_WALL_FAN,
		DEAD_BUBBLE_CORAL_WALL_FAN,
		DEAD_FIRE_CORAL_WALL_FAN,
		DEAD_HORN_CORAL_WALL_FAN,
		DEAD_TUBE_CORAL_WALL_FAN
	)

	val aliveOrDeadCoralWallFan = MaterialGroup {
		when (it) {
			in aliveCoralWallFan,
			in deadCoralWallFan -> true
			else -> false
		}
	}

	val campfire = arrayOf(
		CAMPFIRE,
		SOUL_CAMPFIRE
	)

	val wallHead = arrayOf(
		CREEPER_WALL_HEAD,
		DRAGON_WALL_HEAD,
		PLAYER_WALL_HEAD,
		SKELETON_WALL_SKULL,
		WITHER_SKELETON_WALL_SKULL,
		ZOMBIE_WALL_HEAD
	)

	val groundHead = arrayOf(
		CREEPER_HEAD,
		DRAGON_HEAD,
		PLAYER_HEAD,
		SKELETON_SKULL,
		WITHER_SKELETON_SKULL,
		ZOMBIE_HEAD
	)

	val groundOrWallHead = MaterialGroup {
		when (it) {
			in groundHead,
			in wallHead -> true
			else -> false
		}
	}

	val wallTorch = arrayOf(
		REDSTONE_WALL_TORCH,
		SOUL_WALL_TORCH,
		WALL_TORCH
	)

	val standingTorch = arrayOf(
		REDSTONE_TORCH,
		SOUL_TORCH,
		TORCH
	)

	val standingOrWallTorch = MaterialGroup {
		when (it) {
			in standingTorch,
			in wallTorch -> true
			else -> false
		}
	}

	val fire = arrayOf(
		FIRE,
		SOUL_FIRE
	)

	val lantern = arrayOf(
		LANTERN,
		SOUL_LANTERN
	)

	val itemframe = arrayOf(
		GLOW_ITEM_FRAME,
		ITEM_FRAME
	)

	val blackstoneWall = arrayOf(
		BLACKSTONE_WALL,
		POLISHED_BLACKSTONE_BRICK_WALL,
		POLISHED_BLACKSTONE_WALL
	)

	val sandstoneWall = arrayOf(
		RED_SANDSTONE_WALL,
		SANDSTONE_WALL
	)

	val stoneBrickWall = arrayOf(
		MOSSY_STONE_BRICK_WALL,
		STONE_BRICK_WALL
	)

	val cobblestoneWall = arrayOf(
		COBBLESTONE_WALL,
		MOSSY_COBBLESTONE_WALL
	)

	val netherBrickWall = arrayOf(
		NETHER_BRICK_WALL,
		RED_NETHER_BRICK_WALL
	)

	val deepslateWall = arrayOf(
		COBBLED_DEEPSLATE_WALL,
		DEEPSLATE_BRICK_WALL,
		DEEPSLATE_TILE_WALL,
		POLISHED_DEEPSLATE_WALL
	)

	val wall = MaterialGroup {
		when (it) {
			in blackstoneWall,
			in sandstoneWall,
			in stoneBrickWall,
			in cobblestoneWall,
			in netherBrickWall,
			in deepslateWall,
			ANDESITE_WALL,
			BRICK_WALL,
			DIORITE_WALL,
			END_STONE_BRICK_WALL,
			GRANITE_WALL,
			PRISMARINE_WALL -> true
			else -> false
		}
	}

	val rail = arrayOf(
		ACTIVATOR_RAIL,
		DETECTOR_RAIL,
		POWERED_RAIL,
		RAIL
	)

	/*
	public static boolean isDirtWithCover(Material type) {
		switch (type) {
		case GRASS_BLOCK:
		case MYCELIUM:
		case PODZOL:
			return true;
		default:
			return false;
		}
	}
	*/
	val musicDisc = MaterialGroup {
		when (it) {
			MUSIC_DISC_11,
			MUSIC_DISC_13,
			MUSIC_DISC_BLOCKS,
			MUSIC_DISC_CAT,
			MUSIC_DISC_CHIRP,
			MUSIC_DISC_FAR,
			MUSIC_DISC_MALL,
			MUSIC_DISC_MELLOHI,
			MUSIC_DISC_PIGSTEP,
			MUSIC_DISC_STAL,
			MUSIC_DISC_STRAD,
			MUSIC_DISC_WAIT,
			MUSIC_DISC_WARD -> true
			else -> false
		}
	}

	val helmet = arrayOf(
		LEATHER_HELMET,
		IRON_HELMET,
		CHAINMAIL_HELMET,
		GOLDEN_HELMET,
		DIAMOND_HELMET,
		NETHERITE_HELMET,
		TURTLE_HELMET
	)

	val nonElytraChestplate = arrayOf(
		LEATHER_CHESTPLATE,
		IRON_CHESTPLATE,
		CHAINMAIL_CHESTPLATE,
		GOLDEN_CHESTPLATE,
		DIAMOND_CHESTPLATE,
		NETHERITE_CHESTPLATE
	)

	val chestplate = MaterialGroup {
		when (it) {
			in nonElytraChestplate,
			ELYTRA -> true
			else -> false
		}
	}

	val leggings = arrayOf(
		LEATHER_LEGGINGS,
		IRON_LEGGINGS,
		CHAINMAIL_LEGGINGS,
		GOLDEN_LEGGINGS,
		DIAMOND_LEGGINGS,
		NETHERITE_LEGGINGS
	)

	val boots = arrayOf(
		LEATHER_BOOTS,
		IRON_BOOTS,
		CHAINMAIL_BOOTS,
		GOLDEN_BOOTS,
		DIAMOND_BOOTS,
		NETHERITE_BOOTS
	)

	val armor = MaterialGroup {
		when (it) {
			in helmet,
			in chestplate,
			in leggings,
			in boots,
			in groundOrWallHead -> true
			else -> false
		}
	}

	val sword = arrayOf(
		WOODEN_SWORD,
		STONE_SWORD,
		IRON_SWORD,
		GOLDEN_SWORD,
		DIAMOND_SWORD,
		NETHERITE_SWORD
	)

	val hoe = arrayOf(
		WOODEN_HOE,
		STONE_HOE,
		IRON_HOE,
		GOLDEN_HOE,
		DIAMOND_HOE,
		NETHERITE_HOE
	)

	val axe = arrayOf(
		WOODEN_AXE,
		STONE_AXE,
		IRON_AXE,
		GOLDEN_AXE,
		DIAMOND_AXE,
		NETHERITE_AXE
	)

	val pickaxe = arrayOf(
		WOODEN_PICKAXE,
		STONE_PICKAXE,
		IRON_PICKAXE,
		GOLDEN_PICKAXE,
		DIAMOND_PICKAXE,
		NETHERITE_PICKAXE
	)

	val shovel = arrayOf(
		WOODEN_SHOVEL,
		STONE_SHOVEL,
		IRON_SHOVEL,
		GOLDEN_SHOVEL,
		DIAMOND_SHOVEL,
		NETHERITE_SHOVEL
	)

	val bowOrCrossbow = arrayOf(
		BOW,
		CROSSBOW
	)

	val mainHandWeapon = MaterialGroup {
		when (it) {
			in sword,
			in hoe,
			in axe,
			in pickaxe,
			in shovel,
			in bowOrCrossbow,
			TRIDENT -> true
			else -> false
		}
	}

	val offHandCombatItem = arrayOf(
		SHIELD,
		TOTEM_OF_UNDYING
	)

	val commandBlock = arrayOf(
		COMMAND_BLOCK,
		REPEATING_COMMAND_BLOCK,
		CHAIN_COMMAND_BLOCK
	)

	val structureRelatedBlock = arrayOf(
		JIGSAW,
		STRUCTURE_BLOCK,
		STRUCTURE_VOID
	)

	val illegal = MaterialGroup {
		when (it) {
			in commandBlock,
			in structureRelatedBlock,
			BARRIER,
			COMMAND_BLOCK_MINECART,
			DEBUG_STICK,
			LIGHT -> true
			else -> false
		}
	}
}