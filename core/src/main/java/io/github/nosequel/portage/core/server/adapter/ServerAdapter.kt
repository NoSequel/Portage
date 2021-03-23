package io.github.nosequel.portage.core.server.adapter

interface ServerAdapter {

    /**
     * Broadcast a message
     */
    fun broadcastMessage(message: String)

    /**
     * Broadcast a message with a required permission to receive it
     */
    fun broadcastMessage(message: String, permission: String)

    /**
     * Execute a command on the server
     */
    fun executeCommand(command: String)

}