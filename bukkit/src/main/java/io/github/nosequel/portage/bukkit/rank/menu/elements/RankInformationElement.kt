package io.github.nosequel.portage.bukkit.rank.menu.elements

import io.github.nosequel.menus.button.ButtonBuilder
import io.github.nosequel.portage.bukkit.rank.menu.editor.RankEditorMenu
import io.github.nosequel.portage.core.rank.Rank
import org.apache.commons.lang.StringUtils
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player

class RankInformationElement(player: Player, rank: Rank, index: Int) : ButtonBuilder(index, Material.INK_SACK) {

    init {
        this.displayName(rank.getDisplayName())
        this.action { RankEditorMenu(rank, player).updateMenu(); true }
        this.lore(
            "${ChatColor.GRAY}${ChatColor.STRIKETHROUGH}${StringUtils.repeat("-", 24)}",
            "${ChatColor.GOLD}Rank Information:",
            "${ChatColor.GRAY}${ChatColor.BOLD} ｜ ${ChatColor.WHITE}Weight: ${ChatColor.RED}${rank.weight}",
            "${ChatColor.GRAY}${ChatColor.BOLD} ｜ ${ChatColor.WHITE}Display Name: ${ChatColor.RED}${rank.getDisplayName()}",
            "  ${ChatColor.GRAY}${ChatColor.STRIKETHROUGH}${StringUtils.repeat("-", 18)}",
            "",
            "${ChatColor.GOLD}Chat Information:",
            "${ChatColor.GRAY}${ChatColor.BOLD} ｜ ${ChatColor.WHITE}Prefix: ${ChatColor.RED}${rank.prefix}",
            "${ChatColor.GRAY}${ChatColor.BOLD} ｜ ${ChatColor.WHITE}Suffix: ${ChatColor.RED}${rank.suffix}",
            "  ${ChatColor.GRAY}${ChatColor.STRIKETHROUGH}${StringUtils.repeat("-", 18)}",
            "",
            "${ChatColor.GRAY}${ChatColor.ITALIC}Click to edit this rank",
            "${ChatColor.GRAY}${ChatColor.STRIKETHROUGH}${StringUtils.repeat("-", 24)}",
        )
    }
}