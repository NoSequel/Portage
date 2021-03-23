package io.github.nosequel.portage.core.punishments.redis

import com.google.gson.JsonObject
import io.github.nosequel.portage.core.PortageAPI
import io.github.nosequel.portage.core.database.DatabaseHandler
import io.github.nosequel.portage.core.database.redis.repository.DefaultRedisRepository
import io.github.nosequel.portage.core.punishments.PunishmentHandler

class RedisPunishmentRepository(portageAPI: PortageAPI, val handler: PunishmentHandler) : DefaultRedisRepository("punishments", portageAPI.handler.findOrThrow(DatabaseHandler::class.java).redis) {

    override fun handle(json: JsonObject) {
        RedisPunishmentType.valueOf(json.get("type").asString).handle(json, handler)
    }
}