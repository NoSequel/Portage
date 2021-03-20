package io.github.nosequel.portage.server.`object`.redis

import com.google.gson.JsonObject

import io.github.nosequel.portage.server.`object`.Server
import io.github.nosequel.portage.server.`object`.ServerHandler

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
            TODO("Not yet implemented")
        }

        /**
         * Serialize a server to a new json object
         */
        override fun toJson(server: Server): JsonObject {
            TODO("Not yet implemented")
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