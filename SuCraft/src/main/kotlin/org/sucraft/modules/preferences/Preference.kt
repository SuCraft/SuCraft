package org.sucraft.modules.preference

import org.bukkit.entity.Player
import org.sucraft.modules.offlineplayerinformation.DetailedOfflinePlayer

interface Preference {

    val owner: DetailedOfflinePlayer
    val name: String
    val enabled: Boolean

    fun isEnabledBy(player: DetailedOfflinePlayer)
    
    fun isEnabledBy(player: Player)
}
