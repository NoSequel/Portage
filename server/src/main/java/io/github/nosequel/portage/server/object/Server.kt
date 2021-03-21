package io.github.nosequel.portage.server.`object`

import io.github.nosequel.portage.server.`object`.redis.RedisServerType

class Server(val name: String, private val handler: ServerHandler) {

    /**
     * Send a message in the server
     */
    fun sendMessage(message: String, permission: String) {
        this.handler.redis.publish(RedisServerType.MESSAGE.toJson(this).also {
            it.addProperty("message", message)
            it.addProperty("permission", permission)
        })
    }
}