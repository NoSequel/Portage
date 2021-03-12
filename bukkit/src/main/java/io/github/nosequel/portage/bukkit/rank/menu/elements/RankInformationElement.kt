package io.github.nosequel.portage.bukkit.rank.menu.elements

import io.github.nosequel.menus.button.ButtonBuilder
import io.github.nosequel.portage.bukkit.util.ColorUtils
import io.github.nosequel.portage.bukkit.rank.menu.editor.RankEditorMenu
import io.github.nosequel.portage.core.rank.Rank
import org.apache.commons.lang.StringUtils
import org.bukkit.ChatColor
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class RankInformationElement(player: Player, rank: Rank, index: Int) : ButtonBuilder(index, ItemStack(Material.INK_SACK, 1, ColorUtils.DYE_COLORS.getOrDefault(ColorUtils.getRankColor(rank), DyeColor.WHITE).dyeData.toShort())) {

    init {
        this.displayName(rank.getDisplayName())
        this.action { RankEditorMenu(rank, player).updateMenu(); true }
        this.lore(
            "${ChatColor.GRAY}${ChatColor.STRIKETHROUGH}${StringUtils.repeat("-", 24)}",
            "${ChatColor.GOLD}Rank Information:",
            "${ChatColor.BLUE}▶ ${ChatColor.YELLOW}Weight: ${ChatColor.RED}${rank.weight}",
            "${ChatColor.BLUE}▶ ${ChatColor.YELLOW}Display Name: ${ChatColor.RED}${rank.getDisplayName()}",
            "",
            "${ChatColor.GOLD}Chat Information:",
            "${ChatColor.BLUE}▶ ${ChatColor.YELLOW}Prefix: ${ChatColor.RED}${rank.prefix}",
            "${ChatColor.BLUE}▶ ${ChatColor.YELLOW}Suffix: ${ChatColor.RED}${rank.suffix}",
            "${ChatColor.BLUE}▶ ${ChatColor.YELLOW}Color: ${ColorUtils.getRankColor(rank)}${ColorUtils.getRankColor(rank).name}",
            "",
            "${ChatColor.GRAY}${ChatColor.ITALIC}Click to edit this rank",
            "${ChatColor.GRAY}${ChatColor.STRIKETHROUGH}${StringUtils.repeat("-", 24)}",
        )
    }
}