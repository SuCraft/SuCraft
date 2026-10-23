package org.sucraft.sushi.gamecontent.recipe;

import net.minecraft.server.MinecraftServer;

public final class AutomaticRecipeAdvancementAdder {

    private AutomaticRecipeAdvancementAdder() {
        throw new UnsupportedOperationException();
    }

    public static void run() {
        MinecraftServer.getServer().getRecipeManager().getRecipes().forEach(holder -> {

        });
    }

}
