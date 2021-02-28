package io.github.nosequel.portage.bukkit.punishment

import io.github.nosequel.portage.bukkit.expirable.Duration
import io.github.nosequel.portage.bukkit.util.UUIDFetcher
import io.github.nosequel.portage.bukkit.util.command.annotation.Command
import io.github.nosequel.portage.bukkit.util.command.annotation.Parameter
import io.github.nosequel.portage.core.handler.HandlerManager
import io.github.nosequel.portage.core.punishments.Punishment
import io.github.nosequel.portage.core.punishments.PunishmentHandler
import io.github.nosequel.portage.core.punishments.PunishmentType
import org.bukkit.ChatColor
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import java.util.Optional
import java.util.UUID

class PunishmentCommand {

    private val punishmentHandler: PunishmentHandler =
        HandlerManager.instance.findOrThrow(PunishmentHandler::class.java)

    @Command(label = "ban", permission = "portage.ban")
    fun ban(sender: CommandSender, targetName: String, @Parameter(value="No reason provided", name = "reason") reason: String) {
        val target: OfflinePlayer = UUIDFetcher.getOfflinePlayer(targetName)

        if (target.firstPlayed > 0) {
            this.punishmentHandler.registerPunishment(Punishment(
                target.uniqueId,
                PunishmentType.BAN,
                UUID.randomUUID(),
                reason
            ))
        } else {
            sender.sendMessage("${ChatColor.RED}That player has never played before")
        }
    }

    @Command(label = "tempban", permission = "portage.tempban")
    fun tempban(sender: CommandSender, targetName: String, duration: Duration, @Parameter(value="No reason provided", name = "reason") reason: String) {
        val target: OfflinePlayer = UUIDFetcher.getOfflinePlayer(targetName)

        if (target.firstPlayed > 0) {
            this.punishmentHandler.registerPunishment(Punishment(
                target.uniqueId,
                PunishmentType.BAN,
                UUID.randomUUID(),
                reason,
                duration = duration.duration
            ))
        } else {
            sender.sendMessage("${ChatColor.RED}That player has never played before")
        }
    }

    @Command(label = "unban", permission = "portage.unban")
    fun unban(sender: CommandSender, targetName: String, @Parameter(value="No reason provided", name = "reason") reason: String) {
        val target: OfflinePlayer = UUIDFetcher.getOfflinePlayer(targetName)

        if (target.firstPlayed > 0) {
            val punishment: Optional<Punishment> =
                this.punishmentHandler.findMostRelevantPunishment(target.uniqueId, PunishmentType.BAN)

            if (!punishment.isPresent) {
                sender.sendMessage("${ChatColor.RED}That player does not have any active punishments.")
                return
            } else {
                punishment.get().expire(reason)
            }
        } else {
            sender.sendMessage("${ChatColor.RED}That player has never played before")
        }
    }
}