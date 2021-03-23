package io.github.nosequel.portage.server.adapter

import io.github.nosequel.portage.core.server.adapter.ServerAdapter
import io.github.nosequel.portage.server.util.StaffMetadataUtil
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.util.stream.Stream

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
        this.getPlayers(permission).forEach { it.sendMessage(ChatColor.translateAlternateColorCodes('&', message)) }
    }

    /**
     * Send a staff chat message
     */
    override fun sendStaffChatMessage(message: String, permission: String) {
        this.getPlayers(permission)
            .filter { StaffMetadataUtil.hasMetadata(it, StaffMetadataUtil.StaffMetadataType.STAFF_CHAT) }
            .forEach { it.sendMessage(ChatColor.translateAlternateColorCodes('&', message)) }
    }

    /**
     * Execute a command on the server
     */
    override fun executeCommand(command: String) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command)
    }

    /**
     * Get all players by a permission
     */
    private fun getPlayers(permission: String): Stream<out Player> {
        return Bukkit.getOnlinePlayers().stream()
            .filter { it.hasPermission(permission) }
    }
}