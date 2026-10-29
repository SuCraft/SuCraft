package org.sucraft.sushi.main;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.sucraft.sushi.gamecontent.item.ReplaceOldItemsInInventories;
import org.sucraft.sushi.gamecontent.recipe.AutomaticRecipeAdder;
import org.sucraft.sushi.gamecontent.recipe.AutomaticRecipeAdvancementAdder;
import java.util.logging.Logger;

public final class SushiPlugin extends JavaPlugin {

    private static SushiPlugin instance;
    private static Logger logger;
    private static ComponentLogger componentLogger;

    public static SushiPlugin get() {
        return instance;
    }

    public static Logger getPluginLogger() {
        return logger;
    }

    public static ComponentLogger getPluginComponentLogger() {
        return componentLogger;
    }

    @Override
    public void onEnable() {
        // Don't do anything else if the server doesn't support Spout
        if (!CheckSpout.checkSpout()) return;

        // Store instance statically
        instance = this;
        logger = this.getLogger();
        componentLogger = this.getComponentLogger();

        // Register events
        Bukkit.getPluginManager().registerEvents(new ReplaceOldItemsInInventories(), this);

        // Run initializers
        AutomaticRecipeAdder.run();
        AutomaticRecipeAdvancementAdder.run();

        // Log to console
        getLogger().info(getName() + " is enabled!");
    }

}
