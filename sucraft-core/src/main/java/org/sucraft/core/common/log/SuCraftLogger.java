package org.sucraft.core.common.log;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.sucraft.core.common.plugin.SuCraftPlugin;

/**
 * A SuCraft logger belongs to either a plugin (representing the plugin as a whole), or a parent logger and own name
 */
public class SuCraftLogger {
	
	private final @Nullable SuCraftPlugin plugin;
	private final @Nullable SuCraftLogger parentLogger;
	private final @Nullable String name;
	
	/**
	 * Creates a new logger with a parent
	 * @param parentLogger The parent logger
	 * @param name The name of this logger
	 */
	public SuCraftLogger(@NotNull SuCraftLogger parentLogger, @NotNull String name) {
		Objects.requireNonNull(parentLogger);
		Objects.requireNonNull(name);
		this.plugin = null;
		this.parentLogger = parentLogger;
		this.name = name;
	}
	
	/**
	 * Creates a new logger belonging to a plugin
	 * @param plugin The plugin
	 */
	public SuCraftLogger(@NotNull SuCraftPlugin plugin) {
		Objects.requireNonNull(plugin);
		this.plugin = plugin;
		this.parentLogger = null;
		this.name = null;
	}
	
	/**
	 * Convenience method to create a new logger with a parent, which is the logger belonging to the given plugin (the plugin's logger must already be initialized)
	 * @param parentLoggerPlugin The parent logger's plugin
	 * @param name The name of this logger
	 */
	public SuCraftLogger(@NotNull SuCraftPlugin plugin, @NotNull String name) {
		this(plugin.getSuCraftLogger(), name);
	}
	
	private @NotNull String prefixMessage(@Nullable String message) {
		if (name != null) {
			return "[" + name + "] " + message;
		}
		return message;
	}
	
	public void info(@Nullable String message) {
		String prefixedMessage = prefixMessage(message);
		if (plugin != null) {
			plugin.getLogger().info(prefixedMessage);
			return;
		}
		parentLogger.info(prefixedMessage);
	}
	
	public void warning(@Nullable String message) {
		String prefixedMessage = prefixMessage(message);
		if (plugin != null) {
			plugin.getLogger().warning(prefixedMessage);
			return;
		}
		parentLogger.warning(prefixedMessage);
	}
	
	public void severe(@Nullable String message) {
		String prefixedMessage = prefixMessage(message);
		if (plugin != null) {
			plugin.getLogger().severe(prefixedMessage);
			return;
		}
		parentLogger.severe(prefixedMessage);
	}
	
	/**
	 * Convenience method to either call info or warning depending on the given boolean
	 * @param message The message
	 * @param useWarning Whether to call warning
	 */
	public void infoOrWarning(@Nullable String message, boolean useWarning) {
		if (useWarning) {
			warning(message);
		} else {
			info(message);
		}
	}
	
}
