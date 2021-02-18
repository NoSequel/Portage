package io.github.nosequel.portage.core.database

import io.github.nosequel.portage.core.database.mongo.MongoHandler
import io.github.nosequel.portage.core.handler.Handler

class DatabaseHandler(val mongo: MongoHandler) : Handler