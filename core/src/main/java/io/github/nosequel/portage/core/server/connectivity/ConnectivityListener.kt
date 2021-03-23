package io.github.nosequel.portage.core.server.connectivity

import io.github.nosequel.portage.core.server.`object`.Server
import io.github.nosequel.portage.core.server.session.Session

interface ConnectivityListener {

    /**
     * Handle a player connecting to the network
     */
    fun handleConnect(session: Session, server: Server)

    /**
     * Handle a player disconnecting from the network
     */
    fun handleDisconnect(session: Session, server: Server)

    /**
     * Handle a player switching servers on the network
     */
    fun handleSwitch(session: Session, serverTo: Server, serverFrom: Server)

}