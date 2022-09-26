/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.homequotes

import it.unimi.dsi.fastutil.objects.Object2LongMap
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap
import org.sucraft.common.event.on
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.text.by
import org.sucraft.common.text.quoteArrayOf
import org.sucraft.common.time.TimeInMinutes
import org.sucraft.modules.homes.event.PlayerTeleportToHomeEvent
import java.util.*

/**
 * Displays a quote when a player teleports home.
 */
object HomeQuotes : SuCraftModule<HomeQuotes>() {

	// Settings

	private val minimumTimeSinceLastHomeTeleport = TimeInMinutes(10)

	@Suppress("SpellCheckingInspection")
	private val homeQuotes = quoteArrayOf(
		"Home sweet home!",
		"Welcome home.",
		"There's no place like home...",
		"The best journey takes you home.",
		"It takes hands to build a house, but only hearts can build a home."
				by "Unknown",
		"East west, home best."
				by "Dutch proverb",
		"To know the road ahead, ask those coming back."
				by "Chinese proverb",
		"金窩，銀窩，不如自家的狗窩"
				by "Chinese proverb",
		"Home is where the wifi connects automatically."
				by "The Internet",
		"Home is a shelter from storms - all sorts of storms."
				by "William J. Bennett",
		"Home is not where you live but where they understand you."
				by "Christian Morgenstern",
		"Peace - that was the other name for home."
				by "Kathleen Norris",
		"I had rather be on my farm than be emperor of the world."
				by "George Washington",
		"Home is the nicest word there is."
				by "Laura Ingalls Wilder",
		"The ornament of a house is the friends who frequent it."
				by "Ralph Waldo Emerson",
		"Home is everything you can walk to."
				by "Jerry Spinelli",
		"Home is anywhere that you know all your friends and all your enemies."
				by "Orson Scott Card",
		"Nature is not a place to visit. It is home."
				by "Gary Snyder",
		"Home is people. Not a place."
				by "Robin Hobb",
		"A man travels the world over in search of what he needs and returns home to find it."
				by "George Augustus Moore",
		"Perhaps home is not a place but simply an irrevocable condition."
				by "James Baldwin",
		"Home is where somebody notices when you are no longer there."
				by "Aleksandar Hemon",
		"Home is where my habits have a habitat"
				by "Fiona Apple",
		"Home is where, when you have to go there, they have to take you in."
				by "Lois McMaster Bujold",
		"Be grateful for the home you have, knowing that at this moment, all you have is all you need."
				by "Sarah Ban Breathnach",
		"An empty house is like a body from which life has departed."
				by "Samuel Butler",
		"You are a king by your own fireside, as much as any monarch in his throne."
				by "Miguel De Cervantes",
		"A man's home is his wife's castle."
				by "Alexander Chase",
		"Home - that blessed word, which opens to the human heart the most perfect glimpse of heaven."
				by "Lydia M. Child",
		"There is no place more delightful than one's own fireplace."
				by "Marcus Tullius Cicero",
		"Going home must be like going to render an account."
				by "Joseph Conrad",
		"A house is a machine for living in."
				by "Le Corbusier",
		"Where thou art, that is home."
				by "Emily Dickinson",
		"Many a man who thinks to found a home discovers that he has merely opened a tavern for his friends."
				by "Norman Douglas",
		"There is no sanctuary of virtue like home."
				by "Edward Everett",
		"He is the happiest, be he king or peasant, who finds peace in his home."
				by "Johann Wolfgang Von Goethe",
		"One never reaches home, but wherever friendly paths intersect the whole world looks like home for a time."
				by "Hermann Hesse",
		"The worst feeling in the world is the homesickness that comes over a man occasionally when he is at home."
				by "Edgar Watson Howe",
		"One returns to the place one came from."
				by "Jean De La Fontaine",
		"A man's home may seem to be his castle on the outside; inside, it is more often his nursery."
				by "Clare Boothe Luce",
		"Home, the spot of earth supremely blest, A dearer, sweeter spot than all the rest."
				by "Robert Montgomery",
		"The ordinary acts we practice every day at home " +
				"are of more importance to the soul than their simplicity might suggest."
				by "Thomas Moore",
		"Have nothing in your house that you do not know to be useful, or believe to be beautiful."
				by "William Morris",
		"Home interprets heaven. Home is heaven for beginners."
				by "Charles H. Parkhurst",
		"Home is where the heart is."
				by "Pliny The Elder",
		"Home is the most popular, and will be the most enduring of all earthly establishments."
				by "Channing Pollock",
		"Home is any four walls that enclose the right person."
				by "Helen Rowland",
		"There is room in the smallest cottage for a happy loving pair."
				by "Johann Friedrich Von Schiller",
		"People usually are the happiest at home."
				by "William Shakespeare",
		"Home is where there's one to love us."
				by "Charles Swain",
		"The farther away, the closer the home becomes."
				by "Dejan Stojanovic",
		"No matter where on the map you dwell, as long as you are loved, you will always have a home."
				by "Daniella Kessler",
		"Whether it's a dirt hut or a diamond mansion, when the furnace touches the crafting table, it's home."
				by "Trpi Cloud",
		"The magic thing about home is that it feels good to leave, and it feels even better to come back."
				by "Wendy Wunder",
		"Home is not a place... it's a feeling."
				by "Cecelia Ahern",
		"A house is made with walls and beams; a home is built with love and dreams."
				by "Ralph Waldo Emerson",
		"What I love most about my home is who I share it with."
				by "Tad Carpenter",
		"Home is a place you grow up wanting to leave, and grow old wanting to get back to."
				by "John Ed Pearce",
		"May your home always be too small to hold all of your friends."
				by "Irish proverb",
		"Home is where one starts from."
				by "T.S. Eliot",
		"Life takes you unexpected places, love brings you home."
				by "Melissa McClone",
		"Chase your dreams but always know the road that will lead you home again."
				by "Ziad K. Abdelnour",
		"No one realizes how beautiful it is to travel until he comes home " +
				"and rests his head on his old, familiar pillow."
				by "Lin Yutang"
	)

	// Data

	/**
	 * In milliseconds since the Unix epoch.
	 */
	private val lastHomeTeleportTimes: Object2LongMap<UUID> = Object2LongOpenHashMap<UUID>(10).apply {
		defaultReturnValue(-1L)
	}

	// Events

	init {
		// Listen to home teleports to send a quote
		on(PlayerTeleportToHomeEvent::class) {
			// Don't send a quote if the home belongs to someone else
			if (!isOwnHome) return@on
			// Retrieve the last home teleport time before updating it
			val lastHomeTeleportTime = lastHomeTeleportTimes.getLong(player.uniqueId)
			// Update the last home teleport time
			lastHomeTeleportTimes[player.uniqueId] = System.currentTimeMillis()
			// Check if the last home teleport time was too recent
			if (lastHomeTeleportTime != -1L)
				if (lastHomeTeleportTime > System.currentTimeMillis() - minimumTimeSinceLastHomeTeleport.millis)
					return@on
			// Send the quote
			player.sendMessage(homeQuotes.random().component)
		}
	}

}