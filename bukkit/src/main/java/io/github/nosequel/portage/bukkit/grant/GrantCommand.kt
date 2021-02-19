package io.github.nosequel.portage.bukkit.grant

import io.github.nosequel.portage.bukkit.grant.menu.GrantMenu
import io.github.nosequel.portage.bukkit.grant.menu.GrantsMenu
import io.github.nosequel.portage.bukkit.util.command.annotation.Command
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

class GrantCommand {

    @Command(label="grant", permission = "portage.grant")
    fun grant(player: Player, name: String) {
        val target: OfflinePlayer = this.getOfflinePlayer(name)

        if (target.firstPlayed > 0) {
            GrantMenu(player, target.uniqueId).updateMenu()
        } else {
            player.sendMessage("${ChatColor.RED}That player has never played before")
        }
    }

    @Command(label="grants", permission="portage.grants")
    fun grants(player: Player, name: String) {
        val target: OfflinePlayer = this.getOfflinePlayer(name)

        if (target.firstPlayed > 0) {
            GrantsMenu(player, target.uniqueId).updateMenu()
        } else {
            player.sendMessage("${ChatColor.RED}That player has never played before")
        }
    }

    /**
     * Get the [OfflinePlayer] object by a [String]
     */
    private fun getOfflinePlayer(name: String) : OfflinePlayer = runBlocking(Dispatchers.IO) {
        return@runBlocking Bukkit.getOfflinePlayer(name)
    }
}