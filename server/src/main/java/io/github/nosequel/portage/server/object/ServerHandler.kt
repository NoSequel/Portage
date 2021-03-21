package io.github.nosequel.portage.server.`object`

import io.github.nosequel.portage.core.database.DatabaseHandler
import io.github.nosequel.portage.core.handler.Handler
import io.github.nosequel.portage.core.handler.HandlerManager
import io.github.nosequel.portage.server.`object`.redis.RedisServerRepository
import io.github.nosequel.portage.server.`object`.redis.RedisServerType
import io.github.nosequel.portage.server.session.SessionHandler
import java.util.Optional
import java.util.stream.Stream

class ServerHandler(serverName: String, val sessionHandler: SessionHandler) : Handler {

    private val servers: MutableList<Server> = mutableListOf()

    val localServer = this.register(serverName)
    val redis: RedisServerRepository =
        RedisServerRepository(HandlerManager.instance.findOrThrow(DatabaseHandler::class.java).redis, this)

    override fun enable() {
        this.redis.publish(RedisServerType.STARTUP.toJson(this.localServer))
    }

    /**
     * Register a new server to the server handler
     */
    fun register(server: Server): Server {
        return server.also {
            this.servers.add(it)
        }
    }

    /**
     * Register and make a server to the server handler
     */
    fun register(name: String): Server {
        return this.register(Server(name, this))
    }

    /**
     * Open a new stream to the servers list
     */
    fun stream(): Stream<Server> {
        return this.servers.stream()
    }

    /**
     * Find a server by a name
     */
    fun find(name: String): Optional<Server> {
        return this.stream()
            .filter { it.name == name }
            .findFirst()
    }
}