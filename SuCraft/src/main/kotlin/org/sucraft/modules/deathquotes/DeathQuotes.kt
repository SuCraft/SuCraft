/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.deathquotes

import org.bukkit.event.entity.PlayerDeathEvent
import org.sucraft.common.event.on
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.scheduler.runNextTick
import org.sucraft.common.text.by
import org.sucraft.common.text.quoteArrayOf

/**
 * Displays a quote when a player dies.
 */
object DeathQuotes : SuCraftModule<DeathQuotes>() {

	// Settings

	@Suppress("SpellCheckingInspection")
	private val deathQuotes = quoteArrayOf(
		"Oh dear, you're dead!",
		"Rest in peace.",
		"There is no end to this but death."
				by "Unknown",
		"Living is what scares me. Dying is easy."
				by "Unknown",
		"吃得苦中苦，方为人上人"
				by "Chinese proverb",
		"I desire the things which will destroy me in the end"
				by "Sylvia Plath",
		"The day of my birth, my death began its walk. It is walking toward me, without hurrying."
				by "Jean Cocteau",
		"Death is beautiful when to be seen as a law - it is as common as life."
				by "Henry David Thoreau",
		"A man who would not die for something is not fit to live."
				by "Martin Luther King",
		"I didn't attend the funeral, but I sent a nice letter saying I approved of it."
				by "Mark Twain",
		"Our dead are never dead to us, until we have forgotten them."
				by "George Eliot",
		"Suicide is man's way of telling God, 'You can't fire me - I quit.'"
				by "Bill Maher",
		"I saw a few die of hunger; of eating, a hundred thousand."
				by "Benjamin Franklin",
		"The life of the dead is placed in the memory of the living."
				by "Marcus Tullius Cicero",
		"The day which we fear as our last is but the birthday of eternity."
				by "Lucius Annaeus Seneca",
		"Die, verb: To stop sinning suddenly."
				by "Elbert Hubbard",
		"A friend who does, it's something of you who dies."
				by "Gustave Flaubert",
		"No one can confidently say that he will still be living tomorrow."
				by "Euripides",
		"I'm not afraid of death; I just don't want to be there when it happens."
				by "Woody Allen",
		"To the well-organized mind, death is but the next great adventure."
				by "J.K. Rowling",
		"I'm the one that's got to die when it's time for me to die, so let me live my life the way I want to."
				by "Jimi Hendrix",
		"Death ends a life, not a relationship."
				by "Mitch Albom",
		"A thing is not necessarily true because a man dies for it."
				by "Oscar Wilde",
		"I don't want to die without any scars."
				by "Chuck Palahniuk",
		"Don't feel bad, I'm usually about to die."
				by "Rick Riordan",
		"I could die for you. But I couldn't, and wouldn't, live for you."
				by "Ayn Rand",
		"To die will be an awfully big adventure."
				by "J.M. Barrie",
		"Better to flee from death than feel its grip."
				by "Homerus",
		"When a great life sets it leaves an afterglow on the sky far into the night."
				by "Austin O'Malley",
		"We all labour against our own cure, for death is the cure of all diseases."
				by "Thomas Browne",
		"You only live twice. Once when you are born and once when you look death in the face."
				by "Ian Fleming",
		"The world's an inn, and death the journey's end."
				by "John Dryden",
		"As soon as one is born, one starts dying."
				by "Luigi Pirandello",
		"Death doesn't bargain."
				by "August Strindberg",
		"Death is when the monsters get you."
				by "Stephen King",
		"No one on his deathbed ever said, 'I wish I had spent more time on my business.'"
				by "Paul E. Tsongas",
		"Death is the dropping of the flower, that the fruit may swell."
				by "Henry Ward Beecher",
		"Some people die, others just run out of fuel."
				by "Carmen Boullosa",
		"There are some dead who are more alive than the living."
				by "Romain Rolland",
		"We live as we die, and die as we live.."
				by "Edward Counsel",
		"Death always leaves one singer to mourn."
				by "Katherine Anne Porter",
		"Shun death, is my advice."
				by "Robert Browning",
		"Death lies dormant in each of us and will bloom in time."
				by "Dean Koontz",
		"One day the ordinariness will be terminally punctuated by the extraordinary full stop of death.."
				by "Glen Duncan",
		"That's life. Still the best alternative to death."
				by "Cody McFadyen",
		"Death has a hundred hands and walks by a thousand ways."
				by "T.S. Eliot",
		"Death will get you sober."
				by "Elizabeth Selvin",
		"Life is hard, but death is even harder."
				by "Peter Kreeft"
	)

	// Events

	init {
		on(PlayerDeathEvent::class) {
			runNextTick(player) {
				sendMessage(deathQuotes.random().component)
			}
		}
	}

}