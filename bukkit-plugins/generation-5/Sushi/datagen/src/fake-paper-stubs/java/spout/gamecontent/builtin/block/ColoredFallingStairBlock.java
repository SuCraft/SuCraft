package spout.gamecontent.builtin.block;

import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ColoredFallingStairBlock extends StairBlock {

    public ColoredFallingStairBlock(final BlockState baseState, final ColorRGBA dustColor, final Properties properties) {
        super(baseState, properties);
    }

}
