package io.github.nosequel.portage.core.grant.redis

import com.google.gson.JsonObject
import io.github.nosequel.portage.core.grant.Grant
import io.github.nosequel.portage.core.grant.GrantHandler
import java.util.Optional
import java.util.UUID

enum class RedisGrantType {

    ADDED {
        override fun handle(json: JsonObject, handler: GrantHandler) {
            val uuid: UUID? = UUID.fromString(json.get("uuid").asString)

            if (handler.stream().noneMatch { it.uuid == uuid }) {
                val grant = handler.repository.retrieveAsync(uuid.toString())

                if (!grant.isPresent) {
                    println("Unable to load grant with UUID ${uuid.toString()} from database.")
                } else {
                    handler.repository.cache.add(grant.get())
                }
            }
        }
    },

    ACTIVITY {
        override fun handle(json: JsonObject, handler: GrantHandler) {
            val uuid: UUID? = UUID.fromString(json.get("uuid").asString)

            if (uuid != null) {
                val grant: Optional<Grant> = handler.stream()
                    .filter { it.uuid == uuid }
                    .findFirst()

                if (grant.isPresent) {
                    grant.get().expired = json.get("expired").asBoolean
                }
            }
        }
    };

    abstract fun handle(json: JsonObject, handler: GrantHandler)

}