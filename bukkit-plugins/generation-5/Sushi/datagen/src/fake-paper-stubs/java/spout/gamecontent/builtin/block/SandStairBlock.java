package spout.gamecontent.builtin.block;

import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.block.state.BlockState;

public class SandStairBlock extends ColoredFallingStairBlock {

    public SandStairBlock(final BlockState baseState, final ColorRGBA dustColor, final Properties properties) {
        super(baseState, dustColor, properties);
    }

}
