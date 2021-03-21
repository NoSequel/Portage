package io.github.nosequel.portage.server.session

import io.github.nosequel.portage.server.`object`.Server
import java.util.UUID
import java.util.function.Consumer

class Session(val uuid: UUID, val name: String, var server: Server) {

    var lastLogout: Long = 0L
    var lastLogin: Long = 0L

    /**
     * Logout the session
     */
    fun logout(logoutCallback: Consumer<Session>) {
        this.lastLogout = System.currentTimeMillis()
    }

    /**
     * Login the session
     */
    fun login(server: Server) {
        this.lastLogin = System.currentTimeMillis()
        this.server = server
    }
}