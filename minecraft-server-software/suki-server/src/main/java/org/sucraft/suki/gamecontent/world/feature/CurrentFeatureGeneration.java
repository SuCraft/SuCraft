package org.sucraft.suki.gamecontent.world.feature;

public final class CurrentFeatureGeneration {

    private CurrentFeatureGeneration() {
        throw new UnsupportedOperationException();
    }

    private static final ThreadLocal<Integer> wouldSurviveCheckIsForPredicate = ThreadLocal.withInitial(() -> 0);

    public static boolean getDoing() {
        return wouldSurviveCheckIsForPredicate.get() > 0;
    }

    public static void setDoing() {
        wouldSurviveCheckIsForPredicate.set(wouldSurviveCheckIsForPredicate.get() + 1);
    }

    public static void unsetDoing() {
        wouldSurviveCheckIsForPredicate.set(wouldSurviveCheckIsForPredicate.get() - 1);
    }

}
