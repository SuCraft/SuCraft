/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.main

import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer


class SuCraftCorePlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftCorePlugin>()

}