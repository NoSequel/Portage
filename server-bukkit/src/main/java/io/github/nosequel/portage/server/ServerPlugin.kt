package io.github.nosequel.portage.server

import io.github.nosequel.portage.bukkit.util.command.CommandHandler
import io.github.nosequel.portage.core.handler.HandlerManager
import io.github.nosequel.portage.core.server.`object`.ServerHandler
import io.github.nosequel.portage.core.server.adapter.ServerAdapterHandler
import io.github.nosequel.portage.core.server.connectivity.ConnectivityHandler
import io.github.nosequel.portage.server.command.RemoteExecuteCommand
import io.github.nosequel.portage.server.command.ServerDumpCommand
import io.github.nosequel.portage.server.command.StaffChatCommand
import io.github.nosequel.portage.server.command.metadata.MetadataCommand
import io.github.nosequel.portage.server.command.metadata.MetadataTypeAdapter
import io.github.nosequel.portage.core.server.session.SessionHandler
import io.github.nosequel.portage.server.adapter.BukkitServerAdapter
import io.github.nosequel.portage.server.connectivity.BukkitConnectivityListener
import io.github.nosequel.portage.server.listener.PlayerListener
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class ServerPlugin : JavaPlugin() {

    override fun onEnable() {
        this.saveDefaultConfig()

        val handler: HandlerManager = HandlerManager.instance
        val commandHandler = handler.findOrThrow(CommandHandler::class.java)

        val adapterHandler = ServerAdapterHandler(BukkitServerAdapter())
        val connectivityHandler = ConnectivityHandler(BukkitConnectivityListener())
        val sessionHandler = SessionHandler()

        val serverHandler = ServerHandler(this.config.getString("server.id"), sessionHandler)

        handler.register(adapterHandler)
        handler.register(connectivityHandler)
        handler.register(sessionHandler)
        handler.register(serverHandler)

        commandHandler.typeAdapters.add(MetadataTypeAdapter())

        commandHandler.registerCommand(
            ServerDumpCommand(serverHandler),
            StaffChatCommand(serverHandler),
            RemoteExecuteCommand(serverHandler),
            MetadataCommand()
        )

        Bukkit.getPluginManager().registerEvents(PlayerListener(handler), this)
    }
}