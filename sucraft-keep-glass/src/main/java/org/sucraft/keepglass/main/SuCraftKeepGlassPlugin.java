package org.sucraft.keepglass.main;

import org.sucraft.core.common.plugin.PluginUtils;
import org.sucraft.core.common.plugin.SuCraftPlugin;
import org.sucraft.keepglass.listener.GlassBreakListener;

public class SuCraftKeepGlassPlugin extends SuCraftPlugin {
	
	// Singleton
	
	private static SuCraftKeepGlassPlugin instance = null;
	public static SuCraftKeepGlassPlugin instance() {
		return PluginUtils.requirePluginInitialized(instance);
	}
	
	// Initialization
	
	@Override
	public void onPluginPreEnable() {
		instance = this;
	}
	
	// Enable
	
	@Override
	public void onPluginEnable() {
		GlassBreakListener.initialize();
	}
	
}
