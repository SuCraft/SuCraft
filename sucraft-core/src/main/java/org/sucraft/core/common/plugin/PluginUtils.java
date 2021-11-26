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
	
}
