/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.homequotes.data

import org.bukkit.entity.Player
import org.sucraft.core.common.sucraft.chat.Quote

object HomeQuotesData {

	// Settings

	private val quotes: Array<Quote> = arrayOf(
		Quote("Home sweet home!"),
		Quote("Welcome home."),
		Quote("Home is a shelter from storms - all sorts of storms.", "William J. Bennett"),
		Quote("There's no place like home..."),
		Quote("East west, home best.", "Dutch proverb"),
		Quote("Home is not where you live but where they understand you.", "Christian Morgenstern"),
		Quote("Peace - that was the other name for home.", "Kathleen Norris"),
		Quote("It takes hands to build a house, but only hearts can build a home.", "Unknown"),
		Quote("I had rather be on my farm than be emperor of the world.", "George Washington"),
		Quote("Home is the nicest word there is.", "Laura Ingalls Wilder"),
		Quote("The ornament of a house is the friends who frequent it.", "Ralph Waldo Emerson"),
		Quote("Home is everything you can walk to.", "Jerry Spinelli"),
		Quote("Home is anywhere that you know all your friends and all your enemies.", "Orson Scott Card"),
		Quote("Nature is not a place to visit. It is home.", "Gary Snyder"),
		Quote("Home is people. Not a place.", "Robin Hobb"),
		Quote("A man travels the world over in search of what he needs and returns home to find it.", "George Augustus Moore"),
		Quote("Perhaps home is not a place but simply an irrevocable condition.", "James Baldwin"),
		Quote("Home is where somebody notices when you are no longer there.", "Aleksandar Hemon"),
		Quote("Home is where my habits have a habitat", "Fiona Apple"),
		Quote("Home is where, when you have to go there, they have to take you in.", "Lois McMaster Bujold"),
		Quote("Be grateful for the home you have, knowing that at this moment, all you have is all you need.", "Sarah Ban Breathnach"),
		Quote("An empty house is like a body from which life has departed.", "Samuel Butler"),
		Quote("You are a king by your own fireside, as much as any monarch in his throne.", "Miguel De Cervantes"),
		Quote("A man's home is his wife's castle.", "Alexander Chase"),
		Quote("Home - that blessed word, which opens to the human heart the most perfect glimpse of heaven.", "Lydia M. Child"),
		Quote("There is no place more delightful than one's own fireplace.", "Marcus Tullius Cicero"),
		Quote("Going home must be like going to render an account.", "Joseph Conrad"),
		Quote("A house is a machine for living in.", "Le Corbusier"),
		Quote("Where thou art, that is home.", "Emily Dickinson"),
		Quote("Many a man who thinks to found a home discovers that he has merely opened a tavern for his friends.", "Norman Douglas"),
		Quote("There is no sanctuary of virtue like home.", "Edward Everett"),
		Quote("He is the happiest, be he king or peasant, who finds peace in his home.", "Johann Wolfgang Von Goethe"),
		Quote("One never reaches home, but wherever friendly paths intersect the whole world looks like home for a time.", "Hermann Hesse"),
		Quote("The worst feeling in the world is the homesickness that comes over a man occasionally when he is at home.", "Edgar Watson Howe"),
		Quote("One returns to the place one came from.", "Jean De La Fontaine"),
		Quote("A man's home may seem to be his castle on the outside; inside, it is more often his nursery.", "Clare Boothe Luce"),
		Quote("Home, the spot of earth supremely blest, A dearer, sweeter spot than all the rest.", "Robert Montgomery"),
		Quote("The ordinary acts we practice every day at home are of more importance to the soul than their simplicity might suggest.", "Thomas Moore"),
		Quote("Have nothing in your house that you do not know to be useful, or believe to be beautiful.", "William Morris"),
		Quote("Home interprets heaven. Home is heaven for beginners.", "Charles H. Parkhurst"),
		Quote("Home is where the heart is.", "Pliny The Elder"),
		Quote("Home is the most popular, and will be the most enduring of all earthly establishments.", "Channing Pollock"),
		Quote("Home is any four walls that enclose the right person.", "Helen Rowland"),
		Quote("There is room in the smallest cottage for a happy loving pair.", "Johann Friedrich Von Schiller"),
		Quote("People usually are the happiest at home.", "William Shakespeare"),
		Quote("Home is where there's one to love us.", "Charles Swain"),
		Quote("The farther away, the closer the home becomes.", "Dejan Stojanovic"),
		Quote("No matter where on the map you dwell, as long as you are loved, you will always have a home.", "Daniella Kessler"),
		Quote("Home is where the wifi connects automatically.", "The Internet"),
		Quote("Whether it's a dirt hut or a diamond mansion, when the furnace touches the crafting table, it's home.", "Trpi Cloud"),
		Quote("The magic thing about home is that it feels good to leave, and it feels even better to come back.", "Wendy Wunder"),
		Quote("Home is not a place... it's a feeling.", "Cecelia Ahern"),
		Quote("A house is made with walls and beams; a home is built with love and dreams.", "Ralph Waldo Emerson"),
		Quote("What I love most about my home is who I share it with.", "Tad Carpenter"),
		Quote("Home is a place you grow up wanting to leave, and grow old wanting to get back to.", "John Ed Pearce"),
		Quote("May your home always be too small to hold all of your friends.", "Irish proverb"),
		Quote("Home is where one starts from.", "T.S. Eliot"),
		Quote("The best journey takes you home."),
		Quote("Life takes you unexpected places, love brings you home.", "Melissa McClone"),
		Quote("Chase your dreams but always know the road that will lead you home again.", "Ziad K. Abdelnour"),
		Quote("To know the road ahead, ask those coming back.", "Chinese proverb"),
		Quote("No one realizes how beautiful it is to travel until he comes home and rests his head on his old, familiar pillow.", "Lin Yutang"),
		Quote("金窩，銀窩，不如自家的狗窩", "Chinese proverb")
	)

	// Implementation

	fun getRandomQuote() = quotes.random()

	fun sendRandomQuote(player: Player) =
		player.sendMessage(getRandomQuote().getComponent())

}