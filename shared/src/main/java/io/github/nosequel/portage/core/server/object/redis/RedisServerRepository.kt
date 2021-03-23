package io.github.nosequel.portage.core.server.`object`.redis

import com.google.gson.JsonObject
import io.github.nosequel.portage.core.database.redis.RedisHandler
import io.github.nosequel.portage.core.database.redis.repository.DefaultRedisRepository
import io.github.nosequel.portage.core.server.`object`.ServerHandler

class RedisServerRepository(redisHandler: RedisHandler, private val serverHandler: ServerHandler) : DefaultRedisRepository("server", redisHandler) {

    override fun handle(json: JsonObject) {
        RedisServerType.valueOf(json.get("type").asString).handle(json, serverHandler)
    }
}