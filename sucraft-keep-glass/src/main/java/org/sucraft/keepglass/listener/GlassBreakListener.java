package org.sucraft.keepglass.listener;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.sucraft.core.common.log.DefaultLogTexts;
import org.sucraft.core.common.log.SuCraftLogger;
import org.sucraft.core.common.material.MaterialGroups;
import org.sucraft.core.common.plugin.PluginUtils;
import org.sucraft.keepglass.main.SuCraftKeepGlassPlugin;

public class GlassBreakListener implements Listener {
	
	// Singleton
	
	private static @Nullable GlassBreakListener instance = null;
	public static void initialize() {
		PluginUtils.requireComponentUninitialized(instance);
		instance = new GlassBreakListener();
	}
	public static @NotNull GlassBreakListener instance() {
		return PluginUtils.requireComponentInitialized(instance);
	}
	
	// Logger
	
	private final @NotNull SuCraftLogger logger;
	
	// Initialization
	
	private GlassBreakListener() {
		logger = new SuCraftLogger(SuCraftKeepGlassPlugin.instance(), "Glass break listener");
		logger.info(DefaultLogTexts.registeringEvents);
		SuCraftKeepGlassPlugin.instance().registerEvents(this);
	}
	
	// Events
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (player.getGameMode() != GameMode.SURVIVAL) {
			return;
		}
		ItemStack itemInHand = player.getInventory().getItemInMainHand();
		// Make sure the player is not using silk touch
		if (itemInHand != null && itemInHand.getEnchantments().keySet().stream().anyMatch(enchantment -> enchantment.equals(Enchantment.SILK_TOUCH))) {
			return;
		}
		Block block = event.getBlock();
		Material type = block.getType();
		if (MaterialGroups.isStainedOrRegularGlassPane(type) || MaterialGroups.isStainedOrRegularGlass(type)) {
			Location dropLocation = block.getLocation();
			dropLocation.getWorld().dropItem(dropLocation, new ItemStack(type));
		}
	}
	
}

