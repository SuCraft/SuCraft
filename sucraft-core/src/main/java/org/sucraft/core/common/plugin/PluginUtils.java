package org.sucraft.core.common.plugin;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PluginUtils {
	
	public <T extends SuCraftPlugin> @NotNull T requirePluginInitialized(@Nullable T plugin) {
		return Objects.requireNonNull(plugin, "Plugin has not been initialized yet");
	}
	
	public <T> @NotNull T requireComponentInitialized(@Nullable T component) {
		return Objects.requireNonNull(component, "Component has not been initialized yet");
	}
	
	public void requireComponentUninitialized(@Nullable Object component) {
		if (component == null) {
			throw new IllegalStateException("Component was already initialized");
		}
	}
	
}
