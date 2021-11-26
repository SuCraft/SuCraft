package org.sucraft.core.common.plugin;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.sucraft.core.common.log.DefaultLogTexts;
import org.sucraft.core.common.log.SuCraftLogger;

public abstract class SuCraftPlugin extends JavaPlugin {
	
	// Enable and disable
	
	@Override
	public void onEnable() {
		suCraftLogger = new SuCraftLogger(this);
		suCraftLogger.info(DefaultLogTexts.pluginEnabling);
		onPluginPreEnable();
		onPluginEnable();
		suCraftLogger.info(DefaultLogTexts.pluginEnabled);
	}
	
	@Override
	public void onDisable() {
		suCraftLogger.info(DefaultLogTexts.pluginDisabling);
		onPluginDisable();
		suCraftLogger.info(DefaultLogTexts.pluginDisabled);
	}
	
	/**
	 * The logger of this plugin has been created, nothing else has been done
	 * The plugin singleton instance should be initialized in this method
	 */
	protected abstract void onPluginPreEnable();
	
	protected void onPluginEnable() {}
	
	protected void onPluginDisable() {}
	
	// Logger
	
	private @Nullable SuCraftLogger suCraftLogger;
	
	public @NotNull SuCraftLogger getSuCraftLogger() {
		return Objects.requireNonNull(suCraftLogger, "The logger has not been initialized yet");
	}
	
	// Convenience methods for doing plugin-based things
	
	public void registerEvents(@NotNull Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, this);
	}
	
	public @NotNull NamespacedKey getNamespacedKey(@NotNull String key) {
		return new NamespacedKey(this, key);
	}
	
}
