package io.github.nosequel.portage.server.util

import io.github.nosequel.portage.server.ServerPlugin
import org.bukkit.entity.Player
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.plugin.java.JavaPlugin

class StaffMetadataUtil {

    companion object {
        /**
         * Toggle the metadata of a player
         */
        fun toggleMetadata(player: Player, metadata: StaffMetadataType, toggled: Boolean) {
            player.setMetadata(metadata.id, FixedMetadataValue(JavaPlugin.getPlugin(ServerPlugin::class.java), toggled))
        }

        /**
         * Check if the player has the metadata
         */
        fun hasMetadata(player: Player, metadata: StaffMetadataType): Boolean {
            return player.hasMetadata(metadata.id) && player.getMetadata(metadata.id)[0].asBoolean()
        }
    }

    enum class StaffMetadataType(val id: String) {

        CONNECTIVITY("connectivity-notifications"),
        STAFF_CHAT("staff-chat-notifications"),

    }
}