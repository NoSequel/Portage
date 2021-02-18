package io.github.nosequel.portage.core.database

import io.github.nosequel.portage.core.PortageAPI
import io.github.nosequel.portage.core.database.mongo.MongoHandler
import io.github.nosequel.portage.core.handler.Handler

class DatabaseHandler(portageAPI: PortageAPI, val mongo: MongoHandler) : Handler {

    init {
        portageAPI.handler.register(mongo)
    }

}