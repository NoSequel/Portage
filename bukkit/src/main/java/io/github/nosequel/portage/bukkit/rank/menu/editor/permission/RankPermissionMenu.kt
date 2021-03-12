package io.github.nosequel.portage.bukkit.rank.menu.editor.permission

import io.github.nosequel.menus.MenuHandler
import io.github.nosequel.menus.button.Button
import io.github.nosequel.menus.button.ButtonBuilder
import io.github.nosequel.menus.menu.PaginatedMenu
import io.github.nosequel.portage.bukkit.rank.menu.editor.permission.element.RankPermissionElement
import io.github.nosequel.portage.bukkit.rank.prompt.RankEditPermissionPrompt
import io.github.nosequel.portage.bukkit.util.chat.ChatPromptHandler
import io.github.nosequel.portage.core.handler.HandlerManager
import io.github.nosequel.portage.core.rank.Rank
import org.bukkit.ChatColor
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack
import java.util.concurrent.atomic.AtomicInteger

class RankPermissionMenu(player: Player, val rank: Rank) : PaginatedMenu(player, "Viewing Permissions", 9*4) {

    private val promptHandler: ChatPromptHandler = HandlerManager.instance.findOrThrow(ChatPromptHandler::class.java)

    override fun getButtonsInRange(): MutableList<Button> {
        val buttons: MutableList<Button> = super.getButtonsInRange()

        buttons.add(
            ButtonBuilder(4, ItemStack(Material.WOOL, 1, DyeColor.GREEN.data.toShort()))
                .displayName("${ChatColor.LIGHT_PURPLE}Add a permission")
                .lore("${ChatColor.YELLOW}Click to add a new permission")
                .action {
                    player.closeInventory()
                    this.promptHandler.startPrompt(player, RankEditPermissionPrompt(), rank)
                    true
                }
        )


        for (i in 9..17) {
            buttons.add(this.getEmptyButton().index(i))
        }

        return buttons
    }

    override fun getButtons(): MutableList<Button> {
        val buttons: MutableList<Button> = mutableListOf()
        val index = AtomicInteger(9)

        rank.permissions.stream()
            .map { RankPermissionElement(index.getAndIncrement(), rank, it, this) }
            .forEach { buttons.add(it) }

        return buttons
    }

    override fun onClose(event: InventoryCloseEvent) {
        MenuHandler.get().menus.remove(event.player)
    }


    /**
     * Get an empty [ButtonBuilder] object
     */
    private fun getEmptyButton(): ButtonBuilder {
        return ButtonBuilder(0, ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLACK.data.toShort()))
            .displayName("")
            .action { true }
    }
}