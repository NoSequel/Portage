package io.github.nosequel.portage.server.command

import io.github.nosequel.portage.bukkit.util.command.annotation.Command
import io.github.nosequel.portage.server.`object`.ServerHandler
import org.apache.commons.lang.StringUtils
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

class StaffChatCommand(val handler: ServerHandler) {

    @Command(label = "staffchat", aliases = ["sc", "staffc", "schat"], permission = "portage.staff")
    fun staffChat(sender: CommandSender, message: Array<String>) {
        val chat =
            "${ChatColor.BLUE}[Staff] ${ChatColor.GRAY}(${handler.localServer.name}) ${ChatColor.DARK_AQUA}${sender.name}${ChatColor.AQUA}: ${
                StringUtils.join(message,
                    " ")
            }"

        this.handler.stream().forEach { it.sendMessage(chat, "portage.staff") }
    }
}