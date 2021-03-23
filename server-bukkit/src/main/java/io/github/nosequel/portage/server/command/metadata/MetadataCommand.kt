package io.github.nosequel.portage.server.command.metadata

import io.github.nosequel.portage.bukkit.util.command.annotation.Command
import io.github.nosequel.portage.server.util.StaffMetadataUtil
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class MetadataCommand {

    @Command(label = "metadata", aliases = ["togglemetadata"], permission = "portage.staff")
    fun toggleMetadata(player: Player, metadata: StaffMetadataUtil.StaffMetadataType, toggled: Boolean) {
        val message: String = if (toggled) {
            "${ChatColor.YELLOW}You have toggled your ${metadata.id} metadata ${ChatColor.RED}on."
        } else {
            "${ChatColor.YELLOW}You have toggled your ${metadata.id} metadata ${ChatColor.RED}off."
        }

        StaffMetadataUtil.toggleMetadata(player, metadata, toggled)
        player.sendMessage(message)
    }

    @Command(label = "togglestaffchat", aliases = ["togglesc", "tsc"], permission = "portage.staff")
    fun toggleStaffChat(player: Player, toggled: Boolean) {
        player.performCommand("metadata ${StaffMetadataUtil.StaffMetadataType.STAFF_CHAT} $toggled")
    }

    @Command(label = "toggleconnectivity", aliases = ["toggleconnect", "tc"], permission = "portage.staff")
    fun toggleConnectivity(player: Player, toggled: Boolean) {
        player.performCommand("metadata ${StaffMetadataUtil.StaffMetadataType.CONNECTIVITY} $toggled")
    }
}