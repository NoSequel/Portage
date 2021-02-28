package io.github.nosequel.portage.bukkit.listener

import io.github.nosequel.portage.core.punishments.PunishmentHandler
import io.github.nosequel.portage.core.handler.HandlerManager
import io.github.nosequel.portage.core.punishments.PunishmentType
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerLoginEvent

class PunishmentListener : Listener {

    private val punishmentHandler: PunishmentHandler =
        HandlerManager.instance.findOrThrow(PunishmentHandler::class.java)


    @EventHandler(priority = EventPriority.LOWEST)
    fun login(event: PlayerLoginEvent) {
        this.punishmentHandler.findMostRelevantPunishment(event.player.uniqueId, PunishmentType.BAN)
            .ifPresent {
                event.disallow(PlayerLoginEvent.Result.KICK_BANNED,
                    "${ChatColor.YELLOW}You are currently banned from this server." +
                            "\n${ChatColor.YELLOW}Reason: ${ChatColor.LIGHT_PURPLE}${it.reason}" +
                            "\n" +
                            "\n" +
                            "${ChatColor.GRAY}You can appeal your punishment, or purchase an unban.")
            }
    }

    @EventHandler
    fun chat(event: AsyncPlayerChatEvent) {
        this.punishmentHandler.findMostRelevantPunishment(event.player.uniqueId, PunishmentType.MUTE)
            .ifPresent {
                event.isCancelled = true
                event.player.sendMessage("${ChatColor.RED}You are currently muted for violating our terms of service,\n${ChatColor.RED}Reason: ${ChatColor.LIGHT_PURPLE}${it.reason}")
            }
    }
}