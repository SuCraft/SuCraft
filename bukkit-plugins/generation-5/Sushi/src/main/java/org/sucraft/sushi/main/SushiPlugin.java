package org.sucraft.sushi.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.sucraft.sushi.gamecontent.item.ReplaceOldItemsInInventories;
import org.sucraft.sushi.gamecontent.recipe.AutomaticRecipeAdder;
import org.sucraft.sushi.gamecontent.recipe.AutomaticRecipeAdvancementAdder;

public final class SushiPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Don't do anything else if the server doesn't support Spout
        if (!CheckSpout.checkSpout()) return;

        // Register events
        Bukkit.getPluginManager().registerEvents(new ReplaceOldItemsInInventories(), this);

        // Run initializers
        AutomaticRecipeAdder.run();
        AutomaticRecipeAdvancementAdder.run();

        // Log to console
        getLogger().info(getName() + " is enabled!");
    }

}
