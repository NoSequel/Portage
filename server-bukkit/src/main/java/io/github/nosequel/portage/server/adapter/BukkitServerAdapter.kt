package io.github.nosequel.portage.server.adapter

import io.github.nosequel.portage.core.server.adapter.ServerAdapter
import org.bukkit.Bukkit
import org.bukkit.ChatColor

class BukkitServerAdapter : ServerAdapter {

    /**
     * Broadcast a message
     */
    override fun broadcastMessage(message: String) {
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message))
    }

    /**
     * Broadcast a message with a required permission to receive it
     */
    override fun broadcastMessage(message: String, permission: String) {
        Bukkit.getOnlinePlayers().stream()
            .filter { it.hasPermission(permission) }
            .forEach { it.sendMessage(ChatColor.translateAlternateColorCodes('&', message)) }
    }

    /**
     * Execute a command on the server
     */
    override fun executeCommand(command: String) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command)
    }
}