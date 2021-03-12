package io.github.nosequel.portage.core.database.redis.repository.async

import com.google.gson.JsonObject
import io.github.nosequel.portage.core.database.redis.repository.RedisRepository
import kotlinx.coroutines.runBlocking

interface AsyncRedisRepository : RedisRepository {

    /**
     * Call the publish inside of a new coroutine block
     */
    fun publishAsync(json: JsonObject) {
        runBlocking {
            publish(json)
        }
    }
}