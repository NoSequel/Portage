package io.github.nosequel.portage.core.database.redis

import io.github.nosequel.portage.core.handler.Handler
import kotlinx.coroutines.runBlocking
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import java.util.function.Consumer

class RedisHandler (private val hostname: String, private val port: Int) : Handler {

    private var password: String? = null

    var pool: JedisPool? = null

    /**
     * Constructor to make a new [RedisHandler] instance with authentication fields
     * Call this when you have a database with authentication enabled
     */
    constructor(hostname: String, port: Int, password: String) : this(hostname, port) {
        this.password = password
    }

    init {
        this.pool = JedisPool(this.hostname, this.port)
    }

    /**
     * Execute a jedis command
     */
    fun executeCommand(consumer: Consumer<Jedis>) {
        Thread {
            val jedis = this.pool?.resource

            if (jedis != null) {
                if (password != null && password?.isNotEmpty() == true) {
                    this.pool!!.resource.auth(password)
                }

                consumer.accept(jedis)
                jedis.close()
            }
        }.start()
    }
}