package org.sucraft.core.common.material;

import org.bukkit.Material;

import lombok.experimental.UtilityClass;

// TODO MUST POTENTIALLY BE UPDATED EVERY VERSION
@UtilityClass
public class MaterialGroups {
	
	public boolean isGoldenApple(Material type) {
		switch (type) {
		case ENCHANTED_GOLDEN_APPLE:
		case GOLDEN_APPLE:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isFallingBlock(Material type) {
		if (isAnvil(type)) {
			return true;
		}
		if (isConcretePowder(type)) {
			return true;
		}
		if (isSand(type)) {
			return true;
		}
		return type == Material.GRAVEL;
	}
	
	public boolean isSand(Material type) {
		switch (type) {
		case RED_SAND:
		case SAND:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isExplosiveBlock(Material type) {
		if (isBed(type)) {
			return true;
		}
		switch (type) {
		case RESPAWN_ANCHOR:
		case TNT:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isTrappedOrUntrappedChest(Material type) {
		switch (type) {
		case CHEST:
		case TRAPPED_CHEST:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isDamageDealingBlock(Material type) {
		if (isFallingBlock(type)) {
			return true;
		}
		switch (type) {
		case CACTUS:
		case FIRE:
		case POINTED_DRIPSTONE:
		case LAVA:
		case LAVA_CAULDRON:
		case MAGMA_BLOCK:
		case POWDER_SNOW:
		case POWDER_SNOW_BUCKET:
		case SOUL_FIRE:
		case SWEET_BERRY_BUSH:
		case WITHER_ROSE:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isRightClickPlaceableEntity(Material type) {
		if (isBoat(type)) {
			return true;
		}
		if (isMinecart(type)) {
			return true;
		}
		if (isItemFrame(type)) {
			return true;
		}
		switch (type) {
		case ARMOR_STAND:
		case END_CRYSTAL:
		case LEAD:
		case PAINTING:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isItemFrame(Material type) {
		switch (type) {
		case GLOW_ITEM_FRAME:
		case ITEM_FRAME:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isMinecart(Material type) {
		switch (type) {
		case CHEST_MINECART:
		case COMMAND_BLOCK_MINECART:
		case FURNACE_MINECART:
		case HOPPER_MINECART:
		case MINECART:
		case TNT_MINECART:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isPotion(Material type) {
		return isThrowablePotion(type) || type == Material.POTION;
	}
	
	public boolean isThrowablePotion(Material type) {
		switch (type) {
		case LINGERING_POTION:
		case SPLASH_POTION:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isThrowableDamagingProjectile(Material type) {
		if (isThrowablePotion(type)) {
			return true;
		}
		switch (type) {
		case EGG:
		case SNOWBALL:
		case TRIDENT:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isBowFireableDamagingProjectile(Material type) {
		return isBowFireableArrow(type) || type == Material.FIREWORK_ROCKET;
	}
	
	public boolean isBowFireableArrow(Material type) {
		switch (type) {
		case ARROW:
		case SPECTRAL_ARROW:
		case TIPPED_ARROW:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isNonRawUncutCopperBlock(Material type) {
		switch (type) {
		case COPPER_BLOCK:
		case EXPOSED_COPPER:
		case OXIDIZED_COPPER:
		case WAXED_COPPER_BLOCK:
		case WAXED_EXPOSED_COPPER:
		case WAXED_OXIDIZED_COPPER:
		case WAXED_WEATHERED_COPPER:
		case WEATHERED_COPPER:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isNonRawCutCopperBlock(Material type) {
		switch (type) {
		case CUT_COPPER:
		case EXPOSED_CUT_COPPER:
		case OXIDIZED_CUT_COPPER:
		case WAXED_CUT_COPPER:
		case WAXED_EXPOSED_CUT_COPPER:
		case WAXED_OXIDIZED_CUT_COPPER:
		case WAXED_WEATHERED_CUT_COPPER:
		case WEATHERED_CUT_COPPER:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isNonRawCopperBlock(Material type) {
		return isNonRawUncutCopperBlock(type) || isNonRawCutCopperBlock(type);
	}
	
	public boolean isQuartzBlock(Material type) {
		switch (type) {
		case CHISELED_QUARTZ_BLOCK:
		case SMOOTH_QUARTZ:
		case QUARTZ_BLOCK:
		case QUARTZ_PILLAR:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isRawMetal(Material type) {
		switch (type) {
		case RAW_COPPER:
		case RAW_GOLD:
		case RAW_IRON:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isRawMetalBlock(Material type) {
		switch (type) {
		case RAW_COPPER_BLOCK:
		case RAW_GOLD_BLOCK:
		case RAW_IRON_BLOCK:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isMetalOrMineralBlock(Material type) {
		if (isNonRawCopperBlock(type)) {
			return true;
		}
		if (isQuartzBlock(type)) {
			return true;
		}
		if (isRawMetalBlock(type)) {
			return true;
		}
		if (isAmethystBlock(type)) {
			return true;
		}
		switch (type) {
		case COAL_BLOCK:
		case DIAMOND_BLOCK:
		case EMERALD_BLOCK:
		case GOLD_BLOCK:
		case IRON_BLOCK:
		case LAPIS_BLOCK:
		case NETHERITE_BLOCK:
		case REDSTONE_BLOCK:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isStoneOre(Material type) {
		switch (type) {
		case COAL_ORE:
		case COPPER_ORE:
		case DIAMOND_ORE:
		case EMERALD_ORE:
		case GOLD_ORE:
		case IRON_ORE:
		case LAPIS_ORE:
		case REDSTONE_ORE:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isDeepslateOre(Material type) {
		switch (type) {
		case DEEPSLATE_COAL_ORE:
		case DEEPSLATE_COPPER_ORE:
		case DEEPSLATE_DIAMOND_ORE:
		case DEEPSLATE_EMERALD_ORE:
		case DEEPSLATE_GOLD_ORE:
		case DEEPSLATE_IRON_ORE:
		case DEEPSLATE_LAPIS_ORE:
		case DEEPSLATE_REDSTONE_ORE:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isNetherrackOre(Material type) {
		switch (type) {
		case NETHER_GOLD_ORE:
		case NETHER_QUARTZ_ORE:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isNetherOre(Material type) {
		return isNetherrackOre(type) || type == Material.ANCIENT_DEBRIS;
	}
	
	public boolean isOverworldOre(Material type) {
		return isStoneOre(type) || isDeepslateOre(type);
	}
	
	public boolean isOre(Material type) {
		return isNetherOre(type) || isOverworldOre(type);
	}
	
	public boolean isSpawnEgg(Material type) {
		switch (type) {
		case AXOLOTL_SPAWN_EGG:
		case BAT_SPAWN_EGG:
		case BEE_SPAWN_EGG:
		case BLAZE_SPAWN_EGG:
		case CAT_SPAWN_EGG:
		case CAVE_SPIDER_SPAWN_EGG:
		case CHICKEN_SPAWN_EGG:
		case COD_SPAWN_EGG:
		case COW_SPAWN_EGG:
		case CREEPER_SPAWN_EGG:
		case DOLPHIN_SPAWN_EGG:
		case DONKEY_SPAWN_EGG:
		case DRAGON_EGG:
		case DROWNED_SPAWN_EGG:
		case ELDER_GUARDIAN_SPAWN_EGG:
		case ENDERMAN_SPAWN_EGG:
		case ENDERMITE_SPAWN_EGG:
		case EVOKER_SPAWN_EGG:
		case FOX_SPAWN_EGG:
		case GHAST_SPAWN_EGG:
		case GLOW_SQUID_SPAWN_EGG:
		case GOAT_SPAWN_EGG:
		case GUARDIAN_SPAWN_EGG:
		case HOGLIN_SPAWN_EGG:
		case HORSE_SPAWN_EGG:
		case HUSK_SPAWN_EGG:
		case LLAMA_SPAWN_EGG:
		case MAGMA_CUBE_SPAWN_EGG:
		case MOOSHROOM_SPAWN_EGG:
		case MULE_SPAWN_EGG:
		case OCELOT_SPAWN_EGG:
		case PANDA_SPAWN_EGG:
		case PARROT_SPAWN_EGG:
		case PHANTOM_SPAWN_EGG:
		case PIGLIN_BRUTE_SPAWN_EGG:
		case PIGLIN_SPAWN_EGG:
		case PILLAGER_SPAWN_EGG:
		case POLAR_BEAR_SPAWN_EGG:
		case PUFFERFISH_SPAWN_EGG:
		case RABBIT_SPAWN_EGG:
		case RAVAGER_SPAWN_EGG:
		case SALMON_SPAWN_EGG:
		case SHEEP_SPAWN_EGG:
		case SHULKER_SPAWN_EGG:
		case SILVERFISH_SPAWN_EGG:
		case SKELETON_HORSE_SPAWN_EGG:
		case SKELETON_SPAWN_EGG:
		case SLIME_SPAWN_EGG:
		case SPIDER_SPAWN_EGG:
		case SQUID_SPAWN_EGG:
		case STRAY_SPAWN_EGG:
		case STRIDER_SPAWN_EGG:
		case TRADER_LLAMA_SPAWN_EGG:
		case TROPICAL_FISH_SPAWN_EGG:
		case TURTLE_SPAWN_EGG:
		case VEX_SPAWN_EGG:
		case VILLAGER_SPAWN_EGG:
		case VINDICATOR_SPAWN_EGG:
		case WANDERING_TRADER_SPAWN_EGG:
		case WITCH_SPAWN_EGG:
		case WITHER_SKELETON_SPAWN_EGG:
		case WOLF_SPAWN_EGG:
		case ZOGLIN_SPAWN_EGG:
		case ZOMBIE_HORSE_SPAWN_EGG:
		case ZOMBIE_VILLAGER_SPAWN_EGG:
		case ZOMBIFIED_PIGLIN_SPAWN_EGG:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isMossyCobblestone(Material type) {
		switch (type) {
		case MOSSY_COBBLESTONE:
		case MOSSY_COBBLESTONE_SLAB:
		case MOSSY_COBBLESTONE_STAIRS:
		case MOSSY_COBBLESTONE_WALL:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isInfestedBlock(Material type) {
		switch (type) {
		case INFESTED_CHISELED_STONE_BRICKS:
		case INFESTED_COBBLESTONE:
		case INFESTED_CRACKED_STONE_BRICKS:
		case INFESTED_DEEPSLATE:
		case INFESTED_MOSSY_STONE_BRICKS:
		case INFESTED_STONE:
		case INFESTED_STONE_BRICKS:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isMossyStoneBricks(Material type) {
		switch (type) {
		case INFESTED_MOSSY_STONE_BRICKS:
		case MOSSY_STONE_BRICK_SLAB:
		case MOSSY_STONE_BRICK_STAIRS:
		case MOSSY_STONE_BRICK_WALL:
		case MOSSY_STONE_BRICKS:
			return true;
		default:
			return false;
		}
	}
	
	public boolean hasMossySides(Material type) {
		return isMossyCobblestone(type) || isMossyStoneBricks(type);
	}
	
	public boolean isVine(Material type) {
		switch (type) {
		case CAVE_VINES:
		case CAVE_VINES_PLANT:
		case TWISTING_VINES:
		case VINE:
		case WEEPING_VINES:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isFishBucket(Material type) {
		switch (type) {
		case AXOLOTL_BUCKET:
		case COD_BUCKET:
		case PUFFERFISH_BUCKET:
		case SALMON_BUCKET:
		case TROPICAL_FISH_BUCKET:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isNonEmptyBucket(Material type) {
		if (isFishBucket(type)) {
			return true;
		}
		switch (type) {
		case LAVA_BUCKET:
		case POWDER_SNOW_BUCKET:
		case WATER_BUCKET:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isBucket(Material type) {
		return isNonEmptyBucket(type) || type == Material.BUCKET;
	}
	
	public boolean isDoubleFlower(Material type) {
		switch (type) {
		case LILAC:
		case PEONY:
		case ROSE_BUSH:
		case SUNFLOWER:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isUnstrippedOverworldLog(Material type) {
		switch (type) {
		case ACACIA_LOG:
		case BIRCH_LOG:
		case DARK_OAK_LOG:
		case JUNGLE_LOG:
		case OAK_LOG:
		case SPRUCE_LOG:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isStrippedOverworldLog(Material type) {
		switch (type) {
		case STRIPPED_ACACIA_LOG:
		case STRIPPED_BIRCH_LOG:
		case STRIPPED_DARK_OAK_LOG:
		case STRIPPED_JUNGLE_LOG:
		case STRIPPED_OAK_LOG:
		case STRIPPED_SPRUCE_LOG:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isUnstrippedOverworldWood(Material type) {
		switch (type) {
		case ACACIA_WOOD:
		case BIRCH_WOOD:
		case DARK_OAK_WOOD:
		case JUNGLE_WOOD:
		case OAK_WOOD:
		case SPRUCE_WOOD:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isStrippedOverworldWood(Material type) {
		switch (type) {
		case STRIPPED_ACACIA_WOOD:
		case STRIPPED_BIRCH_WOOD:
		case STRIPPED_DARK_OAK_WOOD:
		case STRIPPED_JUNGLE_WOOD:
		case STRIPPED_OAK_WOOD:
		case STRIPPED_SPRUCE_WOOD:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isUnstrippedNetherStem(Material type) {
		switch (type) {
		case CRIMSON_STEM:
		case WARPED_STEM:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isStrippedNetherStem(Material type) {
		switch (type) {
		case STRIPPED_CRIMSON_STEM:
		case STRIPPED_WARPED_STEM:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isUnstrippedNetherHyphae(Material type) {
		switch (type) {
		case CRIMSON_HYPHAE:
		case WARPED_HYPHAE:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isStrippedNetherHyphae(Material type) {
		switch (type) {
		case STRIPPED_CRIMSON_HYPHAE:
		case STRIPPED_WARPED_HYPHAE:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isStrippedOrUnstrippedOverworldLog(Material type) {
		return isStrippedOverworldLog(type) || isUnstrippedOverworldLog(type);
	}
	
	public boolean isStrippedOrUnstrippedOverworldWood(Material type) {
		return isStrippedOverworldWood(type) || isUnstrippedOverworldWood(type);
	}
	
	public boolean isStrippedOrUnstrippedNetherStem(Material type) {
		return isStrippedNetherStem(type) || isUnstrippedNetherStem(type);
	}
	
	public boolean isStrippedOrUnstrippedNetherHyphae(Material type) {
		return isStrippedNetherHyphae(type) || isUnstrippedNetherHyphae(type);
	}
	
	public boolean isStrippedOrUnstrippedLogOrStem(Material type) {
		return isStrippedOrUnstrippedOverworldLog(type) || isStrippedOrUnstrippedNetherStem(type);
	}
	
	public boolean isStrippedOrUnstrippedWoodOrHyphae(Material type) {
		return isStrippedOrUnstrippedOverworldLog(type) || isStrippedOrUnstrippedNetherStem(type);
	}
	
	public boolean isStrippedOrUnstrippedLogOrWoodOrStemOrHyphae(Material type) {
		return isStrippedOrUnstrippedLogOrStem(type) || isStrippedOrUnstrippedWoodOrHyphae(type);
	}
	
	public boolean isOverworldSapling(Material type) {
		switch (type) {
		case ACACIA_SAPLING:
		case BIRCH_SAPLING:
		case DARK_OAK_SAPLING:
		case JUNGLE_SAPLING:
		case OAK_SAPLING:
		case SPRUCE_SAPLING:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isBoat(Material type) {
		switch (type) {
		case ACACIA_BOAT:
		case BIRCH_BOAT:
		case DARK_OAK_BOAT:
		case JUNGLE_BOAT:
		case OAK_BOAT:
		case SPRUCE_BOAT:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isLeaves(Material type) {
		switch (type) {
		case ACACIA_LEAVES:
		case AZALEA_LEAVES:
		case BIRCH_LEAVES:
		case DARK_OAK_LEAVES:
		case FLOWERING_AZALEA_LEAVES:
		case JUNGLE_LEAVES:
		case OAK_LEAVES:
		case SPRUCE_LEAVES:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isPlanks(Material type) {
		switch (type) {
		case ACACIA_PLANKS:
		case BIRCH_PLANKS:
		case CRIMSON_PLANKS:
		case DARK_OAK_PLANKS:
		case JUNGLE_PLANKS:
		case OAK_PLANKS:
		case SPRUCE_PLANKS:
		case WARPED_PLANKS:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isWoodenSlab(Material type) {
		switch (type) {
		case ACACIA_SLAB:
		case BIRCH_SLAB:
		case CRIMSON_SLAB:
		case DARK_OAK_SLAB:
		case JUNGLE_SLAB:
		case OAK_SLAB:
		case PETRIFIED_OAK_SLAB:
		case SPRUCE_SLAB:
		case WARPED_SLAB:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isBlackstoneSlab(Material type) {
		switch (type) {
		case BLACKSTONE_SLAB:
		case POLISHED_BLACKSTONE_BRICK_SLAB:
		case POLISHED_BLACKSTONE_SLAB:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isSandstoneSlab(Material type) {
		switch (type) {
		case CUT_RED_SANDSTONE_SLAB:
		case CUT_SANDSTONE_SLAB:
		case RED_SANDSTONE_SLAB:
		case SANDSTONE_SLAB:
		case SMOOTH_RED_SANDSTONE_SLAB:
		case SMOOTH_SANDSTONE_SLAB:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isQuartzSlab(Material type) {
		switch (type) {
		case SMOOTH_QUARTZ_SLAB:
		case QUARTZ_SLAB:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isStoneBrickSlab(Material type) {
		switch (type) {
		case MOSSY_STONE_BRICK_SLAB:
		case STONE_BRICK_SLAB:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isCobblestoneSlab(Material type) {
		switch (type) {
		case COBBLESTONE_SLAB:
		case MOSSY_COBBLESTONE_SLAB:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isPrismarineSlab(Material type) {
		switch (type) {
		case DARK_PRISMARINE_SLAB:
		case PRISMARINE_BRICK_SLAB:
		case PRISMARINE_SLAB:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isNetherBrickSlab(Material type) {
		switch (type) {
		case NETHER_BRICK_SLAB:
		case RED_NETHER_BRICK_SLAB:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isDeepslateSlab(Material type) {
		switch (type) {
		case COBBLED_DEEPSLATE_SLAB:
		case DEEPSLATE_BRICK_SLAB:
		case DEEPSLATE_TILE_SLAB:
		case POLISHED_DEEPSLATE_SLAB:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isCopperSlab(Material type) {
		switch (type) {
		case CUT_COPPER_SLAB:
		case EXPOSED_CUT_COPPER_SLAB:
		case OXIDIZED_CUT_COPPER_SLAB:
		case WAXED_CUT_COPPER_SLAB:
		case WAXED_EXPOSED_CUT_COPPER_SLAB:
		case WAXED_OXIDIZED_CUT_COPPER_SLAB:
		case WAXED_WEATHERED_CUT_COPPER_SLAB:
		case WEATHERED_CUT_COPPER_SLAB:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isNonWoodenSlab(Material type) {
		if (isBlackstoneSlab(type)) {
			return true;
		}
		if (isSandstoneSlab(type)) {
			return true;
		}
		if (isQuartzSlab(type)) {
			return true;
		}
		if (isStoneBrickSlab(type)) {
			return true;
		}
		if (isCobblestoneSlab(type)) {
			return true;
		}
		if (isPrismarineSlab(type)) {
			return true;
		}
		if (isNetherBrickSlab(type)) {
			return true;
		}
		if (isDeepslateSlab(type)) {
			return true;
		}
		if (isCopperSlab(type)) {
			return true;
		}
		switch (type) {
		case ANDESITE_SLAB:
		case BRICK_SLAB:
		case DIORITE_SLAB:
		case END_STONE_BRICK_SLAB:
		case GRANITE_SLAB:
		case POLISHED_ANDESITE_SLAB:
		case POLISHED_DIORITE_SLAB:
		case POLISHED_GRANITE_SLAB:
		case PURPUR_SLAB:
		case SMOOTH_STONE_SLAB:
		case STONE_SLAB:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isSlab(Material type) {
		return isWoodenSlab(type) || isNonWoodenSlab(type);
	}
	
	public boolean isHorseArmor(Material type) {
		switch (type) {
		case DIAMOND_HORSE_ARMOR:
		case GOLDEN_HORSE_ARMOR:
		case IRON_HORSE_ARMOR:
		case LEATHER_HORSE_ARMOR:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isWoodenStairs(Material type) {
		switch (type) {
		case ACACIA_STAIRS:
		case BIRCH_STAIRS:
		case CRIMSON_STAIRS:
		case DARK_OAK_STAIRS:
		case JUNGLE_STAIRS:
		case OAK_STAIRS:
		case SPRUCE_STAIRS:
		case WARPED_STAIRS:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isBlackstoneStairs(Material type) {
		switch (type) {
		case BLACKSTONE_STAIRS:
		case POLISHED_BLACKSTONE_BRICK_STAIRS:
		case POLISHED_BLACKSTONE_STAIRS:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isSandstoneStairs(Material type) {
		switch (type) {
		case SANDSTONE_STAIRS:
		case SMOOTH_RED_SANDSTONE_STAIRS:
		case SMOOTH_SANDSTONE_STAIRS:
		case RED_SANDSTONE_STAIRS:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isQuartzStairs(Material type) {
		switch (type) {
		case SMOOTH_QUARTZ_STAIRS:
		case QUARTZ_STAIRS:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isStoneBrickStairs(Material type) {
		switch (type) {
		case MOSSY_STONE_BRICK_STAIRS:
		case STONE_BRICK_STAIRS:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isCobblestoneStairs(Material type) {
		switch (type) {
		case COBBLESTONE_STAIRS:
		case MOSSY_COBBLESTONE_STAIRS:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isPrismarineStairs(Material type) {
		switch (type) {
		case DARK_PRISMARINE_STAIRS:
		case PRISMARINE_BRICK_STAIRS:
		case PRISMARINE_STAIRS:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isNetherBrickStairs(Material type) {
		switch (type) {
		case NETHER_BRICK_STAIRS:
		case RED_NETHER_BRICK_STAIRS:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isDeepslateStairs(Material type) {
		switch (type) {
		case COBBLED_DEEPSLATE_STAIRS:
		case DEEPSLATE_BRICK_STAIRS:
		case DEEPSLATE_TILE_STAIRS:
		case POLISHED_DEEPSLATE_STAIRS:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isCopperStairs(Material type) {
		switch (type) {
		case CUT_COPPER_STAIRS:
		case EXPOSED_CUT_COPPER_STAIRS:
		case OXIDIZED_CUT_COPPER_STAIRS:
		case WAXED_CUT_COPPER_STAIRS:
		case WAXED_EXPOSED_CUT_COPPER_STAIRS:
		case WAXED_OXIDIZED_CUT_COPPER_STAIRS:
		case WAXED_WEATHERED_CUT_COPPER_STAIRS:
		case WEATHERED_CUT_COPPER_STAIRS:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isNonWoodenStairs(Material type) {
		if (isBlackstoneStairs(type)) {
			return true;
		}
		if (isSandstoneStairs(type)) {
			return true;
		}
		if (isQuartzStairs(type)) {
			return true;
		}
		if (isStoneBrickStairs(type)) {
			return true;
		}
		if (isCobblestoneStairs(type)) {
			return true;
		}
		if (isPrismarineStairs(type)) {
			return true;
		}
		if (isNetherBrickStairs(type)) {
			return true;
		}
		if (isDeepslateStairs(type)) {
			return true;
		}
		if (isCopperStairs(type)) {
			return true;
		}
		switch (type) {
		case ANDESITE_STAIRS:
		case BRICK_STAIRS:
		case DIORITE_STAIRS:
		case END_STONE_BRICK_STAIRS:
		case GRANITE_STAIRS:
		case POLISHED_ANDESITE_STAIRS:
		case POLISHED_DIORITE_STAIRS:
		case POLISHED_GRANITE_STAIRS:
		case PURPUR_STAIRS:
		case STONE_STAIRS:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isStairs(Material type) {
		return isWoodenStairs(type) || isNonWoodenStairs(type);
	}
	
	public boolean isWoodenTrapdoor(Material type) {
		switch (type) {
		case ACACIA_TRAPDOOR:
		case BIRCH_TRAPDOOR:
		case CRIMSON_TRAPDOOR:
		case DARK_OAK_TRAPDOOR:
		case JUNGLE_TRAPDOOR:
		case OAK_TRAPDOOR:
		case SPRUCE_TRAPDOOR:
		case WARPED_TRAPDOOR:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isTrapdoor(Material type) {
		return isWoodenTrapdoor(type) || type == Material.IRON_TRAPDOOR;
	}
	
	public boolean isWoodenDoor(Material type) {
		switch (type) {
		case ACACIA_DOOR:
		case BIRCH_DOOR:
		case CRIMSON_DOOR:
		case DARK_OAK_DOOR:
		case JUNGLE_DOOR:
		case OAK_DOOR:
		case SPRUCE_DOOR:
		case WARPED_DOOR:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isDoor(Material type) {
		return isWoodenDoor(type) || type == Material.IRON_DOOR;
	}
	
	public boolean isWoodenButton(Material type) {
		switch (type) {
		case ACACIA_BUTTON:
		case BIRCH_BUTTON:
		case CRIMSON_BUTTON:
		case DARK_OAK_BUTTON:
		case JUNGLE_BUTTON:
		case OAK_BUTTON:
		case SPRUCE_BUTTON:
		case WARPED_BUTTON:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isStoneButton(Material type) {
		switch (type) {
		case STONE_BUTTON:
		case POLISHED_BLACKSTONE_BUTTON:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isButton(Material type) {
		return isWoodenButton(type) || isStoneButton(type);
	}
	
	public boolean isWoodenPressurePlate(Material type) {
		switch (type) {
		case ACACIA_PRESSURE_PLATE:
		case BIRCH_PRESSURE_PLATE:
		case CRIMSON_PRESSURE_PLATE:
		case DARK_OAK_PRESSURE_PLATE:
		case JUNGLE_PRESSURE_PLATE:
		case OAK_PRESSURE_PLATE:
		case SPRUCE_PRESSURE_PLATE:
		case WARPED_PRESSURE_PLATE:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isStonePressurePlate(Material type) {
		switch (type) {
		case STONE_PRESSURE_PLATE:
		case POLISHED_BLACKSTONE_PRESSURE_PLATE:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isWeightedPressurePlate(Material type) {
		switch (type) {
		case LIGHT_WEIGHTED_PRESSURE_PLATE:
		case HEAVY_WEIGHTED_PRESSURE_PLATE:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isPressurePlate(Material type) {
		return isWoodenPressurePlate(type) || isStonePressurePlate(type) || isWeightedPressurePlate(type);
	}
	
	public boolean isFenceGate(Material type) {
		switch (type) {
		case ACACIA_FENCE_GATE:
		case BIRCH_FENCE_GATE:
		case CRIMSON_FENCE_GATE:
		case DARK_OAK_FENCE_GATE:
		case JUNGLE_FENCE_GATE:
		case OAK_FENCE_GATE:
		case SPRUCE_FENCE_GATE:
		case WARPED_FENCE_GATE:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isWoodenFence(Material type) {
		switch (type) {
		case ACACIA_FENCE:
		case BIRCH_FENCE:
		case CRIMSON_FENCE:
		case DARK_OAK_FENCE:
		case JUNGLE_FENCE:
		case OAK_FENCE:
		case SPRUCE_FENCE:
		case WARPED_FENCE:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isFence(Material type) {
		return isWoodenFence(type) || type == Material.NETHER_BRICK_FENCE;
	}
	
	public boolean isWallSign(Material type) {
		switch (type) {
		case ACACIA_WALL_SIGN:
		case BIRCH_WALL_SIGN:
		case CRIMSON_WALL_SIGN:
		case DARK_OAK_WALL_SIGN:
		case JUNGLE_WALL_SIGN:
		case OAK_WALL_SIGN:
		case SPRUCE_WALL_SIGN:
		case WARPED_WALL_SIGN:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isStandingSign(Material type) {
		switch (type) {
		case ACACIA_SIGN:
		case BIRCH_SIGN:
		case CRIMSON_SIGN:
		case DARK_OAK_SIGN:
		case JUNGLE_SIGN:
		case OAK_SIGN:
		case SPRUCE_SIGN:
		case WARPED_SIGN:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isStandingOrWallSign(Material type) {
		return isStandingSign(type) || isWallSign(type);
	}
	
	public boolean isAnvil(Material type) {
		switch (type) {
		case ANVIL:
		case CHIPPED_ANVIL:
		case DAMAGED_ANVIL:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isConcrete(Material type) {
		switch (type) {
		case BLACK_CONCRETE:
		case BLUE_CONCRETE:
		case BROWN_CONCRETE:
		case CYAN_CONCRETE:
		case GRAY_CONCRETE:
		case GREEN_CONCRETE:
		case LIGHT_BLUE_CONCRETE:
		case LIGHT_GRAY_CONCRETE:
		case LIME_CONCRETE:
		case MAGENTA_CONCRETE:
		case ORANGE_CONCRETE:
		case PINK_CONCRETE:
		case PURPLE_CONCRETE:
		case RED_CONCRETE:
		case WHITE_CONCRETE:
		case YELLOW_CONCRETE:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isConcretePowder(Material type) {
		switch (type) {
		case BLACK_CONCRETE_POWDER:
		case BLUE_CONCRETE_POWDER:
		case BROWN_CONCRETE_POWDER:
		case CYAN_CONCRETE_POWDER:
		case GRAY_CONCRETE_POWDER:
		case GREEN_CONCRETE_POWDER:
		case LIGHT_BLUE_CONCRETE_POWDER:
		case LIGHT_GRAY_CONCRETE_POWDER:
		case LIME_CONCRETE_POWDER:
		case MAGENTA_CONCRETE_POWDER:
		case ORANGE_CONCRETE_POWDER:
		case PINK_CONCRETE_POWDER:
		case PURPLE_CONCRETE_POWDER:
		case RED_CONCRETE_POWDER:
		case WHITE_CONCRETE_POWDER:
		case YELLOW_CONCRETE_POWDER:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isDye(Material type) {
		switch (type) {
		case BLACK_DYE:
		case BLUE_DYE:
		case BROWN_DYE:
		case CYAN_DYE:
		case GRAY_DYE:
		case GREEN_DYE:
		case LIGHT_BLUE_DYE:
		case LIGHT_GRAY_DYE:
		case LIME_DYE:
		case MAGENTA_DYE:
		case ORANGE_DYE:
		case PINK_DYE:
		case PURPLE_DYE:
		case RED_DYE:
		case WHITE_DYE:
		case YELLOW_DYE:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isWoolCarpet(Material type) {
		switch (type) {
		case BLACK_CARPET:
		case BLUE_CARPET:
		case BROWN_CARPET:
		case CYAN_CARPET:
		case GRAY_CARPET:
		case GREEN_CARPET:
		case LIGHT_BLUE_CARPET:
		case LIGHT_GRAY_CARPET:
		case LIME_CARPET:
		case MAGENTA_CARPET:
		case ORANGE_CARPET:
		case PINK_CARPET:
		case PURPLE_CARPET:
		case RED_CARPET:
		case WHITE_CARPET:
		case YELLOW_CARPET:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isCarpet1_17(Material type) {
		return isWoolCarpet(type) || type == Material.MOSS_CARPET;
	}
	
	public boolean isWool(Material type) {
		switch (type) {
		case BLACK_WOOL:
		case BLUE_WOOL:
		case BROWN_WOOL:
		case CYAN_WOOL:
		case GRAY_WOOL:
		case GREEN_WOOL:
		case LIGHT_BLUE_WOOL:
		case LIGHT_GRAY_WOOL:
		case LIME_WOOL:
		case MAGENTA_WOOL:
		case ORANGE_WOOL:
		case PINK_WOOL:
		case PURPLE_WOOL:
		case RED_WOOL:
		case WHITE_WOOL:
		case YELLOW_WOOL:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isCandle(Material type) {
		switch (type) {
		case BLACK_CANDLE:
		case BLUE_CANDLE:
		case BROWN_CANDLE:
		case CYAN_CANDLE:
		case GRAY_CANDLE:
		case GREEN_CANDLE:
		case LIGHT_BLUE_CANDLE:
		case LIGHT_GRAY_CANDLE:
		case LIME_CANDLE:
		case MAGENTA_CANDLE:
		case ORANGE_CANDLE:
		case PINK_CANDLE:
		case PURPLE_CANDLE:
		case RED_CANDLE:
		case WHITE_CANDLE:
		case YELLOW_CANDLE:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isBed(Material type) {
		switch (type) {
		case BLACK_BED:
		case BLUE_BED:
		case BROWN_BED:
		case CYAN_BED:
		case GRAY_BED:
		case GREEN_BED:
		case LIGHT_BLUE_BED:
		case LIGHT_GRAY_BED:
		case LIME_BED:
		case MAGENTA_BED:
		case ORANGE_BED:
		case PINK_BED:
		case PURPLE_BED:
		case RED_BED:
		case WHITE_BED:
		case YELLOW_BED:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isUnglazedColoredTerracotta(Material type) {
		switch (type) {
		case BLACK_TERRACOTTA:
		case BLUE_TERRACOTTA:
		case BROWN_TERRACOTTA:
		case CYAN_TERRACOTTA:
		case GRAY_TERRACOTTA:
		case GREEN_TERRACOTTA:
		case LIGHT_BLUE_TERRACOTTA:
		case LIGHT_GRAY_TERRACOTTA:
		case LIME_TERRACOTTA:
		case MAGENTA_TERRACOTTA:
		case ORANGE_TERRACOTTA:
		case PINK_TERRACOTTA:
		case PURPLE_TERRACOTTA:
		case RED_TERRACOTTA:
		case WHITE_TERRACOTTA:
		case YELLOW_TERRACOTTA:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isGlazedTerracotta(Material type) {
		switch (type) {
		case BLACK_GLAZED_TERRACOTTA:
		case BLUE_GLAZED_TERRACOTTA:
		case BROWN_GLAZED_TERRACOTTA:
		case CYAN_GLAZED_TERRACOTTA:
		case GRAY_GLAZED_TERRACOTTA:
		case GREEN_GLAZED_TERRACOTTA:
		case LIGHT_BLUE_GLAZED_TERRACOTTA:
		case LIGHT_GRAY_GLAZED_TERRACOTTA:
		case LIME_GLAZED_TERRACOTTA:
		case MAGENTA_GLAZED_TERRACOTTA:
		case ORANGE_GLAZED_TERRACOTTA:
		case PINK_GLAZED_TERRACOTTA:
		case PURPLE_GLAZED_TERRACOTTA:
		case RED_GLAZED_TERRACOTTA:
		case WHITE_GLAZED_TERRACOTTA:
		case YELLOW_GLAZED_TERRACOTTA:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isUnglazedColoredOrUncoloredTerracotta(Material type) {
		return isUnglazedColoredTerracotta(type) || type == Material.TERRACOTTA;
	}
	
	public boolean isGlazedOrUnglazedColoredTerracotta(Material type) {
		return isGlazedTerracotta(type) || isUnglazedColoredTerracotta(type);
	}
	
	public boolean isGlazedOrUnglazedColoredOrUncoloredTerracotta(Material type) {
		return isGlazedOrUnglazedColoredTerracotta(type) || type == Material.TERRACOTTA;
	}
	
	public boolean isColoredShulkerBox(Material type) {
		switch (type) {
		case BLACK_SHULKER_BOX:
		case BLUE_SHULKER_BOX:
		case BROWN_SHULKER_BOX:
		case CYAN_SHULKER_BOX:
		case GRAY_SHULKER_BOX:
		case GREEN_SHULKER_BOX:
		case LIGHT_BLUE_SHULKER_BOX:
		case LIGHT_GRAY_SHULKER_BOX:
		case LIME_SHULKER_BOX:
		case MAGENTA_SHULKER_BOX:
		case ORANGE_SHULKER_BOX:
		case PINK_SHULKER_BOX:
		case PURPLE_SHULKER_BOX:
		case RED_SHULKER_BOX:
		case WHITE_SHULKER_BOX:
		case YELLOW_SHULKER_BOX:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isShulkerBox(Material type) {
		return isColoredShulkerBox(type) || type == Material.SHULKER_BOX;
	}
	
	public boolean isDyedCandle(Material type) {
		switch (type) {
		case BLACK_CANDLE:
		case BLUE_CANDLE:
		case BROWN_CANDLE:
		case CYAN_CANDLE:
		case GRAY_CANDLE:
		case GREEN_CANDLE:
		case LIGHT_BLUE_CANDLE:
		case LIGHT_GRAY_CANDLE:
		case LIME_CANDLE:
		case MAGENTA_CANDLE:
		case ORANGE_CANDLE:
		case PINK_CANDLE:
		case PURPLE_CANDLE:
		case RED_CANDLE:
		case WHITE_CANDLE:
		case YELLOW_CANDLE:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isDyedOrUndyedCandle(Material type) {
		return isDyedCandle(type) || type == Material.CANDLE;
	}
	
	public boolean isDyedCandleCake(Material type) {
		switch (type) {
		case BLACK_CANDLE_CAKE:
		case BLUE_CANDLE_CAKE:
		case BROWN_CANDLE_CAKE:
		case CYAN_CANDLE_CAKE:
		case GRAY_CANDLE_CAKE:
		case GREEN_CANDLE_CAKE:
		case LIGHT_BLUE_CANDLE_CAKE:
		case LIGHT_GRAY_CANDLE_CAKE:
		case LIME_CANDLE_CAKE:
		case MAGENTA_CANDLE_CAKE:
		case ORANGE_CANDLE_CAKE:
		case PINK_CANDLE_CAKE:
		case PURPLE_CANDLE_CAKE:
		case RED_CANDLE_CAKE:
		case WHITE_CANDLE_CAKE:
		case YELLOW_CANDLE_CAKE:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isDyedOrUndyedCandleCake(Material type) {
		return isDyedCandle(type) || type == Material.CANDLE_CAKE;
	}
	
	public boolean isGrowingOrGrownAmethystCluster(Material type) {
		switch (type) {
		case AMETHYST_CLUSTER:
		case LARGE_AMETHYST_BUD:
		case MEDIUM_AMETHYST_BUD:
		case SMALL_AMETHYST_BUD:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isAmethystBlock(Material type) {
		switch (type) {
		case AMETHYST_BLOCK:
		case BUDDING_AMETHYST:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isBannerPattern(Material type) {
		switch (type) {
		case CREEPER_BANNER_PATTERN:
		case FLOWER_BANNER_PATTERN:
		case GLOBE_BANNER_PATTERN:
		case MOJANG_BANNER_PATTERN:
		case PIGLIN_BANNER_PATTERN:
		case SKULL_BANNER_PATTERN:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isWallBanner(Material type) {
		switch (type) {
		case BLACK_WALL_BANNER:
		case BLUE_WALL_BANNER:
		case BROWN_WALL_BANNER:
		case CYAN_WALL_BANNER:
		case GRAY_WALL_BANNER:
		case GREEN_WALL_BANNER:
		case LIGHT_BLUE_WALL_BANNER:
		case LIGHT_GRAY_WALL_BANNER:
		case LIME_WALL_BANNER:
		case MAGENTA_WALL_BANNER:
		case ORANGE_WALL_BANNER:
		case PINK_WALL_BANNER:
		case PURPLE_WALL_BANNER:
		case RED_WALL_BANNER:
		case WHITE_WALL_BANNER:
		case YELLOW_WALL_BANNER:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isStandingBanner(Material type) {
		switch (type) {
		case BLACK_BANNER:
		case BLUE_BANNER:
		case BROWN_BANNER:
		case CYAN_BANNER:
		case GRAY_BANNER:
		case GREEN_BANNER:
		case LIGHT_BLUE_BANNER:
		case LIGHT_GRAY_BANNER:
		case LIME_BANNER:
		case MAGENTA_BANNER:
		case ORANGE_BANNER:
		case PINK_BANNER:
		case PURPLE_BANNER:
		case RED_BANNER:
		case WHITE_BANNER:
		case YELLOW_BANNER:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isStainedGlass(Material type) {
		switch (type) {
		case BLACK_STAINED_GLASS:
		case BLUE_STAINED_GLASS:
		case BROWN_STAINED_GLASS:
		case CYAN_STAINED_GLASS:
		case GRAY_STAINED_GLASS:
		case GREEN_STAINED_GLASS:
		case LIGHT_BLUE_STAINED_GLASS:
		case LIGHT_GRAY_STAINED_GLASS:
		case LIME_STAINED_GLASS:
		case MAGENTA_STAINED_GLASS:
		case ORANGE_STAINED_GLASS:
		case PINK_STAINED_GLASS:
		case PURPLE_STAINED_GLASS:
		case RED_STAINED_GLASS:
		case WHITE_STAINED_GLASS:
		case YELLOW_STAINED_GLASS:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isStainedOrRegularGlass(Material type) {
		return isStainedGlass(type) || type == Material.GLASS;
	}
	
	public boolean isStainedOrTintedOrRegularGlass(Material type) {
		return isStainedOrRegularGlass(type) || type == Material.TINTED_GLASS;
	}
	
	public boolean isStainedGlassPane(Material type) {
		switch (type) {
		case BLACK_STAINED_GLASS_PANE:
		case BLUE_STAINED_GLASS_PANE:
		case BROWN_STAINED_GLASS_PANE:
		case CYAN_STAINED_GLASS_PANE:
		case GRAY_STAINED_GLASS_PANE:
		case GREEN_STAINED_GLASS_PANE:
		case LIGHT_BLUE_STAINED_GLASS_PANE:
		case LIGHT_GRAY_STAINED_GLASS_PANE:
		case LIME_STAINED_GLASS_PANE:
		case MAGENTA_STAINED_GLASS_PANE:
		case ORANGE_STAINED_GLASS_PANE:
		case PINK_STAINED_GLASS_PANE:
		case PURPLE_STAINED_GLASS_PANE:
		case RED_STAINED_GLASS_PANE:
		case WHITE_STAINED_GLASS_PANE:
		case YELLOW_STAINED_GLASS_PANE:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isStainedOrRegularGlassPane(Material type) {
		return isStainedGlassPane(type) || type == Material.GLASS_PANE;
	}
	
	public boolean isStandingOrWallBanner(Material type) {
		return isStandingBanner(type) || isWallBanner(type);
	}
	
	public boolean isMushroomBlock(Material type) {
		switch (type) {
		case BROWN_MUSHROOM_BLOCK:
		case RED_MUSHROOM_BLOCK:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isMushroomBlockOrStem(Material type) {
		return isMushroomBlock(type) || type == Material.MUSHROOM_STEM;
	}
	
	public boolean isAliveCoral(Material type) {
		switch (type) {
		case BRAIN_CORAL:
		case BUBBLE_CORAL:
		case FIRE_CORAL:
		case HORN_CORAL:
		case TUBE_CORAL:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isDeadCoral(Material type) {
		switch (type) {
		case DEAD_BRAIN_CORAL:
		case DEAD_BUBBLE_CORAL:
		case DEAD_FIRE_CORAL:
		case DEAD_HORN_CORAL:
		case DEAD_TUBE_CORAL:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isAliveOrDeadCoral(Material type) {
		return isAliveCoral(type) || isDeadCoral(type);
	}
	
	public boolean isAliveCoralBlock(Material type) {
		switch (type) {
		case BRAIN_CORAL_BLOCK:
		case BUBBLE_CORAL_BLOCK:
		case FIRE_CORAL_BLOCK:
		case HORN_CORAL_BLOCK:
		case TUBE_CORAL_BLOCK:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isDeadCoralBlock(Material type) {
		switch (type) {
		case DEAD_BRAIN_CORAL_BLOCK:
		case DEAD_BUBBLE_CORAL_BLOCK:
		case DEAD_FIRE_CORAL_BLOCK:
		case DEAD_HORN_CORAL_BLOCK:
		case DEAD_TUBE_CORAL_BLOCK:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isAliveOrDeadCoralBlock(Material type) {
		return isAliveCoralBlock(type) || isDeadCoralBlock(type);
	}
	
	public boolean isAliveCoralNonWallFan(Material type) {
		switch (type) {
		case BRAIN_CORAL_FAN:
		case BUBBLE_CORAL_FAN:
		case FIRE_CORAL_FAN:
		case HORN_CORAL_FAN:
		case TUBE_CORAL_FAN:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isDeadCoralNonWallFan(Material type) {
		switch (type) {
		case DEAD_BRAIN_CORAL_FAN:
		case DEAD_BUBBLE_CORAL_FAN:
		case DEAD_FIRE_CORAL_FAN:
		case DEAD_HORN_CORAL_FAN:
		case DEAD_TUBE_CORAL_FAN:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isAliveOrDeadCoralNonWallFan(Material type) {
		return isAliveCoralNonWallFan(type) || isDeadCoralNonWallFan(type);
	}
	
	public boolean isAliveCoralWallFan(Material type) {
		switch (type) {
		case BRAIN_CORAL_WALL_FAN:
		case BUBBLE_CORAL_WALL_FAN:
		case FIRE_CORAL_WALL_FAN:
		case HORN_CORAL_WALL_FAN:
		case TUBE_CORAL_WALL_FAN:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isDeadCoralWallFan(Material type) {
		switch (type) {
		case DEAD_BRAIN_CORAL_WALL_FAN:
		case DEAD_BUBBLE_CORAL_WALL_FAN:
		case DEAD_FIRE_CORAL_WALL_FAN:
		case DEAD_HORN_CORAL_WALL_FAN:
		case DEAD_TUBE_CORAL_WALL_FAN:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isAliveOrDeadCoralWallFan(Material type) {
		return isAliveCoralWallFan(type) || isDeadCoralWallFan(type);
	}
	
	public boolean isCampfire(Material type) {
		switch (type) {
		case CAMPFIRE:
		case SOUL_CAMPFIRE:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isWallHead(Material type) {
		switch (type) {
		case CREEPER_WALL_HEAD:
		case DRAGON_WALL_HEAD:
		case PLAYER_WALL_HEAD:
		case SKELETON_WALL_SKULL:
		case WITHER_SKELETON_WALL_SKULL:
		case ZOMBIE_WALL_HEAD:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isGroundHead(Material type) {
		switch (type) {
		case CREEPER_HEAD:
		case DRAGON_HEAD:
		case PLAYER_HEAD:
		case SKELETON_SKULL:
		case WITHER_SKELETON_SKULL:
		case ZOMBIE_HEAD:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isGroundOrWallHead(Material type) {
		return isGroundHead(type) || isWallHead(type);
	}
	
	public boolean isWallTorch(Material type) {
		switch (type) {
		case REDSTONE_WALL_TORCH:
		case SOUL_WALL_TORCH:
		case WALL_TORCH:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isStandingTorch(Material type) {
		switch (type) {
		case REDSTONE_TORCH:
		case SOUL_TORCH:
		case TORCH:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isStandingOrWallTorch(Material type) {
		return isStandingTorch(type) || isWallTorch(type);
	}
	
	public boolean isFire(Material type) {
		switch (type) {
		case FIRE:
		case SOUL_FIRE:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isLantern(Material type) {
		switch (type) {
		case LANTERN:
		case SOUL_LANTERN:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isItemframe(Material type) {
		switch (type) {
		case GLOW_ITEM_FRAME:
		case ITEM_FRAME:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isBlackstoneWall(Material type) {
		switch (type) {
		case BLACKSTONE_WALL:
		case POLISHED_BLACKSTONE_BRICK_WALL:
		case POLISHED_BLACKSTONE_WALL:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isSandstoneWall(Material type) {
		switch (type) {
		case RED_SANDSTONE_WALL:
		case SANDSTONE_WALL:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isStoneBrickWall(Material type) {
		switch (type) {
		case MOSSY_STONE_BRICK_WALL:
		case STONE_BRICK_WALL:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isCobblestoneWall(Material type) {
		switch (type) {
		case COBBLESTONE_WALL:
		case MOSSY_COBBLESTONE_WALL:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isNetherBrickWall(Material type) {
		switch (type) {
		case NETHER_BRICK_WALL:
		case RED_NETHER_BRICK_WALL:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isDeepslateWall(Material type) {
		switch (type) {
		case COBBLED_DEEPSLATE_WALL:
		case DEEPSLATE_BRICK_WALL:
		case DEEPSLATE_TILE_WALL:
		case POLISHED_DEEPSLATE_WALL:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isWall(Material type) {
		if (isBlackstoneWall(type)) {
			return true;
		}
		if (isSandstoneWall(type)) {
			return true;
		}
		if (isStoneBrickWall(type)) {
			return true;
		}
		if (isCobblestoneWall(type)) {
			return true;
		}
		if (isNetherBrickWall(type)) {
			return true;
		}
		if (isDeepslateWall(type)) {
			return true;
		}
		switch (type) {
		case ANDESITE_WALL:
		case BRICK_WALL:
		case DIORITE_WALL:
		case END_STONE_BRICK_WALL:
		case GRANITE_WALL:
		case PRISMARINE_WALL:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isRail(Material type) {
		switch (type) {
		case ACTIVATOR_RAIL:
		case DETECTOR_RAIL:
		case POWERED_RAIL:
		case RAIL:
			return true;
		default:
			return false;
		}
	}
	
	/*
	public boolean isDirtWithCover(Material type) {
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
	
	public boolean isMusicDisc(Material type) {
		switch (type) {
		case MUSIC_DISC_11:
		case MUSIC_DISC_13:
		case MUSIC_DISC_BLOCKS:
		case MUSIC_DISC_CAT:
		case MUSIC_DISC_CHIRP:
		case MUSIC_DISC_FAR:
		case MUSIC_DISC_MALL:
		case MUSIC_DISC_MELLOHI:
		case MUSIC_DISC_PIGSTEP:
		case MUSIC_DISC_STAL:
		case MUSIC_DISC_STRAD:
		case MUSIC_DISC_WAIT:
		case MUSIC_DISC_WARD:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isHelmet(Material type) {
		switch (type) {
		case LEATHER_HELMET:
		case IRON_HELMET:
		case CHAINMAIL_HELMET:
		case GOLDEN_HELMET:
		case DIAMOND_HELMET:
		case NETHERITE_HELMET:
		case TURTLE_HELMET:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isNonElytraChestplate(Material type) {
		switch (type) {
		case LEATHER_CHESTPLATE:
		case IRON_CHESTPLATE:
		case CHAINMAIL_CHESTPLATE:
		case GOLDEN_CHESTPLATE:
		case DIAMOND_CHESTPLATE:
		case NETHERITE_CHESTPLATE:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isChestplate(Material type) {
		return isNonElytraChestplate(type) || type == Material.ELYTRA;
	}
	
	public boolean isLeggings(Material type) {
		switch (type) {
		case LEATHER_LEGGINGS:
		case IRON_LEGGINGS:
		case CHAINMAIL_LEGGINGS:
		case GOLDEN_LEGGINGS:
		case DIAMOND_LEGGINGS:
		case NETHERITE_LEGGINGS:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isBoots(Material type) {
		switch (type) {
		case LEATHER_BOOTS:
		case IRON_BOOTS:
		case CHAINMAIL_BOOTS:
		case GOLDEN_BOOTS:
		case DIAMOND_BOOTS:
		case NETHERITE_BOOTS:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isArmor(Material type) {
		return isHelmet(type) || isChestplate(type) || isLeggings(type) || isBoots(type) || isGroundOrWallHead(type);
	}
	
	public boolean isSword(Material type) {
		switch (type) {
		case WOODEN_SWORD:
		case STONE_SWORD:
		case IRON_SWORD:
		case GOLDEN_SWORD:
		case DIAMOND_SWORD:
		case NETHERITE_SWORD:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isHoe(Material type) {
		switch (type) {
		case WOODEN_HOE:
		case STONE_HOE:
		case IRON_HOE:
		case GOLDEN_HOE:
		case DIAMOND_HOE:
		case NETHERITE_HOE:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isAxe(Material type) {
		switch (type) {
		case WOODEN_AXE:
		case STONE_AXE:
		case IRON_AXE:
		case GOLDEN_AXE:
		case DIAMOND_AXE:
		case NETHERITE_AXE:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isPickaxe(Material type) {
		switch (type) {
		case WOODEN_PICKAXE:
		case STONE_PICKAXE:
		case IRON_PICKAXE:
		case GOLDEN_PICKAXE:
		case DIAMOND_PICKAXE:
		case NETHERITE_PICKAXE:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isShovel(Material type) {
		switch (type) {
		case WOODEN_SHOVEL:
		case STONE_SHOVEL:
		case IRON_SHOVEL:
		case GOLDEN_SHOVEL:
		case DIAMOND_SHOVEL:
		case NETHERITE_SHOVEL:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isBowOrCrossbow(Material type) {
		switch (type) {
		case BOW:
		case CROSSBOW:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isMainHandWeapon(Material type) {
		return isSword(type) || isHoe(type) || isAxe(type) || isPickaxe(type) || isShovel(type) || isBowOrCrossbow(type) || type == Material.TRIDENT;
	}
	
	public boolean isOffHandCombatItem(Material type) {
		return type == Material.SHIELD || type == Material.TOTEM_OF_UNDYING;
	}
	
	public boolean isCommandBlock(Material type) {
		switch (type) {
		case COMMAND_BLOCK:
		case REPEATING_COMMAND_BLOCK:
		case CHAIN_COMMAND_BLOCK:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isStructureRelatedBlock(Material type) {
		switch (type) {
		case JIGSAW:
		case STRUCTURE_BLOCK:
		case STRUCTURE_VOID:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isIllegal(Material type) {
		return isCommandBlock(type) || isStructureRelatedBlock(type) || type == Material.BARRIER || type == Material.COMMAND_BLOCK_MINECART || type == Material.DEBUG_STICK || type == Material.LIGHT;
	}
	
}
