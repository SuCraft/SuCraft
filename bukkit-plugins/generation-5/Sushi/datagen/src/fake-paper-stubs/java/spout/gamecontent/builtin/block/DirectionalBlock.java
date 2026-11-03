package spout.gamecontent.builtin.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class DirectionalBlock extends net.minecraft.world.level.block.DirectionalBlock {

    public DirectionalBlock(final Properties properties) {
        this(properties, Direction.UP);
    }

    public DirectionalBlock(final Properties properties, final Direction defaultDirection) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, defaultDirection));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

}
