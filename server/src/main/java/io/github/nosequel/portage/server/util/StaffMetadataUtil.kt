package io.github.nosequel.portage.server.util

import org.bukkit.entity.Player
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.plugin.java.JavaPlugin

class StaffMetadataUtil {

    companion object {
        /**
         * Toggle the metadata of a player
         */
        fun toggleMetadata(
            player: Player,
            metadata: StaffMetadataType,
            toggled: Boolean,
            plugin: JavaPlugin
        ) {
            player.setMetadata(metadata.id, FixedMetadataValue(plugin, toggled))
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