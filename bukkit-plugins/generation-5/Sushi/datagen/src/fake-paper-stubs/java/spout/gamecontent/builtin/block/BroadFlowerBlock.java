package spout.gamecontent.builtin.block;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BroadFlowerBlock extends FlowerBlock {

    public BroadFlowerBlock(final Holder<MobEffect> suspiciousStewEffect, final float effectSeconds, final BlockBehaviour.Properties properties) {
        super(suspiciousStewEffect, effectSeconds, properties);
    }

    public BroadFlowerBlock(final SuspiciousStewEffects suspiciousStewEffects, final BlockBehaviour.Properties properties) {
        super(suspiciousStewEffects, properties);
    }

}
