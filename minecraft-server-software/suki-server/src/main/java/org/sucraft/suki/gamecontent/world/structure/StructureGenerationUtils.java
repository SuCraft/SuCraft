package org.sucraft.suki.gamecontent.world.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;

public final class StructureGenerationUtils {

    private StructureGenerationUtils() {
        throw new UnsupportedOperationException();
    }

    public static Holder<Biome> approximateSeaLeveBiome(Structure.GenerationContext context) {
        return biome(context, context.chunkPos().getMiddleBlockX(), context.chunkGenerator().getSeaLevel(), context.chunkPos().getMiddleBlockZ());
    }

    public static Holder<Biome> biome(Structure.GenerationContext context, BlockPos pos) {
        return biome(context, pos.getX(), pos.getY(), pos.getZ());
    }

    public static Holder<Biome> biome(Structure.GenerationContext context, int x, int y, int z) {
        return context.biomeResolver().getNoiseBiome(QuartPos.fromBlock(x), QuartPos.fromBlock(y), QuartPos.fromBlock(z));
    }

}
