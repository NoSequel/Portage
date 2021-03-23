package io.github.nosequel.portage.server.command

import io.github.nosequel.portage.bukkit.util.command.annotation.Command
import io.github.nosequel.portage.core.server.`object`.ServerHandler
import org.apache.commons.lang.StringUtils
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

class RemoteExecuteCommand(private val serverHandler: ServerHandler) {

    @Command(label = "remoteexecute", aliases = ["remote", "executeserver"], permission = "portage.admin")
    fun remoteExecute(sender: CommandSender, serverName: String, commandArray: Array<String>) {
        val command = StringUtils.join(commandArray, " ")

        if (serverName.equals("all", true)) {
            serverHandler.stream().forEach { it.executeCommand(command) }
            sender.sendMessage("${ChatColor.YELLOW}Executing command ${ChatColor.GREEN}$command ${ChatColor.YELLOW}on all servers")
        } else {
            val server = this.serverHandler.find(serverName)

            if (!server.isPresent) {
                sender.sendMessage("${ChatColor.RED}No server with name $serverName found, list all servers using '/serverdump'")
            } else {
                server.get().executeCommand(command)
                sender.sendMessage("${ChatColor.YELLOW}Executing command ${ChatColor.GREEN}$command ${ChatColor.YELLOW}on server ${ChatColor.GREEN}${server.get().name}")
            }
        }
    }
}