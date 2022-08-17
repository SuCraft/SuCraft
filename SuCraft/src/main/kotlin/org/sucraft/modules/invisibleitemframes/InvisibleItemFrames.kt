/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.invisibleitemframes

import org.bukkit.entity.ItemFrame
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.sucraft.common.event.on
import org.sucraft.common.itemstack.isEmpty
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.text.*

/**
 * Lets players shift-right-click item frames to make them invisible.
 */
object InvisibleItemFrames : SuCraftModule<InvisibleItemFrames>() {

	// Settings

	private fun createItemFrameVisibilityToggledComponent(becameVisible: Boolean) =
		component(color = BORING_SUCCESS) {
			+"The item frame is now" + BORING_SUCCESS_FOCUS * (if (becameVisible) "visible" else "invisible") +
					BORING_SUCCESS_EXTRA_DETAIL *
					"(shift-right-click it to ${if (becameVisible) "hide" else "show"} it again)" + "."
		}

	private val itemFrameBecameVisibleComponent by lazy {
		createItemFrameVisibilityToggledComponent(true)
	}

	private val itemFrameBecameInvisibleComponent by lazy {
		createItemFrameVisibilityToggledComponent(false)
	}

	// Events

	init {

		// Listen for interactions with item frames to toggle their invisibility if the interacting player is sneaking
		on(PlayerInteractEntityEvent::class) {
			// We do not need to do anything if the player is not sneaking
			if (!player.isSneaking) return@on
			// We only need to do anything if the entity being interacted with is an item frame
			(rightClicked as? ItemFrame)?.run {
				// Do not make empty item frames invisible
				if (item.isEmpty) return@on
				// Rotate back (to undo the normal result of this interaction)
				rotation = rotation.rotateCounterClockwise()
				// Toggle visibility
				isVisible = !isVisible
				// Tell the player what happened
				player.sendMessage(
					if (isVisible) itemFrameBecameVisibleComponent else itemFrameBecameInvisibleComponent
				)
			}
		}

		// Listen for damage to item frames, to make sure they become visible after their item is removed
		on(EntityDamageByEntityEvent::class) {
			(entity as? ItemFrame)?.isVisible = true
		}

	}

}