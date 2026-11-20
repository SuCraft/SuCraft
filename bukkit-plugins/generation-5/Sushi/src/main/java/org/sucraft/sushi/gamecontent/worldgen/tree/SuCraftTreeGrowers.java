package org.sucraft.sushi.gamecontent.worldgen.tree;

import net.minecraft.world.level.block.grower.TreeGrower;

/**
 * A proxy for {@link TreeGrower}.
 */
public final class SuCraftTreeGrowers {

    private SuCraftTreeGrowers() {
        throw new UnsupportedOperationException();
    }

    public static final TreeGrower FROST = TreeGrower.FROST;
    public static final TreeGrower YELLOW_MAPLE = TreeGrower.YELLOW_MAPLE;
    public static final TreeGrower ORANGE_MAPLE = TreeGrower.ORANGE_MAPLE;
    public static final TreeGrower RED_MAPLE = TreeGrower.RED_MAPLE;
    public static final TreeGrower SWAMP = TreeGrower.SWAMP;

}
