package io.github.nosequel.portage.server.command

import io.github.nosequel.portage.bukkit.util.command.annotation.Command
import io.github.nosequel.portage.core.server.`object`.ServerHandler
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import java.util.stream.Collectors

class ServerDumpCommand(private val serverHandler: ServerHandler) {

    @Command(label="serverdump", permission="portage.staff")
    fun serverDump(sender: CommandSender) {
        sender.sendMessage(this.serverHandler.stream()
            .map { "${ChatColor.GREEN}${it.name}" }
            .collect(Collectors.joining("\n")))
    }
}