package io.github.nosequel.portage.server.command

import io.github.nosequel.portage.bukkit.util.command.annotation.Command
import io.github.nosequel.portage.server.`object`.Server
import io.github.nosequel.portage.server.`object`.ServerHandler
import org.apache.commons.lang.StringUtils
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

class StaffChatCommand(val handler: ServerHandler) {

    @Command(label = "staffchat", aliases = ["sc", "staffc", "schat"], permission = "portage.staff")
    fun staffChat(sender: CommandSender, message: Array<String>) {
        this.handler.stream().forEach {
            it.sendMessage(
                StaffChatType.STAFF.getMessage(message, handler.localServer, sender),
                "portage.staff")
        }
    }

    @Command(label = "adminchat", aliases = ["ac", "adminc", "achat"], permission = "portage.admin")
    fun adminChat(sender: CommandSender, message: Array<String>) {
        this.handler.stream().forEach {
            it.sendMessage(
                StaffChatType.ADMIN.getMessage(message, handler.localServer, sender),
                "portage.admin")
        }
    }

    @Command(label = "developerchat", aliases = ["dc", "devchat", "dchat", "devc"], permission = "portage.developer")
    fun developerChat(sender: CommandSender, message: Array<String>) {
        this.handler.stream().forEach {
            it.sendMessage(
                StaffChatType.DEVELOPER.getMessage(message, handler.localServer, sender),
                "portage.developer")
        }
    }


    enum class StaffChatType {

        STAFF {
            /**
             * Get the formatted message by the type
             */
            override fun getMessage(message: Array<String>, server: Server, sender: CommandSender): String {
                return "${ChatColor.BLUE}[Staff] ${ChatColor.GRAY}(${server.name}) ${ChatColor.DARK_AQUA}${sender.name}${ChatColor.AQUA}: ${
                    StringUtils.join(message,
                        " ")
                }"
            }
        },
        ADMIN {
            /**
             * Get the formatted message by the type
             */
            override fun getMessage(message: Array<String>, server: Server, sender: CommandSender): String {
                return "${ChatColor.DARK_RED}[Admin] ${ChatColor.GRAY}(${server.name}) ${ChatColor.DARK_AQUA}${sender.name}${ChatColor.AQUA}: ${
                    StringUtils.join(message,
                        " ")
                }"
            }

        },
        DEVELOPER {
            /**
             * Get the formatted message by the type
             */
            override fun getMessage(message: Array<String>, server: Server, sender: CommandSender): String {
                return "${ChatColor.DARK_AQUA}[Developer] ${ChatColor.GRAY}(${server.name}) ${ChatColor.DARK_AQUA}${sender.name}${ChatColor.AQUA}: ${
                    StringUtils.join(message,
                        " ")
                }"
            }

        };

        /**
         * Get the formatted message by the type
         */
        abstract fun getMessage(message: Array<String>, server: Server, sender: CommandSender): String
    }
}