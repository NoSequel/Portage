package io.github.nosequel.portage.server.`object`

import io.github.nosequel.portage.core.database.DatabaseHandler
import io.github.nosequel.portage.core.handler.HandlerManager
import io.github.nosequel.portage.server.`object`.redis.RedisServerRepository

class ServerHandler {

    private val servers: MutableList<Server> = mutableListOf()
    val redis: RedisServerRepository =
        RedisServerRepository(HandlerManager.instance.findOrThrow(DatabaseHandler::class.java).redis, this)

}