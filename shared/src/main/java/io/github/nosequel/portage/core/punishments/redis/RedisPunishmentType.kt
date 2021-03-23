package io.github.nosequel.portage.core.punishments.redis

import com.google.gson.JsonObject
import io.github.nosequel.portage.core.punishments.Punishment
import io.github.nosequel.portage.core.punishments.PunishmentHandler
import java.util.Optional
import java.util.UUID

enum class RedisPunishmentType {

    ADDED {
        override fun handle(json: JsonObject, handler: PunishmentHandler) {
            val uuid: UUID? = UUID.fromString(json.get("uuid").asString)

            if (handler.stream().noneMatch { it.uuid == uuid }) {
                val punishment = handler.repository.retrieveAsync(uuid.toString())

                if (!punishment.isPresent) {
                    println("Unable to load punishment with UUID ${uuid.toString()} from database.")
                } else {
                    handler.registerPunishment(punishment.get())
                }
            }
        }

        override fun toJson(punishment: Punishment): JsonObject {
            return JsonObject().also {
                it.addProperty("type", name)
                it.addProperty("uuid", punishment.uuid.toString())
            }
        }
    },

    ACTIVITY {
        override fun handle(json: JsonObject, handler: PunishmentHandler) {
            val uuid: UUID? = UUID.fromString(json.get("uuid").asString)

            if (uuid != null) {
                val punishment: Optional<Punishment> = handler.stream()
                    .filter { it.uuid == uuid }
                    .findFirst()

                if (punishment.isPresent) {
                    if (json.get("expired").asBoolean) {
                        handler.expirePunishment(punishment.get(), "Synchronized")
                    } else {
                        punishment.get().expired = false
                    }
                }
            }
        }

        override fun toJson(punishment: Punishment): JsonObject {
            return JsonObject().also {
                it.addProperty("type", name)
                it.addProperty("uuid", punishment.uuid.toString())
                it.addProperty("expired", punishment.expired)
            }
        }
    };

    abstract fun handle(json: JsonObject, handler: PunishmentHandler)
    abstract fun toJson(punishment: Punishment): JsonObject
}