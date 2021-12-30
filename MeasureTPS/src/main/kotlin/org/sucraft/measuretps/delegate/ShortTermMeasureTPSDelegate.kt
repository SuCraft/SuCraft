/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.measuretps.delegate

import org.sucraft.core.common.general.log.AbstractLogger
import org.sucraft.core.common.sucraft.delegate.measuretps.ShortTermMeasureTPS
import org.sucraft.core.common.sucraft.delegate.measuretps.ShortTermMeasuredTPSListener
import org.sucraft.core.common.sucraft.plugin.SuCraftComponent
import org.sucraft.measuretps.data.ShortTermMeasureTPSData
import org.sucraft.measuretps.main.SuCraftMeasureTPSPlugin


object ShortTermMeasureTPSDelegate : ShortTermMeasureTPS<SuCraftMeasureTPSPlugin>, SuCraftComponent<SuCraftMeasureTPSPlugin>(SuCraftMeasureTPSPlugin.getInstance()) {

	// Initialization

	init {
		ShortTermMeasureTPS.registerImplementation(this)
	}

	// Delegate overrides

	override fun getDelegatePlugin(): SuCraftMeasureTPSPlugin = plugin

	override fun getDelegateLogger(): AbstractLogger = logger

	// Implementation

	override fun getRecentTPS() = ShortTermMeasureTPSData.getRecentTPS()

	override fun registerListener(listener: ShortTermMeasuredTPSListener) = ShortTermMeasureTPSData.registerListener(listener)

}