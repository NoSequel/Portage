package io.github.nosequel.portage.bukkit.punishment.action

import io.github.nosequel.portage.core.handler.HandlerManager
import io.github.nosequel.portage.core.profile.Profile
import io.github.nosequel.portage.core.profile.ProfileHandler
import io.github.nosequel.portage.core.punishments.Punishment
import io.github.nosequel.portage.core.punishments.PunishmentActionHandler
import kotlinx.coroutines.runBlocking
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import java.util.Optional
import java.util.UUID

class BukkitPunishmentActionHandler : PunishmentActionHandler {

    private val profileHandler: ProfileHandler = HandlerManager.instance.findOrThrow(ProfileHandler::class.java)

    override fun attemptBan(target: UUID, punishment: Punishment) {
        Optional.of(Bukkit.getPlayer(target)).ifPresent {
            it.kickPlayer(
                "${ChatColor.YELLOW}You are currently banned for violating our terms of service," +
                        "\n${ChatColor.YELLOW}Reason: ${ChatColor.LIGHT_PURPLE}${punishment.reason}" +
                        "\n" +
                        "\n" +
                        "${ChatColor.GRAY}You can appeal your punishment, or purchase an unban.")
        }
    }

    override fun registerPunishment(punishment: Punishment) {
        runBlocking {
            var target: Optional<Profile> = profileHandler.find(punishment.target)
            val executor: Optional<Profile> = profileHandler.find(punishment.executor)

            if (!target.isPresent) {
                target = profileHandler.repository.retrieveAsync(punishment.target.toString())
            }

            Bukkit.broadcastMessage("${ChatColor.WHITE}${target.get().name} " +
                    "${ChatColor.GREEN}has been ${punishment.type.simpleName} by ${ChatColor.WHITE}${if (executor.isPresent) executor.get().name else "CONSOLE"}"
            )
        }
    }

    override fun expirePunishment(punishment: Punishment) {
        runBlocking {
            var target: Optional<Profile> = profileHandler.find(punishment.target)
            val executor: Optional<Profile> = profileHandler.find(punishment.executor)

            if (!target.isPresent) {
                target = profileHandler.repository.retrieveAsync(punishment.target.toString())
            }

            Bukkit.broadcastMessage("${ChatColor.WHITE}${target.get().name} " +
                    "${ChatColor.GREEN}has been un${punishment.type.simpleName} by ${ChatColor.WHITE}${if (executor.isPresent) executor.get().name else "CONSOLE"}"
            )
        }
    }
}