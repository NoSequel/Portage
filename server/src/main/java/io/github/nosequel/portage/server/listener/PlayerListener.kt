package io.github.nosequel.portage.server.listener

import io.github.nosequel.portage.core.handler.HandlerManager
import io.github.nosequel.portage.server.`object`.ServerHandler
import io.github.nosequel.portage.server.`object`.redis.RedisServerType
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerListener(handlerManager: HandlerManager) : Listener {

    private val serverHandler = handlerManager.findOrThrow(ServerHandler::class.java)

    @EventHandler(priority = EventPriority.MONITOR)
    fun join(event: PlayerJoinEvent) {
        val player = event.player

        if(player.hasPermission("portage.staff")) {
            player.sendMessage("${ChatColor.YELLOW}Toggle staff notifications by typing '/notifications true'.")

            serverHandler.redis.publish(RedisServerType.JOIN.toJson(serverHandler.localServer).also {
                it.addProperty("uuid", player.uniqueId.toString())
                it.addProperty("name", player.name)
            })
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun quit(event: PlayerQuitEvent) {
        val player = event.player

        if(player.hasPermission("portage.staff")) {
            serverHandler.redis.publish(RedisServerType.LOGOUT.toJson(serverHandler.localServer).also {
                it.addProperty("uuid", player.uniqueId.toString())
                it.addProperty("name", player.name)
            })
        }
    }
}