package io.github.nosequel.portage.core

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.LongSerializationPolicy
import io.github.nosequel.portage.core.database.DatabaseHandler
import io.github.nosequel.portage.core.database.mongo.MongoHandler
import io.github.nosequel.portage.core.database.redis.RedisHandler
import io.github.nosequel.portage.core.grant.GrantHandler
import io.github.nosequel.portage.core.grant.GrantRepository
import io.github.nosequel.portage.core.handler.HandlerManager
import io.github.nosequel.portage.core.profile.ProfileHandler
import io.github.nosequel.portage.core.profile.ProfileRepository
import io.github.nosequel.portage.core.rank.RankHandler
import io.github.nosequel.portage.core.rank.RankRepository

class PortageAPI(val handler: HandlerManager, databaseHandler: DatabaseHandler) {

    val gson: Gson = GsonBuilder()
        .setLongSerializationPolicy(LongSerializationPolicy.STRING).setPrettyPrinting().create()

    init {
        this.handler.register(databaseHandler)

        this.handler.register(RankHandler(RankRepository(this)))
        this.handler.register(ProfileHandler(ProfileRepository(this)))
        this.handler.register(GrantHandler(GrantRepository(this)))

        this.handler.stream().forEach {
            it.enable()
        }
    }

    fun disable() {
        this.handler.stream().forEach {
            it.disable()
        }
    }
}