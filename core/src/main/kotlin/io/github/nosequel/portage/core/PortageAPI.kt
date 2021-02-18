package io.github.nosequel.portage.core

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.LongSerializationPolicy
import io.github.nosequel.portage.core.database.DatabaseHandler
import io.github.nosequel.portage.core.database.mongo.MongoHandler
import io.github.nosequel.portage.core.handler.HandlerManager
import io.github.nosequel.portage.core.profile.ProfileHandler
import io.github.nosequel.portage.core.profile.ProfileRepository
import io.github.nosequel.portage.core.rank.RankHandler
import io.github.nosequel.portage.core.rank.RankRepository

class PortageAPI(val handler: HandlerManager = HandlerManager()) {

    val gson: Gson = GsonBuilder()
        .setLongSerializationPolicy(LongSerializationPolicy.STRING).setPrettyPrinting().create()

    companion object {
        lateinit var instance: PortageAPI
    }

    init {
        instance = this

        this.handler.register(DatabaseHandler(this, MongoHandler("127.0.0.1", 27017, "portage")))

        this.handler.register(RankHandler(RankRepository(this)))
        this.handler.register(ProfileHandler(ProfileRepository(this)))

        this.handler.stream().forEach { it.enable() }
    }
}