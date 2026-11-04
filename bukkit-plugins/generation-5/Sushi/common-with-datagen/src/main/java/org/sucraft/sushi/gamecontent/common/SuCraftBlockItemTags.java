package org.sucraft.sushi.gamecontent.common;

import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockItemTagId;

public final class SuCraftBlockItemTags {

    private SuCraftBlockItemTags() {
        throw new UnsupportedOperationException();
    }

    public static final BlockItemTagId BOOKSHELF_SLABS = createSuCraft("bookshelf_slabs");
    public static final BlockItemTagId BOOKSHELF_STAIRS = createSuCraft("bookshelf_stairs");
    public static final BlockItemTagId CONCRETE_BUTTONS = createSuCraft("concrete_buttons");
    public static final BlockItemTagId CONCRETE_POWDER_SLABS = createSuCraft("concrete_powder_slabs");
    public static final BlockItemTagId CONCRETE_POWDER_STAIRS = createSuCraft("concrete_powder_stairs");
    public static final BlockItemTagId CONCRETE_PRESSURE_PLATES = createSuCraft("concrete_pressure_plates");
    public static final BlockItemTagId CONCRETE_WALLS = createSuCraft("concrete_walls");
    public static final BlockItemTagId INFESTED_STONE_BRICKS = createSuCraft("infested_stone_bricks");
    public static final BlockItemTagId STONE_WALLS = createSuCraft("stone_walls");
    public static final BlockItemTagId TERRACOTTA_BUTTONS = createSuCraft("terracotta_buttons");
    public static final BlockItemTagId TERRACOTTA_PRESSURE_PLATES = createSuCraft("terracotta_pressure_plates");
    public static final BlockItemTagId TERRACOTTA_SLABS = createSuCraft("terracotta_slabs");
    public static final BlockItemTagId TERRACOTTA_STAIRS = createSuCraft("terracotta_stairs");
    public static final BlockItemTagId TERRACOTTA_WALLS = createSuCraft("terracotta_walls");
    public static final BlockItemTagId WOODEN_WALLS = createSuCraft("wooden_walls");
    public static final BlockItemTagId WOOL_BUTTONS = createSuCraft("wool_buttons");
    public static final BlockItemTagId WOOL_PRESSURE_PLATES = createSuCraft("wool_pressure_plates");
    public static final BlockItemTagId WOOL_WALLS = createSuCraft("wool_walls");

    /**
     * Analogous to {@link BlockItemTagId#create(String, String)}.
     */
    private static BlockItemTagId createSuCraft(String blockName, String itemName) {
        return BlockItemTagId.create(Identifier.fromNamespaceAndPath("sucraft", blockName), Identifier.fromNamespaceAndPath("sucraft", itemName));
    }

    /**
     * Analogous to {@link BlockItemTagId#create(String)}.
     */
    private static BlockItemTagId createSuCraft(String name) {
        return createSuCraft(name, name);
    }

}
