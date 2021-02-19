package io.github.nosequel.portage.bukkit.grant.menu.element

import io.github.nosequel.menus.button.ButtonBuilder
import io.github.nosequel.portage.core.grant.Grant
import org.apache.commons.lang.StringUtils
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import java.util.Date

class GrantInfoElement(index: Int, val grant: Grant, val player: Player) : ButtonBuilder(index, Material.WOOL) {

    init {
        val lore: MutableList<String> = mutableListOf(
            "${ChatColor.GRAY}${ChatColor.STRIKETHROUGH}${StringUtils.repeat("-", 24)}",
            "${ChatColor.YELLOW}Rank: ${grant.findRank().getDisplayName()}",
            "${ChatColor.YELLOW}Executor: ${ChatColor.LIGHT_PURPLE}${Bukkit.getOfflinePlayer(grant.executor).name}",
            "",
            "${ChatColor.YELLOW}Starting Date: ${ChatColor.LIGHT_PURPLE}${Date(grant.start)}",
            "${ChatColor.YELLOW}Expiration Date: ${ChatColor.LIGHT_PURPLE}${
                if (grant.duration == -1L) "Never" else Date(grant.duration + grant.start)
            }",
            "${ChatColor.GRAY}${ChatColor.STRIKETHROUGH}${StringUtils.repeat("-", 24)}"
        )

        if (grant.expirationData != null && !grant.isActive()) {
            lore.addAll(listOf(
                "${ChatColor.YELLOW}Expired On: ${ChatColor.LIGHT_PURPLE}${Date(grant.expirationData!!.date)}",
                "${ChatColor.YELLOW}Expire Reason: ${ChatColor.LIGHT_PURPLE}${grant.expirationData!!.reason}",
                "${ChatColor.GRAY}${ChatColor.STRIKETHROUGH}${StringUtils.repeat("-", 24)}"
            ))
        }

        this.action {
            println(grant.isActive())
            if (grant.isActive()) {
                grant.expire("Unspecified")
            } else {
                grant.expired = false
                grant.expirationData = null
                grant.duration = -1L
            }

            true
        }

        this.displayName("${ChatColor.RED}${this.grant.uuid.toString().substring(0, 7)}")
        this.lore(*lore.toTypedArray())
    }
}