package org.sucraft.suki.gamecontent.common.dataconverter;

import ca.spottedleaf.dataconverter.minecraft.converters.blockname.ConverterAbstractBlockRename;
import ca.spottedleaf.dataconverter.minecraft.converters.itemname.ConverterAbstractItemRename;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The conversions for {@link SuCraftConversionVersions#VERSION_1}.
 */
@org.jspecify.annotations.NullMarked
public final class SuCraftConversions1 {

    private SuCraftConversions1() {
        throw new UnsupportedOperationException();
    }

    public static final Map<String, String> RENAME = new HashMap<>();
    static {
        var sameBlockPaths = new ArrayList<String>();
        var sameBlockSlabStairsBases = new ArrayList<String>();
        for (var color : List.of("black", "blue", "brown", "cyan", "gray", "green", "light_blue", "light_gray", "lime", "magenta", "orange", "pink", "purple", "red", "white", "yellow")) {
            sameBlockSlabStairsBases.add(color + "_concrete");
            sameBlockSlabStairsBases.add(color + "_wool");
        }
        sameBlockSlabStairsBases.add("tuff");
        for (var base : sameBlockSlabStairsBases) {
            sameBlockPaths.add(base + "_slab");
            sameBlockPaths.add(base + "_stairs");
        }
        for (var block : sameBlockPaths) {
            RENAME.put("sucraft:" + block, "minecraft:" + block);
        }
        RENAME.put("sucraft:pale_birch_leaves", "minecraft:birch_leaves");
        RENAME.put("sucraft:red_oak_leaves", "sucraft:red_maple_leaves");
        RENAME.put("sucraft:orange_oak_leaves", "sucraft:orange_maple_leaves");
    }

    public static void register() {
        ConverterAbstractBlockRename.register(SuCraftConversionVersions.VERSION_1, RENAME::get);
        ConverterAbstractItemRename.register(SuCraftConversionVersions.VERSION_1, RENAME::get);
    }

}
