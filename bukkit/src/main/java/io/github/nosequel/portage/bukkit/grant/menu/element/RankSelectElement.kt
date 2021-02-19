package io.github.nosequel.portage.bukkit.grant.menu.element

import io.github.nosequel.menus.button.ButtonBuilder
import io.github.nosequel.portage.bukkit.grant.menu.duration.CalendarMenu
import io.github.nosequel.portage.core.rank.Rank
import org.apache.commons.lang.StringUtils
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import java.util.UUID

class RankSelectElement(index: Int, val target: UUID, val player: Player, val rank: Rank) : ButtonBuilder(index, Material.WOOL) {

    init {
        this.displayName(rank.getDisplayName())
        this.lore(
            "${ChatColor.GRAY}${ChatColor.STRIKETHROUGH}${StringUtils.repeat("-", 24)}",
            "${ChatColor.YELLOW}Click here to grant the ${rank.getDisplayName()} ${ChatColor.YELLOW}rank.",
            "${ChatColor.GRAY}${ChatColor.STRIKETHROUGH}${StringUtils.repeat("-", 24)}"
        )

        this.action { player.closeInventory().also { CalendarMenu(player, rank, target).updateMenu() }; true }
    }
}