package io.github.nosequel.portage.core.database.redis.repository

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import io.github.nosequel.portage.core.database.redis.RedisHandler
import io.github.nosequel.portage.core.database.redis.repository.async.AsyncRedisRepository
import redis.clients.jedis.JedisPubSub

abstract class DefaultRedisRepository(private val channel: String, private val redisHandler: RedisHandler) : AsyncRedisRepository {

    private val parser: JsonParser = JsonParser()

    init {
        redisHandler.executeCommand {
            it.subscribe(object : JedisPubSub() {
                override fun onMessage(channel: String, message: String) {
                    if (channel == getChannel()) {
                        handle(parser.parse(message).asJsonObject)
                    }
                }
            }, channel)
        }
    }

    override fun getChannel(): String {
        return channel
    }

    override fun publish(json: JsonObject) {
        this.redisHandler.executeCommand {
            it.publish(this.channel, json.toString())
        }
    }
}