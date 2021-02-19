package io.github.nosequel.portage.bukkit.grant.menu

import io.github.nosequel.menus.MenuHandler
import io.github.nosequel.menus.button.Button
import io.github.nosequel.menus.menu.PaginatedMenu
import io.github.nosequel.portage.bukkit.grant.menu.element.RankSelectElement
import io.github.nosequel.portage.core.handler.HandlerManager
import io.github.nosequel.portage.core.rank.RankHandler
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryCloseEvent
import java.util.UUID
import java.util.concurrent.atomic.AtomicInteger
import java.util.stream.Collectors

class GrantMenu(player: Player, val target: UUID) : PaginatedMenu(player, "Select a Rank", 18) {

    private val rankHandler: RankHandler = HandlerManager.instance.findOrThrow(RankHandler::class.java)

    override fun getButtons(): MutableList<Button> {
        val index = AtomicInteger()

        return this.rankHandler.stream()
            .map { RankSelectElement(index.getAndIncrement(), target, player, it) }
            .collect(Collectors.toList())
    }

    override fun onClose(event: InventoryCloseEvent) {
        MenuHandler.get().menus.remove(event.player)
    }
}