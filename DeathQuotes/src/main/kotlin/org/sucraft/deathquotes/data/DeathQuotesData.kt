/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.deathquotes.data

import org.bukkit.entity.Player
import org.sucraft.core.common.sucraft.chat.Quote

object DeathQuotesData {

	// Settings

	private val quotes: Array<Quote> = arrayOf(
		Quote("Oh dear, you're dead!"),
		Quote("Rest in peace."),
		Quote("I desire the things which will destroy me in the end", "Sylvia Plath"),
		Quote("There is no end to this but death.", "Unknown"),
		Quote("The day of my birth, my death began its walk. It is walking toward me, without hurrying.", "Jean Cocteau"),
		Quote("Death is beautiful when to be seen as a law - it is as common as life.", "Henry David Thoreau"),
		Quote("Living is what scares me. Dying is easy.", "Unknown"),
		Quote("A man who would not die for something is not fit to live.", "Martin Luther King"),
		Quote("I didn't attend the funeral, but I sent a nice letter saying I approved of it.", "Mark Twain"),
		Quote("Our dead are never dead to us, until we have forgotten them.", "George Eliot"),
		Quote("Suicide is man's way of telling God, 'You can't fire me - I quit.'", "Bill Maher"),
		Quote("I saw a few die of hunger; of eating, a hundred thousand.", "Benjamin Franklin"),
		Quote("The life of the dead is placed in the memory of the living.", "Marcus Tullius Cicero"),
		Quote("The day which we fear as our last is but the birthday of eternity.", "Lucius Annaeus Seneca"),
		Quote("Die, verb: To stop sinning suddenly.", "Elbert Hubbard"),
		Quote("A friend who does, it's something of you who dies.", "Gustave Flaubert"),
		Quote("No one can confidently say that he will still be living tomorrow.", "Euripides"),
		Quote("I'm not afraid of death; I just don't want to be there when it happens.", "Woody Allen"),
		Quote("To the well-organized mind, death is but the next great adventure.", "J.K. Rowling"),
		Quote("I'm the one that's got to die when it's time for me to die, so let me live my life the way I want to.", "Jimi Hendrix"),
		Quote("Death ends a life, not a relationship.", "Mitch Albom"),
		Quote("A thing is not necessarily true because a man dies for it.", "Oscar Wilde"),
		Quote("I don't want to die without any scars.", "Chuck Palahniuk"),
		Quote("Don't feel bad, I'm usually about to die.", "Rick Riordan"),
		Quote("I could die for you. But I couldn't, and wouldn't, live for you.", "Ayn Rand"),
		Quote("To die will be an awfully big adventure.", "J.M. Barrie"),
		Quote("Better to flee from death than feel its grip.", "Homerus"),
		Quote("When a great life sets it leaves an afterglow on the sky far into the night.", "Austin O'Malley"),
		Quote("We all labour against our own cure, for death is the cure of all diseases.", "Thomas Browne"),
		Quote("You only live twice. Once when you are born and once when you look death in the face.", "Ian Fleming"),
		Quote("The world's an inn, and death the journey's end.", "John Dryden"),
		Quote("As soon as one is born, one starts dying.", "Luigi Pirandello"),
		Quote("Death doesn't bargain.", "August Strindberg"),
		Quote("Death is when the monsters get you.", "Stephen King"),
		Quote("No one on his deathbed ever said, 'I wish I had spent more time on my business.'", "Paul E. Tsongas"),
		Quote("Death is the dropping of the flower, that the fruit may swell.", "Henry Ward Beecher"),
		Quote("Some people die, others just run out of fuel.", "Carmen Boullosa"),
		Quote("There are some dead who are more alive than the living.", "Romain Rolland"),
		Quote("We live as we die, and die as we live..", "Edward Counsel"),
		Quote("Death always leaves one singer to mourn.", "Katherine Anne Porter"),
		Quote("Shun death, is my advice.", "Robert Browning"),
		Quote("Death lies dormant in each of us and will bloom in time.", "Dean Koontz"),
		Quote("One day the ordinariness will be terminally punctuated by the extraordinary full stop of death..", "Glen Duncan"),
		Quote("That's life. Still the best alternative to death.", "Cody McFadyen"),
		Quote("Death has a hundred hands and walks by a thousand ways.", "T.S. Eliot"),
		Quote("Death will get you sober.", "Elizabeth Selvin"),
		Quote("Life is hard, but death is even harder.", "Peter Kreeft"),
		Quote("吃得苦中苦，方为人上人", "Chinese proverb")
	)

	// Implementation

	fun getRandomQuote() = quotes.random()

	fun sendRandomQuote(player: Player) =
		player.sendMessage(getRandomQuote().getComponent())

}