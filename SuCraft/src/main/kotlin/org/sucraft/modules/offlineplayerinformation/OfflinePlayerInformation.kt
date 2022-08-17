/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.modules.offlineplayerinformation

import com.github.shynixn.mccoroutine.bukkit.launch
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.sucraft.common.concurrent.IterableSubjectIndependentOperationBroker
import org.sucraft.common.concurrent.IterableSubjectIndependentOperationBrokerImpl
import org.sucraft.common.concurrent.ioScope
import org.sucraft.common.event.on
import org.sucraft.common.location.LocationCoordinates
import org.sucraft.common.location.LocationCoordinates.Companion.getCoordinates
import org.sucraft.common.module.SuCraftModule
import org.sucraft.common.player.getOnlinePlayer
import org.sucraft.main.SuCraftPlugin
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * Keeps track of the details of [offline players][DetailedOfflinePlayer].
 *
 * The implementation will try to minimize the number of
 * operations that require reading from player data files.
 *
 * Note that care must be taken not to rely on the correctness of the information returned
 * by this instance after time has passed, because for instance a player may have
 * changed their username without changing their [UUID]. However, since we clear any information known about an
 * offline player when they join, this should never practically matter, unless we make some sort of call
 * to the Mojang servers about a player while they have just changed their username without changing their UUID (and
 * that call being unrelated to the player joining) - and we are not making any such calls.
 */
object OfflinePlayerInformation : SuCraftModule<OfflinePlayerInformation>() {

	// Components

	override val components = listOf(
		ReadAllOfflinePlayerInformationOnPluginEnable
	)

	// Reverse name storage (from names to UUIDs): we always keep this information in memory once added

	/**
	 * A map from [player names][OfflinePlayer.getName] to the UUID.
	 * The player name is the last name the player joined with.
	 *
	 * The names are in their original case.
	 */
	private val reverseNameCache: MutableMap<String, UUID> = ConcurrentHashMap(0)

	/**
	 * A map from [player names][OfflinePlayer.getName] to the UUID.
	 * The player name is the last name the player joined with.
	 *
	 * The names are in lowercase.
	 */
	private val lowercaseReverseNameCache: MutableMap<String, UUID> = ConcurrentHashMap(0)

	/**
	 * This map is used to find the name for a [UUID] as it is currently stored in the reverse name storage above.
	 * This is so that we can remove outdated mappings if the name for a UUID has changed in the meantime.
	 */
	private val reverseReverseNameCache: MutableMap<UUID, String> = ConcurrentHashMap(0)

	// Brokers that acquire the data and store it in KnownOfflinePlayerInformation.

	private class KnownOfflinePlayerInformationFromDataFile(
		val name: String,
		val firstPlayed: Long,
		val lastLogin: Long?,
		val lastSeen: Long?,
		val bedSpawnLocation: LocationCoordinates?
	)

	private val dataFileLoader:
			IterableSubjectIndependentOperationBroker<UUID, KnownOfflinePlayerInformationFromDataFile?> =
		IterableSubjectIndependentOperationBrokerImpl(
			{ this },
			scope = ioScope,
			getAllSubjects = { Bukkit.getOfflinePlayers().map { it.uniqueId } }
		) task@{

			val offlinePlayer = Bukkit.getOfflinePlayer(it)
			val uuid = offlinePlayer.uniqueId
			// If no name is present, the player has not played before or is not sufficiently valid
			val name = offlinePlayer.name ?: return@task null
			// If the player has never played before, it is not sufficiently valid
			if (!offlinePlayer.hasPlayedBefore()) return@task null

			// Update the reverse name cache
			reverseReverseNameCache.compute(uuid) { _, existingName ->
				// Clear existing reverse pairs
				existingName?.let {
					lowercaseReverseNameCache.remove(existingName.lowercase())
					reverseNameCache.remove(existingName)
				}
				// Update the reverse name cache
				lowercaseReverseNameCache[name.lowercase()] = uuid
				reverseNameCache[name] = uuid
				name
			}

			// Return the information we find
			KnownOfflinePlayerInformationFromDataFile(
				name,
				offlinePlayer.firstPlayed,
				offlinePlayer.lastLogin,
				offlinePlayer.lastSeen,
				offlinePlayer.bedSpawnLocation?.run { getCoordinates() }
			)

		}

	// Extensions methods for DetailedOfflinePlayer that are the interface to the offline player's information

	/**
	 * @return The last [name][OfflinePlayer.getName], if known, that this player joined with.
	 */
	suspend fun DetailedOfflinePlayer.getName() =
		// Use the player's live data if online, else get the value from the data file
		getPlayer()?.name ?: dataFileLoader.compute(uuid)?.name

	/**
	 * @return [OfflinePlayer.getFirstPlayed], or null if the value is unknown or not present
	 * (never returns 0).
	 */
	suspend fun DetailedOfflinePlayer.getFirstPlayed() =
		// Use the player's live data if online, else get the value from the data file
		(getPlayer()?.firstPlayed ?: dataFileLoader.compute(uuid)?.firstPlayed).takeIf { it != 0L }

	/**
	 * @return [OfflinePlayer.getLastLogin], or null if the value is unknown or not present
	 * (never returns 0).
	 */
	suspend fun DetailedOfflinePlayer.getLastLogin() =
		// Use the player's live data if online, else get the value from the data file
		(getPlayer()?.lastLogin ?: dataFileLoader.compute(uuid)?.lastLogin).takeIf { it != 0L }

	/**
	 * @return [OfflinePlayer.getLastSeen], or null if the value is unknown or not present
	 * (never returns 0).
	 */
	suspend fun DetailedOfflinePlayer.getLastSeen() =
		// Use the player's live data if online, else get the value from the data file
		(getPlayer()?.lastSeen ?: dataFileLoader.compute(uuid)?.lastSeen).takeIf { it != 0L }

	/**
	 * @return [OfflinePlayer.getBedSpawnLocation] as [coordinates][LocationCoordinates],
	 * or null if the value is unknown or not present.
	 */
	suspend fun DetailedOfflinePlayer.getBedSpawnLocation() =
		// Use the player's live data if online, else get the value from the data file
		(getPlayer()?.bedSpawnLocation?.getCoordinates() ?: dataFileLoader.compute(uuid)?.bedSpawnLocation)

	// Other extension methods (transitively) supported by this module

	/**
	 * @return Whether any player that is still known joined with this UUID before.
	 */
	suspend fun UUID.hasDetailedOfflinePlayer() =
		getOnlinePlayer() != null || dataFileLoader.compute(this) != null

	/**
	 * @return This UUID as a [DetailedOfflinePlayer], or null if there exists no valid
	 * [DetailedOfflinePlayer] instance with this UUID.
	 */
	suspend fun UUID.getDetailedOfflinePlayer() =
		if (hasDetailedOfflinePlayer())
			DetailedOfflinePlayer(this)
		else
			null

	/**
	 * @return A [DetailedOfflinePlayer] that [last logged in with this name][DetailedOfflinePlayer.getName],
	 * or null if there exists no valid [DetailedOfflinePlayer] instance with this string as the last name.
	 */
	suspend fun String.getDetailedOfflinePlayer(): DetailedOfflinePlayer? {
		// Pre-compute the lowercase name
		val lowercase = lowercase()
		// Attempt to load the data file we already know of
		Bukkit.getOfflinePlayerIfCached(this)?.uniqueId?.let { dataFileLoader.compute(it) }
		// Load all data files if we have not found the right one yet
		if (lowercase !in lowercaseReverseNameCache) {
			dataFileLoader.precomputeAll()
		}
		return (reverseNameCache[this] ?: lowercaseReverseNameCache[lowercase])?.getDetailedOfflinePlayer()
	}

	/**
	 * @return Whether there exists a [DetailedOfflinePlayer] that
	 * [last logged in with this name][DetailedOfflinePlayer.getName].
	 */
	suspend fun String.hasDetailedOfflinePlayer() = getDetailedOfflinePlayer() != null

	/**
	 * @return This Player as a [DetailedOfflinePlayer].
	 */
	suspend fun Player.getDetailedOfflinePlayer() = uniqueId.getDetailedOfflinePlayer()!!

	// Events

	init {
		// Remove all known information for the player,
		// in case some of it was changed within this server session
		// (for example, the name of a premium account)
		on(AsyncPlayerPreLoginEvent::class) {
			// Update the reverse name cache
			reverseReverseNameCache.compute(uniqueId) { _, existingName ->
				// Clear existing reverse pairs
				existingName?.let {
					lowercaseReverseNameCache.remove(existingName.lowercase())
					reverseNameCache.remove(existingName)
				}
				@Suppress("GrazieInspection")
				// Make sure the existing reverse reverse entry is removed
				null
			}
			// Invalidate the data file information
			SuCraftPlugin.instance.launch { dataFileLoader.invalidate(uniqueId) }
		}
	}

	// Functionality provided directly by this class

	/**
	 * @return All [name][DetailedOfflinePlayer.getName]s that were last used by some [DetailedOfflinePlayer].
	 */
	suspend fun getAllUsedNames(): Set<String> {
		// Load all data files to know all the names
		dataFileLoader.precomputeAll()
		return reverseNameCache.keys
	}

	/**
	 * @return All [UUID][DetailedOfflinePlayer.uuid]s that are used by some [DetailedOfflinePlayer].
	 */
	suspend fun getAllUsedUUIDs(): Set<UUID> {
		// Load all data files to know all the names
		dataFileLoader.precomputeAll()
		return reverseReverseNameCache.keys
	}

}