package io.github.nosequel.portage.bukkit

import io.github.nosequel.menus.MenuHandler
import io.github.nosequel.portage.bukkit.command.ListCommand
import io.github.nosequel.portage.bukkit.grant.GrantCommand
import io.github.nosequel.portage.bukkit.rank.RankCommand
import io.github.nosequel.portage.bukkit.util.chat.ChatPromptHandler
import io.github.nosequel.portage.bukkit.listener.ChatPromptListener
import io.github.nosequel.portage.bukkit.listener.ProfileListener
import io.github.nosequel.portage.bukkit.listener.PunishmentListener
import io.github.nosequel.portage.bukkit.punishment.PunishmentCommand
import io.github.nosequel.portage.bukkit.punishment.action.BukkitPunishmentActionHandler
import io.github.nosequel.portage.core.punishments.PunishmentHandler
import io.github.nosequel.portage.bukkit.util.command.CommandHandler
import io.github.nosequel.portage.core.PortageAPI
import io.github.nosequel.portage.core.database.DatabaseHandler
import io.github.nosequel.portage.core.database.mongo.MongoHandler
import io.github.nosequel.portage.core.database.redis.RedisHandler
import io.github.nosequel.portage.core.handler.HandlerManager
import io.github.nosequel.portage.core.punishments.PunishmentRepository
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class PortagePlugin : JavaPlugin() {

    private val handler: HandlerManager = HandlerManager()
    private val portageAPI: PortageAPI = PortageAPI(handler, this.createDatabaseHandler())

    override fun onEnable() {
        this.saveDefaultConfig()

        this.handler.register(PunishmentHandler(PunishmentRepository(this.portageAPI), BukkitPunishmentActionHandler()).also { it.enable() })
        this.handler.register(ChatPromptHandler().also { it.enable() })

        arrayOf(
            ProfileListener(),
            PunishmentListener(),
            ChatPromptListener(this.handler.findOrThrow(ChatPromptHandler::class.java))
        ).forEach {
            Bukkit.getPluginManager().registerEvents(it, this)
        }

        // register commands
        this.handler.register(CommandHandler("portage")
            .also {
                it.registerCommand(RankCommand(this.handler),
                    GrantCommand(),
                    ListCommand(),
                    PunishmentCommand())
            }
            .also { it.enable() })

        // register menu handler
        MenuHandler(this)
    }

    override fun onDisable() {
        this.portageAPI.disable()
    }

    /**
     * Create a new [DatabaseHandler] object
     */
    private fun createDatabaseHandler(): DatabaseHandler {
        val mongo: MongoHandler = if (this.config.getBoolean("mongo.auth.enabled")) {
            MongoHandler(
                this.config.getString("mongo.hostname"),
                this.config.getInt("mongo.port"),
                this.config.getString("mongo.database")
            )
        } else {
            MongoHandler(
                this.config.getString("mongo.hostname"),
                this.config.getInt("mongo.port"),
                this.config.getString("mongo.database"),
                this.config.getString("mongo.auth.username"),
                this.config.getString("mongo.auth.password")
            )
        }

        val redis: RedisHandler = if (this.config.getBoolean("redis.auth.enabled")) {
            RedisHandler(
                this.config.getString("redis.hostname"),
                this.config.getInt("redis.port")
            )
        } else {
            RedisHandler(
                this.config.getString("redis.hostname"),
                this.config.getInt("redis.port"),
                this.config.getString("redis.auth.password")
            )
        }

        return DatabaseHandler(handler, mongo, redis);
    }
}