package org.sucraft.sushi.main;

import org.bukkit.plugin.java.JavaPlugin;

public final class SushiPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Don't do anything else if the server doesn't support Spout
        if (!CheckSpout.checkSpout()) return;

        // Log to console
        getLogger().info(getName() + " is enabled!");
    }

}
