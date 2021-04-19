package io.github.nosequel.portage.bukkit.rank.menu

import io.github.nosequel.menus.MenuHandler
import io.github.nosequel.menus.button.Button
import io.github.nosequel.menus.menu.PaginatedMenu
import io.github.nosequel.portage.bukkit.rank.menu.elements.RankInformationElement
import io.github.nosequel.portage.core.handler.HandlerManager
import io.github.nosequel.portage.core.rank.RankHandler
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryCloseEvent
import java.util.concurrent.atomic.AtomicInteger
import java.util.stream.Collectors

class RankMenu(player: Player) : PaginatedMenu(player, "Global Rank List", 18) {

    private val rankHandler: RankHandler = HandlerManager.findOrThrow(RankHandler::class.java)

    override fun getButtons(): MutableList<Button> {
        val index = AtomicInteger()

        return rankHandler.getRanks()
            .map { RankInformationElement(player, it, index.getAndIncrement()) }
            .toMutableList()
    }

    override fun onClose(event: InventoryCloseEvent) {
        MenuHandler.get().menus.remove(event.player)
    }
}