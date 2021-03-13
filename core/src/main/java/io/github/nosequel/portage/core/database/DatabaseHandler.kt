package io.github.nosequel.portage.core.database

import io.github.nosequel.portage.core.PortageAPI
import io.github.nosequel.portage.core.database.mongo.MongoHandler
import io.github.nosequel.portage.core.database.redis.RedisHandler
import io.github.nosequel.portage.core.handler.Handler
import io.github.nosequel.portage.core.handler.HandlerManager

class DatabaseHandler(handler: HandlerManager, val mongo: MongoHandler, val redis: RedisHandler) : Handler {

    init {
        handler.register(mongo)
        handler.register(redis)
    }

}