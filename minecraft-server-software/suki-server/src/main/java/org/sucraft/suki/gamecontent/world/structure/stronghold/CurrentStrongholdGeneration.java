package org.sucraft.suki.gamecontent.world.structure.stronghold;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import java.util.Stack;

public final class CurrentStrongholdGeneration {

    private CurrentStrongholdGeneration() {
        throw new UnsupportedOperationException();
    }

    private static final ThreadLocal<Stack<Boolean>> surface = ThreadLocal.withInitial(Stack::new);

    public static boolean getSurface() {
        return surface.get().stream().anyMatch(it -> it);
    }

    public static void setSurface(boolean surface) {
        CurrentStrongholdGeneration.surface.get().push(surface);
        clearedOnPurpose.get().push(new LongOpenHashSet());
        madeUnreplaceableSolidOnPurpose.get().push(new LongOpenHashSet());
    }

    public static void unsetSurface() {
        CurrentStrongholdGeneration.surface.get().pop();
        clearedOnPurpose.get().pop();
        madeUnreplaceableSolidOnPurpose.get().pop();
    }

    private static final ThreadLocal<Stack<Boolean>> cold = ThreadLocal.withInitial(Stack::new);

    public static boolean getCold() {
        return cold.get().stream().anyMatch(it -> it);
    }

    public static void setCold(boolean cold) {
        CurrentStrongholdGeneration.cold.get().push(cold);
    }

    public static void unsetCold() {
        CurrentStrongholdGeneration.cold.get().pop();
    }

    private static final ThreadLocal<Stack<Boolean>> grassy = ThreadLocal.withInitial(Stack::new);

    public static boolean getGrassy() {
        return grassy.get().stream().anyMatch(it -> it);
    }

    public static void setGrassy(boolean grassy) {
        CurrentStrongholdGeneration.grassy.get().push(grassy);
    }

    public static void unsetGrassy() {
        CurrentStrongholdGeneration.grassy.get().pop();
    }

    private static final ThreadLocal<Stack<RandomSource>> random = ThreadLocal.withInitial(Stack::new);

    public static RandomSource getRandom() {
        return random.get().peek();
    }

    public static void setRandom(RandomSource random) {
        CurrentStrongholdGeneration.random.get().push(random);
    }

    public static void unsetRandom() {
        CurrentStrongholdGeneration.random.get().pop();
    }

    private static final ThreadLocal<Stack<Boolean>> portalRoom = ThreadLocal.withInitial(Stack::new);

    public static boolean getPortalRoom() {
        return portalRoom.get().stream().anyMatch(it -> it);
    }

    public static void setPortalRoom(boolean portalRoom) {
        CurrentStrongholdGeneration.portalRoom.get().push(portalRoom);
    }

    public static void unsetPortalRoom() {
        CurrentStrongholdGeneration.portalRoom.get().pop();
    }

    private static final ThreadLocal<Stack<LongOpenHashSet>> clearedOnPurpose = ThreadLocal.withInitial(Stack::new);

    public static boolean getClearedOnPurpose(BlockPos pos) {
        return clearedOnPurpose.get().stream().anyMatch(it -> it.contains(pos.asLong()));
    }

    public static void addClearedOnPurpose(BlockPos pos) {
        clearedOnPurpose.get().peek().add(pos.asLong());
    }

    private static final ThreadLocal<Stack<LongOpenHashSet>> madeUnreplaceableSolidOnPurpose = ThreadLocal.withInitial(Stack::new);

    public static boolean getMadeUnreplaceableSolidOnPurpose(BlockPos pos) {
        return madeUnreplaceableSolidOnPurpose.get().stream().anyMatch(it -> it.contains(pos.asLong()));
    }

    public static void addMadeUnreplaceableSolidOnPurpose(BlockPos pos) {
        madeUnreplaceableSolidOnPurpose.get().peek().add(pos.asLong());
    }

}
