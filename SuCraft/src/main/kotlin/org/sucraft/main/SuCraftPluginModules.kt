/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.main

import org.sucraft.modules.addpermissionstocommands.AddPermissionsToCommands
import org.sucraft.modules.anvilmechanics.AnvilMechanics
import org.sucraft.modules.barriersandlightsdrop.BarriersAndLightsDrop
import org.sucraft.modules.breakfreeindependententitiesonquit.BreakFreeIndependentEntitiesOnQuit
import org.sucraft.modules.coloredbucketsofaquaticmob.ColoredBucketsOfAquaticMob
import org.sucraft.modules.craftablealcoholicbeverages.CraftableAlcoholicBeverages
import org.sucraft.modules.craftablebarriersandlights.CraftableBarriersAndLights
import org.sucraft.modules.craftablebetagoldenapple.CraftableBetaGoldenApple
import org.sucraft.modules.craftablecoral.CraftableCoral
import org.sucraft.modules.craftabledebugstick.CraftableDebugStick
import org.sucraft.modules.customjockeys.CustomJockeys
import org.sucraft.modules.deathquotes.DeathQuotes
import org.sucraft.modules.discordcommand.DiscordCommand
import org.sucraft.modules.discordinformation.DiscordInformation
import org.sucraft.modules.donators.Donators
import org.sucraft.modules.dropminecartsonleave.DropMinecartsOnLeave
import org.sucraft.modules.dynamicmotd.DynamicMOTD
import org.sucraft.modules.eastereggcommand.EasterEggCommand
import org.sucraft.modules.enderdragondropselytra.EnderDragonDropsElytra
import org.sucraft.modules.giantsspawn.GiantsSpawn
import org.sucraft.modules.glassalwaysdrops.GlassAlwaysDrops
import org.sucraft.modules.harmlessentities.HarmlessEntities
import org.sucraft.modules.homequotes.HomeQuotes
import org.sucraft.modules.homes.Homes
import org.sucraft.modules.includedcustomjockeys.IncludedCustomJockeys
import org.sucraft.modules.invisibleitemframes.InvisibleItemFrames
import org.sucraft.modules.lightningcommand.LightningCommand
import org.sucraft.modules.monsterfreeareas.MonsterFreeAreas
import org.sucraft.modules.mysteryboxes.MysteryBoxes
import org.sucraft.modules.networkratelimits.NetworkRateLimits
import org.sucraft.modules.nojumpingmobfalldamage.NoJumpingMobFallDamage
import org.sucraft.modules.offlineplayerinformation.OfflinePlayerInformation
import org.sucraft.modules.opmecommand.OpMeCommand
import org.sucraft.modules.performanceadaptation.PerformanceAdaptation
import org.sucraft.modules.pingcommand.PingCommand
import org.sucraft.modules.playercompasses.PlayerCompasses
import org.sucraft.modules.portabletoolblocks.PortableToolBlocks
import org.sucraft.modules.preventmobexplosionblockdamage.PreventMobExplosionBlockDamage
import org.sucraft.modules.redyeingrecipes.RedyeingRecipes
import org.sucraft.modules.replacepermissionmessages.ReplacePermissionMessages
import org.sucraft.modules.resourcepackandversionwarnings.ResourcePackAndVersionWarnings
import org.sucraft.modules.ridemobs.RideMobs
import org.sucraft.modules.shortcutcraftingrecipes.ShortcutCraftingRecipes
import org.sucraft.modules.shorttermbackups.ShortTermBackups
import org.sucraft.modules.teleportfollowingmobsbeforeunload.TeleportFollowingMobsBeforeUnload
import org.sucraft.modules.teleportfollowingmobsfromthevoid.TeleportFollowingMobsFromTheVoid
import org.sucraft.modules.tenyearselytra.TenYearsElytra
import org.sucraft.modules.unsignbooks.UnsignBooks
import org.sucraft.modules.uuidcommand.UUIDCommand
import org.sucraft.modules.clientpacketacceptance.ClientPacketAcceptance
import org.sucraft.modules.dispenserscanlightportals.DispensersCanLightPortals
import org.sucraft.modules.geyserpermissions.GeyserPermissions
import org.sucraft.modules.icanhasbukkitcommandalias.ICanHasBukkitCommandAlias
import org.sucraft.modules.inspectcommandalias.InspectCommandAlias
import org.sucraft.modules.loginvulnerableentitydamage.LogInvulnerableEntityDamage
import org.sucraft.modules.moremobheads.MoreMobHeads
import org.sucraft.modules.rulescommand.RulesCommand
import org.sucraft.modules.zombiehorsejockeys.ZombieHorseJockeys

val modules by lazy {
	arrayOf(
		AddPermissionsToCommands,
		AnvilMechanics,
		BarriersAndLightsDrop,
		BreakFreeIndependentEntitiesOnQuit,
		ClientPacketAcceptance,
		ColoredBucketsOfAquaticMob,
		CraftableAlcoholicBeverages,
		CraftableBarriersAndLights,
		CraftableBetaGoldenApple,
		CraftableCoral,
		CraftableDebugStick,
		CustomJockeys,
		DeathQuotes,
		DiscordCommand,
		DiscordInformation,
		DispensersCanLightPortals,
		Donators,
		DropMinecartsOnLeave,
		DynamicMOTD,
		EasterEggCommand,
		EnderDragonDropsElytra,
		GeyserPermissions,
		GiantsSpawn,
		GlassAlwaysDrops,
		HarmlessEntities,
		Homes,
		HomeQuotes,
		ICanHasBukkitCommandAlias,
		IncludedCustomJockeys,
		InspectCommandAlias,
		InvisibleItemFrames,
		LightningCommand,
		LogInvulnerableEntityDamage,
		MonsterFreeAreas,
		MoreMobHeads,
		MysteryBoxes,
		NetworkRateLimits,
		NoJumpingMobFallDamage,
		OfflinePlayerInformation,
		OpMeCommand,
		PerformanceAdaptation,
		PingCommand,
		PlayerCompasses,
		PortableToolBlocks,
		PreventMobExplosionBlockDamage,
		RedyeingRecipes,
		ReplacePermissionMessages,
		ResourcePackAndVersionWarnings,
		RideMobs,
		RulesCommand,
		ShortcutCraftingRecipes,
		ShortTermBackups,
		TeleportFollowingMobsBeforeUnload,
		TeleportFollowingMobsFromTheVoid,
		TenYearsElytra,
		UnsignBooks,
		UUIDCommand,
		ZombieHorseJockeys
	)
}