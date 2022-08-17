/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.inventory.anvil

import org.bukkit.GameMode.CREATIVE
import org.bukkit.inventory.AnvilInventory

/**
 * This is a constant representing the inclusive upper bound of the
 * [level cost][AnvilInventory.getRepairCost] for anvil repairs
 * that is present in the vanilla game for players that are not in [creative mode][CREATIVE].
 */
const val vanillaMaxSurvivalModeAnvilRepairCost = 39