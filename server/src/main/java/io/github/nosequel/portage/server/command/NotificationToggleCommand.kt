package io.github.nosequel.portage.server.command

import io.github.nosequel.portage.bukkit.util.command.annotation.Command
import io.github.nosequel.portage.server.util.StaffMetadataUtil
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class NotificationToggleCommand(private val plugin: JavaPlugin) {

    @Command(label = "notification",
        aliases = ["togglenotifications", "notis", "notifications"],
        permission = "portage.staff")
    fun toggleNotifications(player: Player, toggled: Boolean) {
        val message: String = if (toggled) {
            "${ChatColor.YELLOW}You have toggled your notifications ${ChatColor.RED}on."
        } else {
            "${ChatColor.YELLOW}You have toggled your notifications ${ChatColor.RED}off."
        }

        StaffMetadataUtil.toggleMetadata(
            player,
            StaffMetadataUtil.ToggleableStaffMetadataType.NOTIFICATIONS,
            true,
            plugin
        )

        player.sendMessage(message)
    }
}