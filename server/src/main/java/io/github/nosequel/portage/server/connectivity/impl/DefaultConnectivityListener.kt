package io.github.nosequel.portage.server.connectivity.impl

import io.github.nosequel.portage.server.`object`.Server
import io.github.nosequel.portage.server.connectivity.ConnectivityListener
import io.github.nosequel.portage.server.session.Session
import io.github.nosequel.portage.server.util.StaffMetadataUtil
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class DefaultConnectivityListener : ConnectivityListener {

    /**
     * Handle a player connecting to the network
     */
    override fun handleConnect(session: Session,  server: Server) {
        this.broadcastStaffMessage("${ChatColor.BLUE}[Staff] ${ChatColor.AQUA}${session.name} ${ChatColor.GREEN}joined ${ChatColor.AQUA}the network (from ${server.name})")
    }

    /**
     * Handle a player disconnecting from the network
     */
    override fun handleDisconnect(session: Session, server: Server) {
        this.broadcastStaffMessage("${ChatColor.BLUE}[Staff] ${ChatColor.AQUA}${session.name} ${ChatColor.RED}left ${ChatColor.AQUA}the network (from ${server.name})")
    }

    /**
     * Handle a player switching servers on the network
     */
    override fun handleSwitch(session: Session, serverTo: Server, serverFrom: Server) {
        this.broadcastStaffMessage("${ChatColor.BLUE}[Staff] ${ChatColor.AQUA}${session.name}${ChatColor.GREEN} joined ${ChatColor.AQUA}${serverTo.name} (from ${serverFrom.name})")
    }

    /**
     * Broadcast a message inside of the local server
     */
    private fun broadcastStaffMessage(message: String) {
        Bukkit.getOnlinePlayers().stream()
            .filter { it.hasPermission("portage.staff") && hasNotifications(it) }
            .forEach { it.sendMessage(message) }
    }

    /**
     * Check if a player has notifications enabled
     */
    private fun hasNotifications(player: Player): Boolean {
        return StaffMetadataUtil.hasMetadata(player, StaffMetadataUtil.StaffMetadataType.CONNECTIVITY)
    }
}