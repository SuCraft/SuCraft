/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.dynamicmotd

import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.JoinConfiguration
import net.kyori.adventure.text.format.NamedTextColor.*
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration.BOLD
import net.kyori.adventure.text.format.TextDecoration.OBFUSCATED
import org.sucraft.common.text.*

// General settings

val focusColors = arrayOf(
	AQUA,
	BLUE,
	GOLD,
	GREEN,
	LIGHT_PURPLE,
	RED,
	YELLOW
)
val neutralColor: TextColor = GRAY
val unnoticeableColor: TextColor = DARK_GRAY

// Special case

const val wonderfulIPAddress = "47.36.3.76"
const val wonderfulMessageProbability = 0.2
val wonderfulMOTD = text("You're wonderful \u2665")

// For 1 online player

val for1PlayerBaseColor = neutralColor
val for1PlayerMOTDs = arrayOf(
	pattern { +"Join the lonely" + variable - "!" },
	pattern { variable + "loves you!" },
	pattern { variable + "is waiting for you!" },
	pattern { +"It's safe, it's just" + variable - "!" },
	pattern { +"Provide support for" + variable - "!" },
	pattern { +"You're friends with" + variable + "right?" },
	pattern { +"Come in and play with" + variable - "!" },
	pattern { variable + "is enjoying themselves!" }
)

// For a few online players

val forFewPlayersSeparator = text(", ")
val forFewPlayersBaseColor = neutralColor
val forFewPlayersJoinConfiguration = JoinConfiguration.separator(forFewPlayersSeparator)

// For no or many online players

val miscMOTDs = arrayOf(
	"Since August 2011!",
	"Since Minecraft beta!",
	*Array(2) { "Single-player, but together!" },
	*Array(5) { "From Cottage to Citadel!" },
	"Here be penguins!",
	"Build your dirt.",
	"Don't click here!",
	"Communication error. Joking!",
	"Run from the Pentagon!",
	"Sex on the beach",
	"Ruled by Skynet!",
	"Martijn composes music!",
	"isittimeforabeer.com",
	"Free shipping!",
	"Want some candy kid?",
	"Mwuhahahahahahahahaa:P",
	"Thor approves!",
	"Explosions everywhere.",
	"Epic survival!",
	"Potion of best server.",
	"Nom nom nom.",
	"Allez allays!",
	"Stay fit, eat bicycle.",
	"Our wheat is 10% glutenfree.",
	"Eek! Spiders!",
	"THIS - IS - SUPARTA!",
	"You're a clownfish",
	"Super starts with Su!",
	"Pssst. Eat me!",
	"Achievements. Achievements everywhere!",
	"Endorsed by YOUR dentist!",
	"Love is in the air...",
	"Not where Osama is",
	"Aaaand... refresh!",
	"Santa's favorite hobby!",
	"Pizzas on demand!",
	"Unicorns DO exist!",
	"Blackbeard's hidden treasure!",
	"Hugging trees!",
	"Where Elvis lives!",
	"Also known as heaven.",
	"Get dirty all the time!",
	"Now with cookies!",
	"Don't feed the Moderators!",
	"Low fat percentage!",
	"Capitalistic states!",
	"Unclimbable!",
	"Apes applaud you!",
	"Hasta la vista baby!",
	"Looking for a good time?",
	"Thy real life hath ended.",
	"Ice-cream!",
	"Almost slenderman-free!",
	"One of us.. One of us...",
	"HASKS!",
	"Endorsed by Martijn for President\u2122",
	text("Played by everyone during classes").strikethrough(),
	"That's what she said.",
	"\u4e00\u7dd2\u306b\u904a\u3073\u307e\u3057\u3087\u3046!! \\(\u2605\u03c9\u2605)/",
	"\u5efa\u9020\u4f60\u81ea\u5df1\u7684\u4e16\u754c\u0021",
	"Datacenters in Atlantis!",
	"Kim Jong-un's favorite server",
	"Kidnapping innocent villagers encouraged!",
	"You only lose once #sandstreetbets",
	"Unlikely to give you corona!",
	"Build the cobble wall!",
	"Make Thundertown great again!",
	component { +"The password is" + OBFUSCATED * "hunter2" },
	"Celebrating 11 years in 2022! <3",
	component { +"Are you" + BOLD * "bold" + "enough?" },
	"Joinable from Pocket Edition!",
	"Follows the second law of thermodynamics!",
	"&#3486;",
	"We made a pact with the Lag God!",
	"Coarse dirt has been nerfed",
	"\u0262\u1d04 can be exchanged for goods and services.",
	"Dark coffee with milk!",
	"Everyone in jail is a volunteer",
	"The rabbits are taking over",
	"Background checks for crossbows",
	"Melonade stands!",
	"Organic chorus fruit!",
	"\"No officer, this is bone meal for my crops\"",
	component { +"\"F\"" + unnoticeableColor * "-" + neutralColor * "Sun Tzu" },
	"Double-click to see status",
	"Knee-deep in powdered snow"
).map(::valueToComponent)