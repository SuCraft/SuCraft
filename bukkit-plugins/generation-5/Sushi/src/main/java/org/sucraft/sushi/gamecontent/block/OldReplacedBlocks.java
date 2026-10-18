package org.sucraft.sushi.gamecontent.block;

import net.minecraft.resources.Identifier;
import java.util.ArrayList;
import java.util.List;

public final class OldReplacedBlocks {

    private OldReplacedBlocks() {
        throw new UnsupportedOperationException();
    }

    public record ReplacedBlock(Identifier oldBlock, Identifier newBlock) {
    }

    public static final List<ReplacedBlock> LIST = new ArrayList<>();
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
            LIST.add(new ReplacedBlock(Identifier.fromNamespaceAndPath("sucraft", block), Identifier.fromNamespaceAndPath(Identifier.DEFAULT_NAMESPACE, block)));
        }
    }

}
