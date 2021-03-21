package io.github.nosequel.portage.server.`object`.redis

import com.google.common.base.Preconditions
import com.google.gson.JsonObject

import io.github.nosequel.portage.server.`object`.Server
import io.github.nosequel.portage.server.`object`.ServerHandler
import io.github.nosequel.portage.server.session.Session
import io.github.nosequel.portage.server.session.SessionActivity
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import java.util.UUID

enum class RedisServerType {

    STARTUP {
        /**
         * Handle an incoming message
         */
        override fun handle(json: JsonObject, handler: ServerHandler) {
            Preconditions.checkArgument(json.has("server"), "No server field found in provided JsonObject")

            handler.find(json.get("server").asString)
                .orElseGet { handler.register(json.get("server").asString) }
        }

        /**
         * Serialize a server to a new json object
         */
        override fun toJson(server: Server): JsonObject {
            return JsonObject().also {
                it.addProperty("server", server.name)
                it.addProperty("type", STARTUP.name)
            }
        }
    },

    MESSAGE {
        /**
         * Handle an incoming message
         */
        override fun handle(json: JsonObject, handler: ServerHandler) {
            Preconditions.checkArgument(json.has("server"), "No server field found in provided JsonObject")
            Preconditions.checkArgument(json.has("message"), "No message field found in provided JsonObject")

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
            val server = handler.find(json.get("server").asString)
                .orElseGet { handler.register(json.get("server").asString) }

            val session = this.findSession(json, handler)
            val message: String = if (session.lastActivity == SessionActivity.LEFT) {
                "${ChatColor.BLUE}[Staff] ${ChatColor.AQUA}${session.name}${ChatColor.GREEN} joined ${ChatColor.AQUA}${session.server.name} (from ${server.name})"
            } else {
                "${ChatColor.BLUE}[Staff] ${ChatColor.AQUA}${session.name} ${ChatColor.GREEN}joined ${ChatColor.AQUA}the network (${server.name})"
            }

            Bukkit.getOnlinePlayers().stream()
                .filter { it.hasPermission("portage.staff") }
                .forEach { it.sendMessage(message) }

            session.login(server)
        }

        /**
         * Serialize a server to a new json object
         */
        override fun toJson(server: Server): JsonObject {
            return JsonObject().also {
                it.addProperty("server", server.name)
                it.addProperty("type", JOIN.name)
            }
        }
    },

    LOGOUT {
        /**
         * Handle an incoming message
         */
        override fun handle(json: JsonObject, handler: ServerHandler) {
            findSession(json, handler).logout { session ->
                Bukkit.getOnlinePlayers().stream()
                    .filter { it.hasPermission("portage.staff") }
                    .forEach { it.sendMessage("${ChatColor.BLUE}[Staff] ${ChatColor.AQUA}${session.name} ${ChatColor.RED}left ${ChatColor.AQUA}the network (from ${session.server.name})") }
            }
        }

        /**
         * Serialize a server to a new json object
         */
        override fun toJson(server: Server): JsonObject {
            return JsonObject().also {
                it.addProperty("server", server.name)
                it.addProperty("type", LOGOUT.name)
            }
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

    /**
     * Find a session
     */
    fun findSession(json: JsonObject, handler: ServerHandler): Session {
        Preconditions.checkArgument(json.has("server"), "No server field found in provided JsonObject")
        Preconditions.checkArgument(json.has("uuid"), "No uuid field found in provided JsonObject")
        Preconditions.checkArgument(json.has("name"), "No name field found in provided JsonObject")

        val uuid = UUID.fromString(json.get("uuid").asString)
        val name = json.get("name").asString
        val server = handler.find(json.get("server").asString)
            .orElseGet { handler.register(json.get("server").asString) }

        return handler.sessionHandler.find(uuid).orElseGet {
            handler.sessionHandler.register(Session(uuid, name, server))
        }
    }
}