package io.github.nosequel.portage.server

import io.github.nosequel.portage.core.handler.HandlerManager
import io.github.nosequel.portage.server.`object`.ServerHandler
import io.github.nosequel.portage.server.listener.PlayerListener
import io.github.nosequel.portage.server.session.SessionHandler
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class ServerPlugin : JavaPlugin() {

    override fun onEnable() {
        this.saveDefaultConfig()

        val handler: HandlerManager = HandlerManager.instance
        val sessionHandler = SessionHandler()

        handler.register(sessionHandler)
        handler.register(ServerHandler(this.config.getString("server.id"), sessionHandler))

        Bukkit.getPluginManager().registerEvents(PlayerListener(handler), this)
    }
}