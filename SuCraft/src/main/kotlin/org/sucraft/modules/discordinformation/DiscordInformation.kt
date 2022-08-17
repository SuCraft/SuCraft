/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.discordinformation

import org.sucraft.common.module.SuCraftModule
import org.sucraft.modules.discordcommand.DiscordCommand

/**
 * Utility module that contains the information about the Discord server and its channels.
 *
 * This class exists only to conceptually mark this as a module that can be depended on:
 * only the [DiscordChannel] class actually has an effect.
 *
 * The actual command to see the Discord invite is added by [DiscordCommand].
 */
object DiscordInformation : SuCraftModule<DiscordInformation>()