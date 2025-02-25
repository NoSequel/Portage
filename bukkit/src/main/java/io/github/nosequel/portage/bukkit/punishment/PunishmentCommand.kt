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
import org.bukkit.entity.Player
import java.util.Optional
import java.util.UUID

class PunishmentCommand {

    private val punishmentHandler: PunishmentHandler =
        HandlerManager.findOrThrow(PunishmentHandler::class.java)

    @Command(label = "ban", permission = "portage.ban")
    fun ban(
        sender: CommandSender,
        targetName: String,
        @Parameter(name = "reason") reason: Array<String>
    ) {
        this.punish(sender, UUIDFetcher.getOfflinePlayer(targetName), reason, Duration(-1L), PunishmentType.BAN)
    }

    @Command(label = "tempban", permission = "portage.tempban")
    fun tempban(
        sender: CommandSender,
        targetName: String,
        duration: Duration,
        @Parameter(name = "reason") reason: Array<String>
    ) {
        this.punish(sender, UUIDFetcher.getOfflinePlayer(targetName), reason, duration, PunishmentType.BAN)
    }

    @Command(label = "unban", permission = "portage.unban")
    fun unban(
        sender: CommandSender,
        targetName: String,
        @Parameter(name = "reason") reason: Array<String>
    ) {
        this.unpunish(sender, UUIDFetcher.getOfflinePlayer(targetName), reason, PunishmentType.BAN)
    }

    @Command(label = "mute", permission = "portage.mute")
    fun mute(
        sender: CommandSender,
        targetName: String,
        @Parameter(name = "reason") reason: Array<String>
    ) {
        this.punish(sender, UUIDFetcher.getOfflinePlayer(targetName), reason, Duration(-1L), PunishmentType.MUTE)
    }

    @Command(label = "tempmute", permission = "portage.tempmute")
    fun tempmute(
        sender: CommandSender,
        targetName: String,
        duration: Duration,
        @Parameter(name = "reason") reason: Array<String>
    ) {
        this.punish(sender, UUIDFetcher.getOfflinePlayer(targetName), reason, duration, PunishmentType.MUTE)
    }

    @Command(label = "unmute", permission = "portage.unmute")
    fun unmute(
        sender: CommandSender,
        targetName: String,
        @Parameter(name = "reason") reason: Array<String>
    ) {
        this.unpunish(sender, UUIDFetcher.getOfflinePlayer(targetName), reason, PunishmentType.MUTE)
    }

    /**
     * Handle the punishment of an [OfflinePlayer]
     */
    private fun punish(
        sender: CommandSender,
        target: OfflinePlayer,
        reason: Array<String>,
        duration: Duration,
        type: PunishmentType
    ) {
        if (target.firstPlayed > 0) {
            this.punishmentHandler.registerPunishment(Punishment(
                target = target.uniqueId,
                type = type,
                reason = reason.joinToString(" "),
                uuid = UUID.randomUUID(),
                executor = if (sender is Player)
                    sender.uniqueId
                else
                    UUID.randomUUID(),
                duration = duration.duration
            ))

            if (type == PunishmentType.BAN) {
                this.punishmentHandler.attemptBan(target.uniqueId)
            }
        } else {
            sender.sendMessage("${ChatColor.RED}That player has never played before")
        }
    }

    /**
     * Handle the unpunishment of an [OfflinePlayer]
     */
    private fun unpunish(sender: CommandSender, target: OfflinePlayer, reason: Array<String>, type: PunishmentType) {
        if (target.firstPlayed > 0) {
            val punishment: Optional<Punishment> =
                this.punishmentHandler.findMostRelevantPunishment(target.uniqueId, type)

            if (!punishment.isPresent) {
                sender.sendMessage("${ChatColor.RED}That player does not have any active punishments.")
                return
            } else {
                this.punishmentHandler.expirePunishment(punishment.get(), reason.joinToString(" "))
            }
        } else {
            sender.sendMessage("${ChatColor.RED}That player has never played before")
        }
    }
}