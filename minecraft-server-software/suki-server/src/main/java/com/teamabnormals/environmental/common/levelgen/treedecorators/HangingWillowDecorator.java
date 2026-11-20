package com.teamabnormals.environmental.common.levelgen.treedecorators;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.sucraft.suki.gamecontent.block.SukiBlocks;

public class HangingWillowDecorator extends TreeDecorator {

	public static final HangingWillowDecorator INSTANCE = new HangingWillowDecorator();
    public static final MapCodec<HangingWillowDecorator> CODEC = MapCodec.unit(() -> INSTANCE);

	@Override
	protected TreeDecoratorType<?> type() {
		return TreeDecoratorType.HANGING_WILLOW_LEAVES;
	}

	@Override
	public void place(Context context) {
		for (BlockPos pos : context.leaves()) {
			if (context.level().isStateAtPosition(pos.below(), BlockState::isAir)) {
				if (context.random().nextInt(2) == 0) {
					context.setBlock(pos.below(), SukiBlocks.SWAMP_LEAVES.get().defaultBlockState());
				}
			}
		}
	}

}
