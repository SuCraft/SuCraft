package org.sucraft.core.main;

import org.sucraft.core.common.plugin.PluginUtils;
import org.sucraft.core.common.plugin.SuCraftPlugin;

public class SuCraftCorePlugin extends SuCraftPlugin {
	
	private static SuCraftCorePlugin instance = null;
	public static SuCraftCorePlugin instance() {
		return PluginUtils.requirePluginInitialized(instance);
	}
	
	@Override
	public void onPluginPreEnable() {
		instance = this;
	}
	
}
