package io.github.nosequel.portage.bukkit.grant.menu.element

import io.github.nosequel.menus.button.ButtonBuilder
import io.github.nosequel.menus.menu.Menu
import io.github.nosequel.portage.core.expirable.ExpirationData
import io.github.nosequel.portage.core.grant.Grant
import io.github.nosequel.portage.core.grant.GrantHandler
import io.github.nosequel.portage.core.handler.HandlerManager
import org.apache.commons.lang.StringUtils
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.material.MaterialData
import java.util.Date

class GrantInfoElement(index: Int, val grant: Grant, menu: Menu) : ButtonBuilder(index, Material.WOOL) {

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
            val handler = HandlerManager.findOrThrow(GrantHandler::class.java)

            if (grant.isActive()) {
                handler.expireGrant(grant, ExpirationData("Unspecified", System.currentTimeMillis()))
            } else {
                handler.unexpireGrant(grant)
            }

            menu.updateMenu()
            true
        }

        this.displayName("${ChatColor.RED}${this.grant.uuid.toString().substring(0, 12)}")
        this.lore(*lore.toTypedArray())
    }

    override fun toItemStack(): ItemStack {
        return super.toItemStack().clone().also {
            it.durability = (if (grant.isActive()) DyeColor.GREEN.data else DyeColor.YELLOW.data).toShort()
        }
    }
}