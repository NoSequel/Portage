package io.github.nosequel.portage.core.database.redis.repository

import com.google.gson.JsonObject

interface RedisRepository {

    /**
     * Get the channel
     */
    fun getChannel(): String

    /**
     * Publish a [JsonObject]
     */
    fun publish(json: JsonObject)

    /**
     * Handle an incoming [JsonObject]
     */
    fun handle(json: JsonObject)

}