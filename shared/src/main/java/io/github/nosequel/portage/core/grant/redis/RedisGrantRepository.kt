package io.github.nosequel.portage.core.grant.redis

import com.google.gson.JsonObject
import io.github.nosequel.portage.core.PortageAPI
import io.github.nosequel.portage.core.database.DatabaseHandler
import io.github.nosequel.portage.core.database.redis.repository.DefaultRedisRepository
import io.github.nosequel.portage.core.grant.GrantHandler

class RedisGrantRepository(portageAPI: PortageAPI, val handler: GrantHandler) : DefaultRedisRepository("grants", portageAPI.handler.findOrThrow(DatabaseHandler::class.java).redis) {

    override fun handle(json: JsonObject) {
        RedisGrantType.valueOf(json.get("type").asString).handle(json, handler)
    }
}