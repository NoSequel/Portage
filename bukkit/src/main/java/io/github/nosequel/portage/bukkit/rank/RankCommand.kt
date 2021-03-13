package io.github.nosequel.portage.bukkit.rank

import io.github.nosequel.portage.bukkit.rank.menu.RankMenu
import io.github.nosequel.portage.bukkit.util.command.annotation.Command
import io.github.nosequel.portage.bukkit.util.command.annotation.Subcommand
import io.github.nosequel.portage.core.PortageAPI
import io.github.nosequel.portage.core.handler.HandlerManager
import io.github.nosequel.portage.core.rank.Rank
import io.github.nosequel.portage.core.rank.RankHandler
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class RankCommand(handler: HandlerManager) {

    private val rankHandler: RankHandler = handler.findOrThrow(RankHandler::class.java)

    @Command(label = "rank", permission = "portage.rank")
    fun rank(player: Player) {
        player.sendMessage(arrayOf(
            "${ChatColor.RED}/rank create <name>",
            "${ChatColor.RED}/rank delete <name>",
            "${ChatColor.RED}/rank editor",
            "${ChatColor.RED}/rank list"
        ))
    }

    @Subcommand(label = "create", parentLabel = "rank", permission = "portage.rank.create")
    fun create(player: Player, rank: String) {
        if (rankHandler.find(rank).isPresent) {
            player.sendMessage("${ChatColor.RED}A rank with the name ${ChatColor.DARK_RED}$rank${ChatColor.RED} already exists.")
            return
        }

        Rank(rank).also {
            this.rankHandler.register(it)

            player.sendMessage("${ChatColor.YELLOW}You have created the ${it.getDisplayName()}${ChatColor.YELLOW} rank.")
        }
    }

    @Subcommand(label = "delete", parentLabel = "rank", permission = "portage.rank.delete")
    fun delete(player: Player, rank: Rank) {
        this.rankHandler.delete(rank)
        player.sendMessage("${ChatColor.YELLOW}You have deleted the ${rank.getDisplayName()}${ChatColor.YELLOW} rank.")
    }

    @Subcommand(label = "editor", parentLabel = "rank", permission = "portage.rank.editor")
    fun editor(player: Player) {
        RankMenu(player).updateMenu()
    }

    @Subcommand(label = "list", parentLabel = "rank", permission = "portage.rank.list")
    fun list(player: Player) {
        this.rankHandler.stream()
            .map { "${ChatColor.GRAY}${it.getDisplayName()}${ChatColor.GRAY} | ${ChatColor.WHITE}Weight: ${ChatColor.RED}${it.weight}" }
            .forEach { player.sendMessage(it) }
    }
}