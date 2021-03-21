package io.github.nosequel.portage.server

import io.github.nosequel.portage.bukkit.util.command.CommandHandler
import io.github.nosequel.portage.core.handler.HandlerManager
import io.github.nosequel.portage.server.`object`.ServerHandler
import io.github.nosequel.portage.server.command.ServerDumpCommand
import io.github.nosequel.portage.server.command.StaffChatCommand
import io.github.nosequel.portage.server.listener.PlayerListener
import io.github.nosequel.portage.server.session.SessionHandler
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class ServerPlugin : JavaPlugin() {

    override fun onEnable() {
        this.saveDefaultConfig()

        val handler: HandlerManager = HandlerManager.instance
        val sessionHandler = SessionHandler()
        val serverHandler = ServerHandler(this.config.getString("server.id"), sessionHandler)

        handler.register(sessionHandler)
        handler.register(serverHandler)

        handler.findOrThrow(CommandHandler::class.java)
            .registerCommand(ServerDumpCommand(serverHandler), StaffChatCommand(serverHandler))

        Bukkit.getPluginManager().registerEvents(PlayerListener(handler), this)
    }
}