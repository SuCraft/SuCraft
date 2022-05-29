/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.supporters.main

import org.sucraft.core.common.sucraft.plugin.SuCraftPlugin
import org.sucraft.core.common.general.pattern.SingletonContainer

class SuCraftSupportersPlugin : SuCraftPlugin() {

	// Companion (singleton)

	companion object : SingletonContainer<SuCraftSupportersPlugin>()

}