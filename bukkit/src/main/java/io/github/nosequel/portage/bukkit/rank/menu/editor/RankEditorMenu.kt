package io.github.nosequel.portage.bukkit.rank.menu.editor

import io.github.nosequel.menus.MenuHandler
import io.github.nosequel.menus.button.Button
import io.github.nosequel.menus.button.ButtonBuilder
import io.github.nosequel.menus.menu.PaginatedMenu
import io.github.nosequel.portage.core.rank.Rank
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryCloseEvent
import java.util.concurrent.atomic.AtomicInteger

class RankEditorMenu(val rank: Rank, player: Player) : PaginatedMenu(player, "Rank Editor", 18) {

    override fun getButtons(): MutableList<Button> {
        val index = AtomicInteger()

        return RankEditorType.values()
            .map {
                ButtonBuilder(index.getAndIncrement(), it.material)
                    .displayName(it.displayName)
                    .lore(*it.getLore(rank))
                    .action { clickType: ClickType -> it.getAction(rank, clickType, this).accept(player); true }
            }.toCollection(mutableListOf())
    }

    override fun onClose(event: InventoryCloseEvent) {
        MenuHandler.get().menus.remove(event.player)
    }
}