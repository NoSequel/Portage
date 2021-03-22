package io.github.nosequel.portage.server.session

import io.github.nosequel.portage.bukkit.util.ColorUtils
import io.github.nosequel.portage.core.grant.GrantHandler
import io.github.nosequel.portage.core.handler.HandlerManager
import io.github.nosequel.portage.server.`object`.Server
import io.github.nosequel.portage.server.session.task.SessionChecker
import java.util.UUID
import java.util.function.Consumer

class Session(val uuid: UUID, val name: String, var server: Server) {

    var lastLogout: Long = 0L
    var lastLogin: Long = 0L
    var lastActivity: SessionActivity = SessionActivity.JOIN

    /**
     * Logout the session
     */
    fun logout(logoutCallback: Consumer<Session>) {
        this.lastLogout = System.currentTimeMillis()
        this.lastActivity = SessionActivity.LEFT

        SessionChecker(this, logoutCallback)
    }

    /**
     * Login the session
     */
    fun login(server: Server) {
        this.lastLogin = System.currentTimeMillis()
        this.lastActivity = SessionActivity.JOIN
        this.server = server
    }

    /**
     * Get the display name of a session
     */
    fun getDisplayName(): String {
        return "${
            ColorUtils.getRankColor(HandlerManager.instance.findOrThrow(GrantHandler::class.java).findGrant(uuid)
                .findRank())
        }$name"
    }
}