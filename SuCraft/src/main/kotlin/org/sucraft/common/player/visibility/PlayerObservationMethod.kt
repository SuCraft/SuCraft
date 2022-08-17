/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.player.visibility

import org.bukkit.entity.Player
import org.sucraft.common.player.visibility.PlayerObservationMethod.PHYSICAL
import org.sucraft.common.player.visibility.PlayerObservationMethod.PUBLIC_CHAT

/**
 * Methods by which players can be observed (and thus via which players can be visible or not).
 *
 * In the future, it is planned to have players only ever be hidden via the [PHYSICAL] method,
 * and always visible through the other methods.
 * Currently, the default Bukkit [Player.showPlayer], [Player.hidePlayer] and
 * [Player.canSee] methods are used, where being hidden means being hidden via every method
 * except [PUBLIC_CHAT].
 */
enum class PlayerObservationMethod {

	/**
	 * Observing through the tab list:
	 * if the player is hidden via this method,
	 * they will not appear in the tab list, and no network packets regarding this player
	 * will be sent to the observer.
	 *
	 * When a player becomes hidden via this method from an observer and was previously visible,
	 * the observer should receive a packet to indicate the player leaving the server, and vice versa
	 * for becoming visible and receiving a packet to indicate joining the server.
	 *
	 * Being hidden via this method also requires being hidden via [PHYSICAL].
	 *
	 * Being hidden via this method currently happens using the default Bukkit
	 * [Player.showPlayer] and [Player.hidePlayer] methods, but that is not desired,
	 * and it should be modified in the future so that this method never applies at all.
	 */
	TAB_LIST,

	/**
	 * Observing through physical presence:
	 * if the player is hidden via this method,
	 * the player will not be physically visible to the observer,
	 * and no network packets regarding this player, except potentially packets
	 * to indicate joining and leaving the server
	 * (if the player is not hidden via [TAB_LIST]), will be sent to the observer.
	 */
	PHYSICAL,

	/**
	 * Observing through public chat:
	 * if the player is hidden via this method,
	 * public chat messages sent by the player will not be sent to the observer.
	 */
	PUBLIC_CHAT,

	/**
	 * Observing through Commands:
	 * if the player is hidden via this method,
	 * Commands run by the observer where the player is an argument will fail to find them.
	 *
	 * Being hidden via this method also requires being hidden via [COMMAND_TAB_COMPLETION].
	 */
	COMMANDS,

	/**
	 * Observing through command tab completion:
	 * if the player is hidden via this method,
	 * command tab completion requested by the observer will not include this player.
	 */
	COMMAND_TAB_COMPLETION

}