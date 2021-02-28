package io.github.nosequel.portage.bukkit.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer

class UUIDFetcher {

    companion object {

        /**
         * Get the [OfflinePlayer] object by a [String]
         */
        @JvmStatic
        fun getOfflinePlayer(name: String) : OfflinePlayer = runBlocking(Dispatchers.IO) {
            return@runBlocking Bukkit.getOfflinePlayer(name)
        }
    }

}