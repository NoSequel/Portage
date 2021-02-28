package io.github.nosequel.portage.bukkit.rank.menu.editor.permission.element

import io.github.nosequel.menus.button.ButtonBuilder
import io.github.nosequel.menus.menu.Menu
import io.github.nosequel.portage.core.rank.Rank
import org.bukkit.ChatColor
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class RankPermissionElement(index: Int, rank: Rank, permission: String, menu: Menu) : ButtonBuilder(index, ItemStack(Material.WOOL, 1, DyeColor.ORANGE.woolData.toShort())) {

    init {
        this.displayName("${ChatColor.LIGHT_PURPLE}$permission")
        this.lore("${ChatColor.YELLOW}Click to remove this permission")
        this.action { rank.permissions.remove(permission); menu.updateMenu(); true }
    }
}