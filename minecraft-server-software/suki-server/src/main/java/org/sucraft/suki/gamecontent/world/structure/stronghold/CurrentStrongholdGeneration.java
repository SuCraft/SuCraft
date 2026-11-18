package org.sucraft.suki.gamecontent.world.structure.stronghold;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import org.jspecify.annotations.Nullable;
import java.util.Objects;

public final class CurrentStrongholdGeneration {

    private CurrentStrongholdGeneration() {
        throw new UnsupportedOperationException();
    }

    private static final ThreadLocal<Integer> surface = ThreadLocal.withInitial(() -> 0);

    public static boolean getSurface() {
        return surface.get() > 0;
    }

    public static void setSurface() {
        surface.set(surface.get() + 1);
        if (clearedOnPurpose.get() != null) {
            throw new IllegalStateException();
        }
        clearedOnPurpose.set(new LongOpenHashSet());
        if (madeUnreplaceableSolidOnPurpose.get() != null) {
            throw new IllegalStateException();
        }
        madeUnreplaceableSolidOnPurpose.set(new LongOpenHashSet());
    }

    public static void unsetSurface() {
        surface.set(surface.get() - 1);
        clearedOnPurpose.remove();
        madeUnreplaceableSolidOnPurpose.remove();
    }

    private static final ThreadLocal<Integer> cold = ThreadLocal.withInitial(() -> 0);

    public static boolean getCold() {
        return cold.get() > 0;
    }

    public static void setCold() {
        cold.set(cold.get() + 1);
    }

    public static void unsetCold() {
        cold.set(cold.get() - 1);
    }

    private static final ThreadLocal<Integer> grassy = ThreadLocal.withInitial(() -> 0);

    public static boolean getGrassy() {
        return grassy.get() > 0;
    }

    public static void setGrassy() {
        grassy.set(grassy.get() + 1);
    }

    public static void unsetGrassy() {
        grassy.set(grassy.get() - 1);
    }

    private static final ThreadLocal<@Nullable RandomSource> random = ThreadLocal.withInitial(() -> null);

    public static @Nullable RandomSource getRandom() {
        return random.get();
    }

    public static @Nullable RandomSource setRandom(RandomSource random) {
        RandomSource oldRandom = getRandom();
        CurrentStrongholdGeneration.random.set(random);
        return oldRandom;
    }

    public static void unsetRandom(@Nullable RandomSource oldRandom) {
        CurrentStrongholdGeneration.random.set(oldRandom);
    }

    private static final ThreadLocal<Integer> portalRoom = ThreadLocal.withInitial(() -> 0);

    public static boolean getPortalRoom() {
        return portalRoom.get() > 0;
    }

    public static void setPortalRoom() {
        portalRoom.set(portalRoom.get() + 1);
    }

    public static void unsetPortalRoom() {
        portalRoom.set(portalRoom.get() - 1);
    }

    private static final ThreadLocal<@Nullable LongOpenHashSet> clearedOnPurpose = ThreadLocal.withInitial(() -> null);

    public static boolean getClearedOnPurpose(BlockPos pos) {
        return Objects.requireNonNull(clearedOnPurpose.get()).contains(pos.asLong());
    }

    public static void addClearedOnPurpose(BlockPos pos) {
        Objects.requireNonNull(clearedOnPurpose.get()).add(pos.asLong());
    }

    private static final ThreadLocal<LongOpenHashSet> madeUnreplaceableSolidOnPurpose = ThreadLocal.withInitial(() -> null);

    public static boolean getMadeUnreplaceableSolidOnPurpose(BlockPos pos) {
        return Objects.requireNonNull(madeUnreplaceableSolidOnPurpose.get()).contains(pos.asLong());
    }

    public static void addMadeUnreplaceableSolidOnPurpose(BlockPos pos) {
        Objects.requireNonNull(madeUnreplaceableSolidOnPurpose.get()).add(pos.asLong());
    }

}
