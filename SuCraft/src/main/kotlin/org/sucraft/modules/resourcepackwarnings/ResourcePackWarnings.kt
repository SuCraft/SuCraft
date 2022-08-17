/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.resourcepackwarnings

import com.viaversion.viaversion.api.Via
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion
import net.kyori.adventure.text.Component.join
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.JoinConfiguration.noSeparators
import org.bukkit.event.player.PlayerResourcePackStatusEvent
import org.bukkit.event.player.PlayerResourcePackStatusEvent.Status.*
import org.sucraft.common.event.on
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.scheduler.runLater
import org.sucraft.common.text.*
import org.sucraft.common.time.TimeInTicks

/**
 * Shows a warning to players if they have not accepted the resource pack,
 * or if they have accepted the resource pack but are on a major Minecraft version less than the server
 * (thereby potentially not being able to read the resource pack correctly).
 */
object ResourcePackWarnings : SuCraftModule<ResourcePackWarnings>() {

	// Settings

	private val sendMessageDelayAfterReceiveResourcePackStatus = TimeInTicks(45)

	private const val blocksWillBeReplacedMessage =
		"Custom SuCraft blocks will appear as the closest vanilla substitute, " +
				"and may look different than intended."

	private val declinedResourcePackMessage = component(color = INFORMATIVE_UNIMPORTANT) {
		+"Because you didn't add the server resource pack," +
				blocksWillBeReplacedMessage.replaceFirstChar { it.lowercase() } +
				"To add it, go to your saved server list, click '" -
				INFORMATIVE_UNIMPORTANT_FOCUS * "Edit" -
				"' on" +
				INFORMATIVE_UNIMPORTANT_FOCUS * "SuCraft" +
				"and set '" -
				INFORMATIVE_UNIMPORTANT_FOCUS * "Server Resource Packs" -
				"' to '" -
				INFORMATIVE_UNIMPORTANT_FOCUS * "Enabled" -
				"'."
	}

	private val failedDownloadMessage = component(color = ERROR_NOTHING_HAPPENED) {
		+"The resource pack failed to load." + INFORMATIVE_UNIMPORTANT * blocksWillBeReplacedMessage
	}

	private val recommendedVersion =
		join(
			noSeparators(),
			text("Optifine 1.19", INFORMATIVE_UNIMPORTANT_FOCUS)
				.underlined()
				.clickOpenURL("https://optifine.net/downloads"),
			text(" with Video Settings > Quality > Antialiasing = 16", INFORMATIVE_UNIMPORTANT)
		)

	private val recommendedOlderVersion =
		join(
			noSeparators(),
			text("Forge 1.16.5", INFORMATIVE_UNIMPORTANT_FOCUS)
				.underlined()
				.clickOpenURL("https://files.minecraftforge.net/net/minecraftforge/forge/index_1.16.5.html"),
			text(" with ", INFORMATIVE_UNIMPORTANT),
			text("Optifine", INFORMATIVE_UNIMPORTANT_FOCUS)
				.underlined()
				.clickOpenURL("https://optifine.net/downloads"),
			text(" and ", INFORMATIVE_UNIMPORTANT),
			text("XL Packets", INFORMATIVE_UNIMPORTANT_FOCUS)
				.underlined()
				.clickOpenURL("https://www.curseforge.com/minecraft/mc-mods/xl-packets/download/3629577")
		)

	private const val minimumProtocolVersionFullyCompatibleWithResourcePack = 759 // 1.19

	// Events

	init {
		on(PlayerResourcePackStatusEvent::class) {
			if (status != ACCEPTED) {
				player.runLater(sendMessageDelayAfterReceiveResourcePackStatus) {
					when (resourcePackStatus) {
						SUCCESSFULLY_LOADED -> {
							try {
								val viaAPI = Via.getAPI()
								val playerVersion = viaAPI.getPlayerVersion(this)
								if (playerVersion < minimumProtocolVersionFullyCompatibleWithResourcePack) {
									info(
										"Sending $name a message because their version may not be " +
												"compatible with the resource pack"
									)
									sendMessage(
										485992563785673333L, INFORMATIVE_UNIMPORTANT,
										ProtocolVersion
											.getProtocol(minimumProtocolVersionFullyCompatibleWithResourcePack).name
//										ProtocolVersion.getProtocol(playerVersion).name
									) {
										+"The server is on version" + INFORMATIVE_UNIMPORTANT_FOCUS * variable - "." +
												"It is recommended to use" + recommendedVersion -
												", or" + recommendedOlderVersion - "."
									}
								}
							} catch (e: Exception) {
								warning(
									"An exception occurred while potentially sending a player ($name) a message " +
											"about their Minecraft version, " +
											"because they successfully loaded the resource pack:"
								)
								e.printStackTrace()
							}
						}
						DECLINED -> {
							info("Sending $name a message because they declined the resource pack")
							sendMessage(declinedResourcePackMessage)
						}
						FAILED_DOWNLOAD -> {
							info("Sending $name a message because their resource pack download failed")
							sendMessage(failedDownloadMessage)
						}
						else -> {}
					}
				}
			}
		}
	}

}