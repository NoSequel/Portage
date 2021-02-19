package io.github.nosequel.portage.bukkit.grant.menu

import io.github.nosequel.menus.MenuHandler
import io.github.nosequel.menus.button.Button
import io.github.nosequel.menus.menu.PaginatedMenu
import io.github.nosequel.portage.bukkit.grant.menu.element.GrantInfoElement
import io.github.nosequel.portage.core.grant.GrantHandler
import io.github.nosequel.portage.core.handler.HandlerManager
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryCloseEvent
import java.util.UUID
import java.util.concurrent.atomic.AtomicInteger
import java.util.stream.Collectors

class GrantsMenu(player: Player, private val target: UUID) : PaginatedMenu(player, "${player.name}'s Grants", 9) {

    private val grantHandler: GrantHandler = HandlerManager.instance.findOrThrow(GrantHandler::class.java)

    override fun getButtons(): MutableList<Button> {
        val index = AtomicInteger()

        return this.grantHandler.findGrantsByTarget(target).stream()
            .map { GrantInfoElement(index.getAndIncrement(), it, player) }
            .collect(Collectors.toList())
    }

    override fun onClose(event: InventoryCloseEvent) {
        MenuHandler.get().menus.remove(event.player)
    }
}