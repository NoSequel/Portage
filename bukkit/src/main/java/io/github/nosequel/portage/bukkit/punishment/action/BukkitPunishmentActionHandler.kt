package io.github.nosequel.portage.bukkit.punishment.action

import io.github.nosequel.portage.core.punishments.Punishment
import io.github.nosequel.portage.core.punishments.PunishmentActionHandler
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
}