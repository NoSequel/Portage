package io.github.nosequel.portage.bukkit.command

import io.github.nosequel.portage.bukkit.util.command.annotation.Command
import io.github.nosequel.portage.core.grant.GrantHandler
import io.github.nosequel.portage.core.handler.HandlerManager
import io.github.nosequel.portage.core.rank.RankHandler
import io.github.nosequel.portage.core.rank.metadata.Metadata
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import java.util.stream.Collectors

class ListCommand {

    private val rankHandler: RankHandler = HandlerManager.instance.findOrThrow(RankHandler::class.java)
    private val grantHandler: GrantHandler = HandlerManager.instance.findOrThrow(GrantHandler::class.java)

    @Command(label = "list")
    fun list(sender: CommandSender) {
        this.send(sender, sender.hasPermission("portage.staff"))
    }

    private fun send(sender: CommandSender, hidden: Boolean) {
        sender.sendMessage(this.rankHandler.stream()
            .filter { !it.hasMetadata(Metadata.HIDDEN) || hidden }
            .map { "${if (it.hasMetadata(Metadata.HIDDEN)) "${ChatColor.GRAY}*" else ""}${it.getDisplayName()}" }
            .collect(Collectors.joining("${ChatColor.WHITE}, "))
        )

        sender.sendMessage("${ChatColor.WHITE}(${Bukkit.getOnlinePlayers().size}/${Bukkit.getMaxPlayers()}) ${
            Bukkit.getOnlinePlayers().stream()
                .sorted(Comparator.comparingInt { -this.grantHandler.findGrant(it.uniqueId).findRank().weight })
                .map { grantHandler.findGrant(it.uniqueId).findRank().color + it.name + ChatColor.WHITE }
                .toArray { arrayOfNulls<String>(Bukkit.getOnlinePlayers().size) }.asList()
        }")
    }
}