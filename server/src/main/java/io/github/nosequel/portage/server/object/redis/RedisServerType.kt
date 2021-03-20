package io.github.nosequel.portage.server.`object`.redis

import com.google.gson.JsonObject

import io.github.nosequel.portage.server.`object`.Server
import io.github.nosequel.portage.server.`object`.ServerHandler
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import java.lang.IllegalArgumentException
import kotlin.IllegalArgumentException

enum class RedisServerType {

    STARTUP {
        /**
         * Handle an incoming message
         */
        override fun handle(json: JsonObject, handler: ServerHandler) {
            TODO("Not yet implemented")
        }

        /**
         * Serialize a server to a new json object
         */
        override fun toJson(server: Server): JsonObject {
            TODO("Not yet implemented")
        }
    },

    MESSAGE {
        /**
         * Handle an incoming message
         */
        override fun handle(json: JsonObject, handler: ServerHandler) {
            if (!json.has("message")) {
                throw IllegalArgumentException("JsonObject provided in RedisServerType.MESSAGE doesnt contain message field")
            }

            if (handler.localServer.name == json.get("server").asString) {
                val message = ChatColor.translateAlternateColorCodes('&', json.get("message").asString)
                val permission = if (json.has("permission")) json.get("permission").asString else ""

                if (permission == "") {
                    Bukkit.broadcastMessage(message)
                } else {
                    Bukkit.getOnlinePlayers().stream()
                        .filter { it.hasPermission(permission) }
                        .forEach { it.sendMessage(message) }
                }
            }
        }

        /**
         * Serialize a server to a new json object
         */
        override fun toJson(server: Server): JsonObject {
            return JsonObject().also {
                it.addProperty("server", server.name)
                it.addProperty("type", MESSAGE.name)
            }
        }
    },

    JOIN {
        /**
         * Handle an incoming message
         */
        override fun handle(json: JsonObject, handler: ServerHandler) {
            TODO("Not yet implemented")
        }

        /**
         * Serialize a server to a new json object
         */
        override fun toJson(server: Server): JsonObject {
            TODO("Not yet implemented")
        }
    };

    /**
     * Handle an incoming message
     */
    abstract fun handle(json: JsonObject, handler: ServerHandler)

    /**
     * Serialize a server to a new json object
     */
    abstract fun toJson(server: Server): JsonObject

}