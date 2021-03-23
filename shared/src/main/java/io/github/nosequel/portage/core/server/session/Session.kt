package io.github.nosequel.portage.core.server.session

import io.github.nosequel.portage.core.server.`object`.Server
import io.github.nosequel.portage.core.server.session.task.SessionChecker
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
}