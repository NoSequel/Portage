package io.github.nosequel.portage.server.connectivity

import io.github.nosequel.portage.bukkit.util.ColorUtils
import io.github.nosequel.portage.core.grant.GrantHandler
import io.github.nosequel.portage.core.handler.HandlerManager
import io.github.nosequel.portage.core.server.`object`.Server
import io.github.nosequel.portage.core.server.connectivity.ConnectivityListener
import io.github.nosequel.portage.core.server.session.Session
import io.github.nosequel.portage.server.util.StaffMetadataUtil
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class BukkitConnectivityListener : ConnectivityListener {

    /**
     * Handle a player connecting to the network
     */
    override fun handleConnect(session: Session,  server: Server) {
        this.broadcastStaffMessage("${ChatColor.BLUE}[Staff] ${ChatColor.AQUA}${this.getDisplayName(session)} ${ChatColor.GREEN}joined ${ChatColor.AQUA}the network (from ${server.name})")
    }

    /**
     * Handle a player disconnecting from the network
     */
    override fun handleDisconnect(session: Session, server: Server) {
        this.broadcastStaffMessage("${ChatColor.BLUE}[Staff] ${ChatColor.AQUA}${this.getDisplayName(session)} ${ChatColor.RED}left ${ChatColor.AQUA}the network (from ${server.name})")
    }

    /**
     * Handle a player switching servers on the network
     */
    override fun handleSwitch(session: Session, serverTo: Server, serverFrom: Server) {
        this.broadcastStaffMessage("${ChatColor.BLUE}[Staff] ${ChatColor.AQUA}${this.getDisplayName(session)}${ChatColor.GREEN} joined ${ChatColor.AQUA}${serverTo.name} (from ${serverFrom.name})")
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
     * Get the display name of the session
     */
    private fun getDisplayName(session: Session): String {
        return "${
            ColorUtils.getRankColor(HandlerManager.findOrThrow(GrantHandler::class.java)
                .findGrant(session.uuid).findRank())
        }${session.name}"
    }


    /**
     * Check if a player has notifications enabled
     */
    private fun hasNotifications(player: Player): Boolean {
        return StaffMetadataUtil.hasMetadata(player, StaffMetadataUtil.StaffMetadataType.CONNECTIVITY)
    }
}