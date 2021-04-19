package io.github.nosequel.portage.bukkit.grant

import io.github.nosequel.portage.bukkit.expirable.Duration
import io.github.nosequel.portage.bukkit.grant.menu.GrantMenu
import io.github.nosequel.portage.bukkit.grant.menu.GrantsMenu
import io.github.nosequel.portage.bukkit.util.UUIDFetcher
import io.github.nosequel.portage.bukkit.util.command.annotation.Command
import io.github.nosequel.portage.bukkit.util.command.annotation.Subcommand
import io.github.nosequel.portage.core.grant.Grant
import io.github.nosequel.portage.core.grant.GrantHandler
import io.github.nosequel.portage.core.handler.HandlerManager
import io.github.nosequel.portage.core.rank.Rank
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.UUID

class GrantCommand {

    private val grantHandler = HandlerManager.findOrThrow(GrantHandler::class.java)

    @Command(label = "grant", permission = "portage.grant")
    fun grant(player: Player, name: String) {
        val target: OfflinePlayer = UUIDFetcher.getOfflinePlayer(name)

        if (target.firstPlayed > 0) {
            GrantMenu(player, target.uniqueId).updateMenu()
        } else {
            player.sendMessage("${ChatColor.RED}That player has never played before")
        }
    }

    @Subcommand(label = "console", parentLabel = "grant", permission = "portage.grant")
    fun consoleGrant(sender: CommandSender, name: String, rank: Rank, duration: Duration, reason: String) {
        val target: OfflinePlayer = UUIDFetcher.getOfflinePlayer(name)

        if (target.firstPlayed > 0) {
            val executor = if (sender is Player) sender.uniqueId else UUID.randomUUID()
            val grant = Grant(target.uniqueId, rank.name, UUID.randomUUID(), reason, executor, duration.duration)

            this.grantHandler.registerGrant(grant)

            sender.sendMessage("${ChatColor.YELLOW}You have granted the ${grant.findRank().getDisplayName()}${ChatColor.YELLOW} rank to ${target.name}")

            if(target.isOnline) {
                target.player.sendMessage("${ChatColor.YELLOW}You have received the ${
                    grant.findRank().getDisplayName()
                }${ChatColor.YELLOW} rank.")
            }
        } else {
            sender.sendMessage("${ChatColor.RED}That player has never played before")
        }
    }

    @Command(label = "grants", permission = "portage.grants")
    fun grants(player: Player, name: String) {
        val target: OfflinePlayer = UUIDFetcher.getOfflinePlayer(name)

        if (target.firstPlayed > 0) {
            GrantsMenu(player, target.uniqueId).updateMenu()
        } else {
            player.sendMessage("${ChatColor.RED}That player has never played before")
        }
    }
}