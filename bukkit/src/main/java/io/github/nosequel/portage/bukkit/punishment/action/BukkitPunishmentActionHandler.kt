package io.github.nosequel.portage.bukkit.punishment.action

import io.github.nosequel.portage.core.punishments.Punishment
import io.github.nosequel.portage.core.punishments.PunishmentActionHandler
import kotlinx.coroutines.runBlocking
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import java.util.Optional
import java.util.UUID

class BukkitPunishmentActionHandler : PunishmentActionHandler {

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
            val target = Optional.ofNullable(Bukkit.getOfflinePlayer(punishment.target))
            val executor = Optional.ofNullable(Bukkit.getOfflinePlayer(punishment.executor))

            Bukkit.broadcastMessage("${ChatColor.WHITE}${target.get().name} " +
                    "${ChatColor.GREEN}has been ${punishment.type.simpleName} by ${ChatColor.WHITE}${if (executor.isPresent) executor.get().name else "CONSOLE"}"
            )
        }
    }

    override fun expirePunishment(punishment: Punishment) {
        runBlocking {
            val target = Optional.ofNullable(Bukkit.getOfflinePlayer(punishment.target))
            val executor = Optional.ofNullable(Bukkit.getOfflinePlayer(punishment.executor))

            Bukkit.broadcastMessage("${ChatColor.WHITE}${target.get().name} " +
                    "${ChatColor.GREEN}has been un${punishment.type.simpleName} by ${ChatColor.WHITE}${if (executor.isPresent) executor.get().name else "CONSOLE"}"
            )
        }
    }
}